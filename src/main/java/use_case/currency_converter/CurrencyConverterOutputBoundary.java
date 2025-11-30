package use_case.currency_converter;


public interface CurrencyConverterOutputBoundary {
    void present(CurrencyConverterOutputData outputData);
    void presentError(String errorMessage);
}
