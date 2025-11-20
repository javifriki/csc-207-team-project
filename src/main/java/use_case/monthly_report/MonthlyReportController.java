package use_case.monthly_report;

/**
 * Controller for the Monthly Report Use Case.
 * Receives the UI Input i.e selected month then converts it into a MonthlyRequestModel
 * then is passed into the MonthlyInputBoundary
 */


import use_case.monthly_report.MonthlyReportInputBoundary;
import use_case.monthly_report.MonthlyReportInputData;

public class MonthlyReportController {

    private final MonthlyReportInputBoundary interactor;

    public MonthlyReportController(MonthlyReportInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void showMonthlyReport(String accountNumber, int year, int month) {
        MonthlyReportInputData inputData =
                new MonthlyReportInputData(accountNumber, year, month);
        interactor.execute(inputData);
    }
}
