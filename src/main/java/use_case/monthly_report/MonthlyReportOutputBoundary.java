package use_case.monthly_report;

import org.json.JSONObject;
import use_case.add_transaction.AddTransactionOutputData;

/**
 * Output Boundary for the Monthly Report Use Case.
 */

public interface MonthlyReportOutputBoundary {
    void prepareTransactionSuccessView (AddTransactionOutputData addTransactionOutputData);
    void prepareTransactionFailView (String errorMessage);

    void prepareSuccessView(int year, int month, JSONObject graphJson, JSONObject categoryJson);
}
