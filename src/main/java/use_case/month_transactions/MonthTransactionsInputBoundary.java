package use_case.month_transactions;

import java.time.YearMonth;

public interface MonthTransactionsInputBoundary {
    void getTransactionsForMonth(YearMonth month);
}
