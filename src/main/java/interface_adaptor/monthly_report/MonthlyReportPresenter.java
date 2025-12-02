package interface_adaptor.monthly_report;

import use_case.monthly_report.MonthlyReportOutputBoundary;
import use_case.monthly_report.MonthlyReportResponseModel;

public class MonthlyReportPresenter implements MonthlyReportOutputBoundary {

    private final MonthlyReportViewModel viewModel;

    public MonthlyReportPresenter(MonthlyReportViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(MonthlyReportResponseModel responseModel) {
        MonthlyReportState state = viewModel.getState();

        state.setYear(responseModel.getYear());
        state.setMonth(responseModel.getMonth());
        state.setLineChartImage(responseModel.getLineGraph());
        state.setPieChartImage(responseModel.getPieChart());
        state.setErrorMessage(null);

        viewModel.firePropertyChanged();
    }

    @Override
    public void presentError(String message) {
        MonthlyReportState state = viewModel.getState();
        state.setErrorMessage(message);
        viewModel.firePropertyChanged();
    }
}
