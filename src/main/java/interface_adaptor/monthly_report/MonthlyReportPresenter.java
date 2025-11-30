package interface_adaptor.monthly_report;

import use_case.monthly_report.MonthlyReportOutputBoundary;
import use_case.monthly_report.MonthlyReportOutputData;

public class MonthlyReportPresenter implements MonthlyReportOutputBoundary {

    private final MonthlyReportViewModel viewModel;

    public MonthlyReportPresenter(MonthlyReportViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(MonthlyReportOutputData outputData) {
        MonthlyReportState state = viewModel.getState();

        state.setYear(outputData.getYear());
        state.setMonth(outputData.getMonth());
        state.setLineChartImage(outputData.getLineGraph());
        state.setPieChartImage(outputData.getPieChart());
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
