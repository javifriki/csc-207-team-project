package entity.currency_converter;


public class CurrencyConversionResult {
    private final double convertedAmount;
    private final String message;


    public CurrencyConversionResult(double convertedAmount, String message) {
        this.convertedAmount = convertedAmount;
        this.message = message;
    }


    public double getConvertedAmount() { return convertedAmount; }
    public String getMessage() { return message; }
}

