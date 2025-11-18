package use_case.month_transactions;

import java.time.LocalDate;
import java.util.List;

public class MonthTransactionsResponseModel {

    public static class TransactionData {
        public final LocalDate date;
        public final double amount;
        public final String accountNumber;
        public final String type;     // e.g. "INCOME" / "SPENDING"
        public final String category;

        public TransactionData(LocalDate date, double amount,
                               String accountNumber, String type, String category) {
            this.date = date;
            this.amount = amount;
            this.accountNumber = accountNumber;
            this.type = type;
            this.category = category;
        }
    }

    private final List<TransactionData> transactions;

    public MonthTransactionsResponseModel(List<TransactionData> transactions) {
        this.transactions = transactions;
    }

    public List<TransactionData> getTransactions() {
        return transactions;
    }
}
