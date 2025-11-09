package use_case.add_transaction;

import entity.Transaction;

import java.time.LocalDate;

public class AddTransactionInputData {
    private final double amount;
    private final String accountNumber;
    private final Transaction.TransactionType type;
    private final Transaction.TransactionCategory category;
    private final LocalDate date;

    public AddTransactionInputData(double amount,
                                   String accountNumber,
                                   Transaction.TransactionType type,
                                   Transaction.TransactionCategory category,
                                   LocalDate date) {
        this.amount = amount;
        this.date = date;
        this.accountNumber = accountNumber;
        this.type = type;
        this.category = category;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public Transaction.TransactionType getType() {
        return this.type;
    }

    public Transaction.TransactionCategory getCategory() {
        return this.category;
    }

    public LocalDate getDate() {
        return this.date;
    }
}
