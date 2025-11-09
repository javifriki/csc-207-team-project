package use_case.add_transaction;

public class AddTransactionOutputData {
    private String message;
    private String accountNumber;
    private double transactionAmount;
    private double newBalance;

    // Transaction of $amount added successfully to $accountNumber. New balance is $newBalance. Additional messages: $message

    public AddTransactionOutputData(String message, String accountNumber, double transactionAmount, double newBalance) {
        this.message = message;
        this.accountNumber = accountNumber;
        this.transactionAmount = transactionAmount;
        this.newBalance = newBalance;
    }

    public String getMessage() {
        return this.message;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public double getTransactionAmount() {
        return this.transactionAmount;
    }

    public double getNewBalance() {
        return this.newBalance;
    }
}
