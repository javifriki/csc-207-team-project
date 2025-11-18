package interface_adaptor.month_transactions;

import use_case.month_transactions.MonthTransactionsInputBoundary;

import java.time.YearMonth;

public class MonthTransactionsController {

    private final MonthTransactionsInputBoundary interactor;

    public MonthTransactionsController(MonthTransactionsInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void selectMonth(YearMonth month) {
        interactor.getTransactionsForMonth(month);
    }
}
