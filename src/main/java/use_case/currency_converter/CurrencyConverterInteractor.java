package use_case.currency_converter;


public class CurrencyConverterInteractor implements CurrencyConverterInputBoundary {


    private final CurrencyConverterOutputBoundary presenter;
    private final CurrencyRateFetcher rateFetcher;


    public CurrencyConverterInteractor(CurrencyConverterOutputBoundary presenter,
                                       CurrencyRateFetcher rateFetcher) {
        this.presenter = presenter;
        this.rateFetcher = rateFetcher;
    }


    @Override
    public void convert(CurrencyConverterInputData inputData) {


        double rate = rateFetcher.getRate(inputData.fromCurrency, inputData.toCurrency);


        if (rate <= 0) {
            presenter.presentError("Invalid currency or conversion rate not available.");
            return;
        }


        double result = inputData.amount * rate;


        presenter.present(new CurrencyConverterOutputData(
                result,
                "Converted successfully!"
        ));
    }
}
