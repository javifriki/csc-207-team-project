package interface_adaptor.monthly_report;


import use_case.monthly_report.MonthlyReportInputBoundary;

public class MonthlyReportController {

    private final MonthlyReportInputBoundary interactor;

    public MonthlyReportController(MonthlyReportInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void showMonthlyReport(int year, int month) {
        interactor.execute(year, month); // pass raw values
    }
}
