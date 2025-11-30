package use_case.currency_converter;

public class CurrencyConverterInputData {
    public double amount;
    public String fromCurrency;
    public String toCurrency;

    public CurrencyConverterInputData(double amount, String fromCurrency, String toCurrency) {
        this.amount = amount;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }
}
