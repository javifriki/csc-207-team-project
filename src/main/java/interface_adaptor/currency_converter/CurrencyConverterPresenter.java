package interface_adaptor.currency_converter;

import use_case.currency_converter.CurrencyConverterOutputBoundary;
import use_case.currency_converter.CurrencyConverterOutputData;

public class CurrencyConverterPresenter implements CurrencyConverterOutputBoundary {

    private final CurrencyConverterViewModel viewModel;

    public CurrencyConverterPresenter(CurrencyConverterViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(CurrencyConverterOutputData outputData) {
        String text = "Converted Amount: " + outputData.convertedAmount;
        viewModel.setResult(text);
        viewModel.firePropertyChanged();
    }

    @Override
    public void presentError(String errorMessage) {
        viewModel.setResult("Error: " + errorMessage);
        viewModel.firePropertyChanged();
    }
}
