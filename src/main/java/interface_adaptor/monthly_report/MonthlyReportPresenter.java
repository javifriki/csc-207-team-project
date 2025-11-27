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

        // Format text for UI
        String title = "Monthly Report for " +
                outputData.getYear() + "-" + outputData.getMonth();

        // Store into ViewModel
        viewModel.setTitle(title);
        viewModel.setLineChartImage(outputData.getLineGraph());
        viewModel.setPieChartImage(outputData.getPieChart());

    }

}

