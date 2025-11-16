package service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class StockPriceService {

    public double getCurrentPrice(String stockSymbol) {
        try {
            // Using Alpha Vantage API (you'll need an API key)
            String apiKey = "YOUR_API_KEY"; // Get free key from alphavantage.co
            String urlString = String.format(
                    "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=%s",
                    stockSymbol, apiKey
            );

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            // Parse JSON response
            // This is simplified - you'll need to extract the price from the JSON
            return parsePriceFromJSON(response.toString());

        } catch (IOException e) {
            System.out.println("Error fetching stock price: " + e.getMessage());
            return 0.0; // Return 0 if API fails
        }
    }

    private double parsePriceFromJSON(String jsonResponse) {
        // Simplified parsing - you'll need to implement proper JSON parsing
        // Alpha Vantage returns complex JSON, you might want to use a JSON library
        if (jsonResponse.contains("\"05. price\":")) {
            String priceStr = jsonResponse.split("\"05. price\":\"")[1].split("\"")[0];
            return Double.parseDouble(priceStr);
        }
        return 0.0;
    }
}