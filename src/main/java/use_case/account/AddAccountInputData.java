package use_case.account;

import entity.Account;

public class AddAccountInputData {
    private final String accountNumber;
    private final Account.AccountType accountType;

    public AddAccountInputData(String accountNumber, Account.AccountType accountType) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
    }

    public String getAddedAccountNumber() {
        return this.accountNumber;
    }

    public Account.AccountType getAddedAccountType() {
        return this.accountType;
    }
}
