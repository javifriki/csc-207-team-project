package use_case.currency_converter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CurrencyRateFetcher {

    private final Map<String, Double> rates = new HashMap<>();
    private final HttpClient httpClient;
    private final String apiBaseUrl;
    private final String apiKey;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Instant lastUpdateTime;
    private static final Duration CACHE_DURATION = Duration.ofHours(1);

    // Common currency codes supported by the API
    private static final String[] SUPPORTED_CURRENCIES = {
            "CAD", "USD", "EUR", "GBP", "JPY", "CNY", "AUD", "CHF",
            "HKD", "SGD", "NZD", "MXN", "INR", "BRL", "ZAR", "KRW",
            "SEK", "NOK", "DKK", "PLN", "RUB", "TRY", "THB", "MYR"
    };

    public CurrencyRateFetcher() {
        this(readApiUrlFromOpenApiSpec(),
                System.getProperty("exchange.rate.api.key", null));
    }

    public CurrencyRateFetcher(String apiBaseUrl) {
        this(apiBaseUrl, null);
    }

    public CurrencyRateFetcher(String apiBaseUrl, String apiKey) {
        this.apiBaseUrl = apiBaseUrl;
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.lastUpdateTime = Instant.MIN;

        // Initialize with fallback rates first (in case API call fails)
        initializeFallbackRates();

        // Attempt to fetch live rates from API on startup
        updateRatesOnStartup();
    }

    private void initializeFallbackRates() {
        lock.writeLock().lock();
        try {
            // Fallback rates (CAD-based, as per API description)
            // These are approximate rates - in production, these would be updated from the API

            // CAD to other currencies (base rates)
            Map<String, Double> cadToOther = new HashMap<>();
            cadToOther.put("USD", 0.73);
            cadToOther.put("EUR", 0.68);
            cadToOther.put("GBP", 0.58);
            cadToOther.put("JPY", 110.0);
            cadToOther.put("CNY", 5.26);
            cadToOther.put("AUD", 1.10);
            cadToOther.put("CHF", 0.66);
            cadToOther.put("HKD", 5.70);
            cadToOther.put("SGD", 0.99);
            cadToOther.put("NZD", 1.20);
            cadToOther.put("MXN", 12.50);
            cadToOther.put("INR", 60.75);
            cadToOther.put("BRL", 3.65);
            cadToOther.put("ZAR", 13.50);
            cadToOther.put("KRW", 980.0);
            cadToOther.put("SEK", 7.85);
            cadToOther.put("NOK", 7.95);
            cadToOther.put("DKK", 5.08);
            cadToOther.put("PLN", 2.95);
            cadToOther.put("RUB", 67.50);
            cadToOther.put("TRY", 23.50);
            cadToOther.put("THB", 26.25);
            cadToOther.put("MYR", 3.40);

            // Build all currency pairs using CAD as base
            for (String currency1 : cadToOther.keySet()) {
                double rate1 = cadToOther.get(currency1);
                // CAD -> currency
                rates.put("CAD->" + currency1, rate1);
                // currency -> CAD
                rates.put(currency1 + "->CAD", 1.0 / rate1);

                // Cross-currency pairs
                for (String currency2 : cadToOther.keySet()) {
                    if (!currency1.equals(currency2)) {
                        double rate2 = cadToOther.get(currency2);
                        // currency1 -> currency2 via CAD
                        rates.put(currency1 + "->" + currency2, rate2 / rate1);
                    }
                }
            }

            // CAD to CAD
            rates.put("CAD->CAD", 1.0);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public double getRate(String from, String to) {
        // Same currency
        if (from.equals(to)) {
            return 1.0;
        }

        // Try to get cached rate
        String key = from + "->" + to;
        lock.readLock().lock();
        try {
            Double cachedRate = rates.get(key);
            if (cachedRate != null && isCacheValid()) {
                return cachedRate;
            }
        } finally {
            lock.readLock().unlock();
        }

        // Update rates if cache is stale
        if (!isCacheValid()) {
            updateRates();
        }

        // Try again after update
        lock.readLock().lock();
        try {
            Double rate = rates.get(key);
            if (rate != null) {
                return rate;
            }

            // Try reverse rate and calculate inverse
            Double reverseRate = rates.get(to + "->" + from);
            if (reverseRate != null && reverseRate > 0) {
                return 1.0 / reverseRate;
            }

            // Try to calculate via CAD if both currencies are supported
            if (calculateRateViaCAD(from, to)) {
                Double rate1 = rates.get(from + "->CAD");
                Double rate2 = rates.get("CAD->" + to);
                if (rate1 != null && rate2 != null && rate1 > 0) {
                    return rate2 / rate1;
                }
            }

            return -1.0;
        } finally {
            lock.readLock().unlock();
        }
    }

    private boolean calculateRateViaCAD(String from, String to) {
        return rates.containsKey(from + "->CAD") && rates.containsKey("CAD->" + to);
    }

    private boolean isCacheValid() {
        return lastUpdateTime != null &&
                Instant.now().minus(CACHE_DURATION).isBefore(lastUpdateTime);
    }

    /**
     * Attempts to fetch live rates from the API on startup.
     * This is called during initialization and will use fallback rates if the API is unavailable.
     */
    private void updateRatesOnStartup() {
        // Force update by setting lastUpdateTime to ensure we fetch fresh rates
        lastUpdateTime = Instant.MIN;
        updateRates();
    }

    private void updateRates() {
        if ("TBD".equals(apiBaseUrl) || apiBaseUrl == null || apiBaseUrl.isEmpty()) {
            // API URL not configured, use fallback rates
            return;
        }

        // Check availability window (19:00-23:59 EST) as per API documentation
        // Note: We'll still attempt the call but be aware rates may not be available outside this window
        ZonedDateTime nowEST = ZonedDateTime.now(ZoneId.of("America/New_York"));
        int hour = nowEST.getHour();
        boolean inAvailabilityWindow = hour >= 19; // 19:00 to 23:59 EST (hours 19-23)

        if (!inAvailabilityWindow) {
            System.out.println("Info: Exchange rates are typically available between 19:00-23:59 EST. " +
                    "Current time: " + nowEST.format(DateTimeFormatter.ofPattern("HH:mm z")) +
                    ". API may return no data outside this window.");
        }

        try {
            // Build URL - no filter needed, API returns all rates and we filter by timestamp in parsing
            String url = apiBaseUrl.endsWith("/") ?
                    apiBaseUrl + "exchange-rates" :
                    apiBaseUrl + "/exchange-rates";

            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .header("Accept", "application/json");

            // Add API key if provided (as per API documentation, authentication key is required)
            if (apiKey != null && !apiKey.isEmpty()) {
                // AWS API Gateway typically uses x-api-key header
                requestBuilder.header("x-api-key", apiKey);
            } else {
                System.err.println("Warning: No API key provided. API may return 403 Access Denied.");
            }

            HttpRequest request = requestBuilder.GET().build();

            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            // Handle different response codes as per API documentation
            switch (response.statusCode()) {
                case 200:
                    parseAndUpdateRates(response.body());
                    lastUpdateTime = Instant.now();
                    System.out.println("Successfully updated exchange rates from API.");
                    break;
                case 400:
                    System.err.println("Error 400: Bad Request - Invalid filter syntax");
                    break;
                case 403:
                    System.err.println("Error 403: Access Denied - Authentication key may be missing or invalid");
                    break;
                case 404:
                    System.err.println("Error 404: Resource not found - API endpoint may be incorrect");
                    break;
                case 409:
                    System.err.println("Error 409: Conflict - Request conflict");
                    break;
                case 429:
                    System.err.println("Error 429: Too Many Requests - Rate limit exceeded");
                    break;
                case 502:
                    System.err.println("Error 502: Bad Gateway - API gateway error");
                    break;
                case 503:
                    System.err.println("Error 503: Service Unavailable - API may be temporarily unavailable");
                    break;
                default:
                    System.err.println("Error " + response.statusCode() + ": Unexpected response from API");
            }
        } catch (IOException | InterruptedException e) {
            // If API call fails, continue using cached/fallback rates
            System.err.println("Failed to update exchange rates: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error updating exchange rates: " + e.getMessage());
        }
    }

    private void parseAndUpdateRates(String jsonResponse) {
        try {
            // The API response format:
            // {
            //   "ForeignExchangeRates": [
            //     {
            //       "ExchangeRateId": 719352,
            //       "Rate": "0.1589",
            //       "ExchangeRateEffectiveTimestamp": "2021-11-03T00:00:00.000Z",
            //       "ExchangeRateExpiryTimestamp": "2021-11-03T23:59:59.000Z",
            //       "ExchangeRateSource": "BoC",
            //       "FromCurrency": { "Value": "HKD" },
            //       "ToCurrency": { "Value": "CAD" }
            //     }
            //   ]
            // }
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray ratesArray = root.optJSONArray("ForeignExchangeRates");

            if (ratesArray == null) {
                // Fallback: try parsing as direct array
                try {
                    ratesArray = new JSONArray(jsonResponse);
                } catch (Exception e) {
                    System.err.println("Warning: Response is not in expected format. Expected 'ForeignExchangeRates' array.");
                    return;
                }
            }

            Map<String, Double> newRates = new HashMap<>();
            Map<String, Double> cadRates = new HashMap<>();
            Instant now = Instant.now();

            for (int i = 0; i < ratesArray.length(); i++) {
                JSONObject rateObj = ratesArray.getJSONObject(i);

                // Verify the rate is currently valid using ExchangeRateEffectiveTimestamp and ExchangeRateExpiryTimestamp
                try {
                    String effectiveStr = rateObj.optString("ExchangeRateEffectiveTimestamp", null);
                    String expiryStr = rateObj.optString("ExchangeRateExpiryTimestamp", null);

                    if (effectiveStr != null && expiryStr != null) {
                        Instant effective = Instant.parse(effectiveStr);
                        Instant expiry = Instant.parse(expiryStr);

                        // Skip if rate is not currently valid (effective <= now < expiry)
                        if (now.isBefore(effective) || !now.isBefore(expiry)) {
                            continue;
                        }
                    }
                } catch (Exception e) {
                    // If date parsing fails, skip this rate
                    continue;
                }

                // Extract currency codes from FromCurrency and ToCurrency objects
                String fromCurrency = extractCurrencyValue(rateObj, "FromCurrency");
                String toCurrency = extractCurrencyValue(rateObj, "ToCurrency");

                if (fromCurrency == null || toCurrency == null) {
                    continue;
                }

                // Rate is a string in the API response
                String rateStr = rateObj.optString("Rate", null);
                if (rateStr == null || rateStr.isEmpty()) {
                    continue;
                }

                double rate;
                try {
                    rate = Double.parseDouble(rateStr);
                } catch (NumberFormatException e) {
                    continue;
                }

                if (rate <= 0) {
                    continue;
                }

                // According to API: Rate = number of FromCurrency units to buy one ToCurrency unit
                String key = fromCurrency + "->" + toCurrency;
                newRates.put(key, rate);

                // Store CAD-based rates for cross-currency calculations
                if ("CAD".equals(fromCurrency)) {
                    // CAD -> ForeignCurrency: rate is CAD per foreign currency unit
                    cadRates.put(toCurrency, rate);
                } else if ("CAD".equals(toCurrency)) {
                    // ForeignCurrency -> CAD: inverse
                    cadRates.put(fromCurrency, 1.0 / rate);
                }
            }

            // Build cross-currency rates using CAD as base currency
            // This allows conversion between any two currencies via CAD
            for (String currency1 : cadRates.keySet()) {
                for (String currency2 : cadRates.keySet()) {
                    if (!currency1.equals(currency2)) {
                        double rate1 = cadRates.get(currency1); // CAD per currency1
                        double rate2 = cadRates.get(currency2); // CAD per currency2
                        // currency1 -> currency2 = (CAD/currency2) / (CAD/currency1) = rate2/rate1
                        newRates.put(currency1 + "->" + currency2, rate2 / rate1);
                    }
                }
            }

            // Update rates map
            lock.writeLock().lock();
            try {
                if (!newRates.isEmpty()) {
                    rates.putAll(newRates);
                    System.out.println("Updated " + newRates.size() + " exchange rate pairs from API.");
                } else {
                    System.err.println("Warning: No valid exchange rates found in API response.");
                }
            } finally {
                lock.writeLock().unlock();
            }

        } catch (Exception e) {
            System.err.println("Failed to parse exchange rates: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Extracts currency code from FromCurrency or ToCurrency field.
     * These can be either objects with a "Value" field or plain strings.
     */
    private String extractCurrencyValue(JSONObject obj, String fieldName) {
        if (!obj.has(fieldName)) {
            return null;
        }

        Object currencyField = obj.get(fieldName);
        if (currencyField instanceof JSONObject) {
            // Format: { "Value": "HKD" }
            return ((JSONObject) currencyField).optString("Value", null);
        } else if (currencyField instanceof String) {
            // Fallback: plain string
            return (String) currencyField;
        }
        return null;
    }

    public String[] getSupportedCurrencies() {
        return SUPPORTED_CURRENCIES.clone();
    }

    /**
     * Reads the API URL from the er-tc-openapi.json file.
     * Falls back to the actual API URL or system property.
     */
    private static String readApiUrlFromOpenApiSpec() {
        // First check system property
        String systemPropertyUrl = System.getProperty("exchange.rate.api.url");
        if (systemPropertyUrl != null && !systemPropertyUrl.isEmpty() && !"TBD".equals(systemPropertyUrl)) {
            return systemPropertyUrl;
        }

        // Try to read from OpenAPI spec file
        try {
            Path openApiPath = Path.of("er-tc-openapi.json");
            if (!Files.exists(openApiPath)) {
                // Try relative to resources if not in root
                openApiPath = Path.of("src/main/resources/er-tc-openapi.json");
            }

            if (Files.exists(openApiPath)) {
                String contents = Files.readString(openApiPath);
                JSONObject openApiSpec = new JSONObject(contents);

                // Extract server URL from OpenAPI spec
                if (openApiSpec.has("servers") && openApiSpec.get("servers") instanceof JSONArray) {
                    JSONArray servers = openApiSpec.getJSONArray("servers");
                    if (servers.length() > 0) {
                        JSONObject server = servers.getJSONObject(0);
                        if (server.has("url")) {
                            String url = server.getString("url");
                            // Only use if it's not "TBD"
                            if (url != null && !url.isEmpty() && !"TBD".equals(url)) {
                                return url;
                            }
                        }
                    }
                }
            }
        } catch (IOException | org.json.JSONException e) {
            // If file reading fails, fall back to default
            System.err.println("Warning: Could not read API URL from er-tc-openapi.json: " + e.getMessage());
        }

        // Default to actual API URL
        return "https://bcd-api-dca-ipa.cbsa-asfc.cloud-nuage.canada.ca/exchange-rate-lambda";
    }
}

