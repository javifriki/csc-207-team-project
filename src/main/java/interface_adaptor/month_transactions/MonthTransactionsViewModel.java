package interface_adaptor.month_transactions;

import java.time.LocalDate;
import java.util.List;

public class MonthTransactionsViewModel {

    public static class TransactionRowViewModel {
        public final LocalDate date;
        public final String accountNumber;
        public final String type;
        public final String category;
        public final double amount;
        public final boolean isIncome;

        public TransactionRowViewModel(LocalDate date, String accountNumber,
                                       String type, String category,
                                       double amount, boolean isIncome) {
            this.date = date;
            this.accountNumber = accountNumber;
            this.type = type;
            this.category = category;
            this.amount = amount;
            this.isIncome = isIncome;
        }
    }

    private List<TransactionRowViewModel> rows;

    public List<TransactionRowViewModel> getRows() {
        return rows;
    }

    public void setRows(List<TransactionRowViewModel> rows) {
        this.rows = rows;
    }
}
