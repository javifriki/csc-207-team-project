package interface_adaptor.monthly_report;


import use_case.monthly_report.MonthlyReportInputBoundary;
import use_case.monthly_report.MonthlyReportInputData;

import java.io.IOException;

public class MonthlyReportController {

    private final MonthlyReportInputBoundary interactor;

    public MonthlyReportController(MonthlyReportInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void showMonthlyReport(int year, int month) throws IOException, InterruptedException {
        interactor.generateReport(new MonthlyReportInputData(year, month));
    }
}
