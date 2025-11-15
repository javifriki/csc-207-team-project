package entity;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public enum AccountType {
        CHECKING,
        SAVINGS,
        CREDIT
    }

    private final String accountNumber;
    private final AccountType type;
    private final List<Transaction> transactions;
    private final List<AssetAndLiability> assetAndLiabilities;
    private double balance;

    public Account(String accountNumber, AccountType type, List<Transaction> transactions, double balance) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.assetAndLiabilities = new ArrayList<>();
        this.transactions = transactions;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public AccountType getAccountType() {
        return this.type;
    }

    public List<Transaction> getAccountTransactions() {
        return this.transactions;
    }

    public List<AssetAndLiability> getAccountAssetAndLiabilities() {
        return assetAndLiabilities;
    }

    public double getAccountBalance() {
        return this.balance;
    }

    public void applyTransaction(Transaction transaction) {
        if (transaction.getTransactionType() == Transaction.TransactionType.INCOME) {
            this.balance += transaction.getTransactionAmount();
        } else {
            this.balance -= transaction.getTransactionAmount();
        }

        this.transactions.add(transaction); // Add the Transaction to this Account
    }

    public void applyAssetAndLiability(AssetAndLiability assetAndLiability) {
        this.assetAndLiabilities.add(assetAndLiability);
    }
}
