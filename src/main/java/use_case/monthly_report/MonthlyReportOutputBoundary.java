package use_case.monthly_report;


/**
 * Output Boundary for the Monthly Report Use Case.
 */

public interface MonthlyReportOutputBoundary {
    void present(MonthlyReportOutputData outputData);
    void presentError(String s);
}
