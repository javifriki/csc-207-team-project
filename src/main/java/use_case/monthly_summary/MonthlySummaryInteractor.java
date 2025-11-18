package use_case.monthly_summary;

import entity.Account;
import entity.Transaction;
import use_case.account.AccountDataAccessInterface;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class MonthlySummaryInteractor implements MonthlySummaryInputBoundary {

    private final AccountDataAccessInterface accountGateway;
    private final MonthlySummaryOutputBoundary presenter;

    public MonthlySummaryInteractor(AccountDataAccessInterface accountGateway,
                                    MonthlySummaryOutputBoundary presenter) {
        this.accountGateway = accountGateway;
        this.presenter = presenter;
    }

    @Override
    public void generateSummary() {
        List<Account> accounts = accountGateway.getAllAccounts();

        Map<YearMonth, double[]> monthToIncomeExpense = new HashMap<>();
        // [0] = income, [1] = spending

        for (Account account : accounts) {
            for (Transaction t : account.getAccountTransactions()) {
                YearMonth ym = YearMonth.from(t.getTransactionDate());
                monthToIncomeExpense.putIfAbsent(ym, new double[2]);
                double[] arr = monthToIncomeExpense.get(ym);

                if (t.getTransactionType() == Transaction.TransactionType.INCOME) {
                    arr[0] += t.getTransactionAmount();
                } else { // SPENDING
                    arr[1] += t.getTransactionAmount();
                }
            }
        }

        // sort months, take last 6
        List<YearMonth> sortedMonths = monthToIncomeExpense.keySet().stream()
                .sorted()
                .collect(Collectors.toList());

        int fromIndex = Math.max(sortedMonths.size() - 6, 0);
        List<YearMonth> lastSix = sortedMonths.subList(fromIndex, sortedMonths.size());

        List<MonthlySummaryResponseModel.MonthSummary> summaries = new ArrayList<>();
        for (YearMonth ym : lastSix) {
            double[] arr = monthToIncomeExpense.get(ym);
            summaries.add(new MonthlySummaryResponseModel.MonthSummary(ym, arr[0], arr[1]));
        }

        MonthlySummaryResponseModel responseModel =
                new MonthlySummaryResponseModel(summaries);

        presenter.present(responseModel);
    }
}
