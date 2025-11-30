package use_case.currency_converter;


import java.util.HashMap;
import java.util.Map;


public class CurrencyRateFetcher {


    private final Map<String, Double> rates = new HashMap<>();


    public CurrencyRateFetcher() {
        rates.put("USD->CAD", 1.37);
        rates.put("CAD->USD", 0.73);
        rates.put("USD->EUR", 0.91);
        rates.put("EUR->USD", 1.09);
    }

    public double getRate(String from, String to) {
        return rates.getOrDefault(from + "->" + to, -1.0);
    }
}

