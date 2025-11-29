package interface_adaptor.currency_converter;

import use_case.currency_converter.*;

public class CurrencyConverterController {

    private final CurrencyConverterInputBoundary interactor;

    public CurrencyConverterController(CurrencyConverterInputBoundary interactor) {
        this.interactor = interactor;

    }

    public void convert(double amount, String fromCurrency, String toCurrency) {
        CurrencyConverterInputData inputData =
                new CurrencyConverterInputData(amount, fromCurrency, toCurrency);

        interactor.convert(inputData);
    }
}
