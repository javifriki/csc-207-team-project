package use_case.add_transaction;

import entity.Transaction;

public class AddTransactionOutputData {
    private String accountNumber;
    private double newBalance;
    private Transaction newTransaction;

    // Transaction of $amount added successfully to $accountNumber. New balance is $newBalance. Additional messages: $message

    public AddTransactionOutputData(String accountNumber, double newBalance, Transaction newTransaction) {
        this.accountNumber = accountNumber;
        this.newBalance = newBalance;
        this.newTransaction = newTransaction;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public Transaction getNewTransaction() {
        return this.newTransaction;
    }

    public double getNewBalance() {
        return this.newBalance;
    }
}
