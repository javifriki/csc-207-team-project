package use_case.currency_converter;

public class CurrencyConverterOutputData {

    public final double convertedAmount;
    public final String message;

    public CurrencyConverterOutputData(double convertedAmount, String message) {
        this.convertedAmount = convertedAmount;
        this.message = message;
    }
}
