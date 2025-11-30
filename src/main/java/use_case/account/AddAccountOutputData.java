package use_case.account;

import entity.Account;

public class AddAccountOutputData {
    private final String addedAccountNumber;
    private final Account.AccountType addedAccountType;

    public AddAccountOutputData(String addedAccountNumber, Account.AccountType addedAccountType) {
        this.addedAccountNumber = addedAccountNumber;
        this.addedAccountType = addedAccountType;
    }

    public String getAddedAccountNumber() {
        return this.addedAccountNumber;
    }

    public Account.AccountType getAddedAccountType() {
        return this.addedAccountType;
    }
}
