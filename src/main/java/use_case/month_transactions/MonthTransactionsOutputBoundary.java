package use_case.month_transactions;

public interface MonthTransactionsOutputBoundary {
    void present(MonthTransactionsResponseModel responseModel);
}
