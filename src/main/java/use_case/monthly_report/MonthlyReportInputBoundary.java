package use_case.monthly_report;

import java.io.IOException;

public interface MonthlyReportInputBoundary {
    void generateReport(MonthlyReportInputData monthlyReportInputData) throws IOException, InterruptedException;
}
