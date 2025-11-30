package entity.currency_converter;
//i don't know if it's required but without it was not working
public class CurrencyConversionRequest {
    private final double amount;
    private final String fromCurrency;
    private final String toCurrency;


    public CurrencyConversionRequest(double amount, String fromCurrency, String toCurrency) {
        this.amount = amount;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }


    public double getAmount() { return amount; }
    public String getFromCurrency() { return fromCurrency; }
    public String getToCurrency() { return toCurrency; }
}

