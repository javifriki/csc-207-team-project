package use_case.month_transactions;

import entity.Account;
import entity.Transaction;
import use_case.account.AccountDataAccessInterface;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MonthTransactionsInteractor implements MonthTransactionsInputBoundary {

    private final AccountDataAccessInterface accountGateway;
    private final MonthTransactionsOutputBoundary presenter;

    public MonthTransactionsInteractor(AccountDataAccessInterface accountGateway,
                                       MonthTransactionsOutputBoundary presenter) {
        this.accountGateway = accountGateway;
        this.presenter = presenter;
    }

    @Override
    public void getTransactionsForMonth(YearMonth month) {
        List<Account> accounts = accountGateway.getAllAccounts();
        List<MonthTransactionsResponseModel.TransactionData> result = new ArrayList<>();

        for (Account account : accounts) {
            for (Transaction t : account.getAccountTransactions()) {
                YearMonth ym = YearMonth.from(t.getTransactionDate());
                if (ym.equals(month)) {
                    result.add(
                            new MonthTransactionsResponseModel.TransactionData(
                                    t.getTransactionDate(),
                                    t.getTransactionAmount(),
                                    account.getAccountNumber(),
                                    t.getTransactionType().name(),
                                    t.getTransactionCategory().name()
                            )
                    );
                }
            }
        }

        // sort by date
        result.sort(Comparator.comparing(td -> td.date));

        presenter.present(new MonthTransactionsResponseModel(result));
    }
}
