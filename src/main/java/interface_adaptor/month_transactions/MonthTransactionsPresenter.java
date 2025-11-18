package interface_adaptor.month_transactions;

import use_case.month_transactions.MonthTransactionsOutputBoundary;
import use_case.month_transactions.MonthTransactionsResponseModel;

import java.util.List;
import java.util.stream.Collectors;

public class MonthTransactionsPresenter implements MonthTransactionsOutputBoundary {

    private final MonthTransactionsViewModel viewModel;

    public MonthTransactionsPresenter(MonthTransactionsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(MonthTransactionsResponseModel responseModel) {
        List<MonthTransactionsViewModel.TransactionRowViewModel> rows =
                responseModel.getTransactions().stream()
                        .map(td -> new MonthTransactionsViewModel.TransactionRowViewModel(
                                td.date,
                                td.accountNumber,
                                td.type,
                                td.category,
                                td.amount,
                                td.type.equals("INCOME")
                        ))
                        .collect(Collectors.toList());

        viewModel.setRows(rows);
        // again, you can notify Swing listeners here
    }
}
