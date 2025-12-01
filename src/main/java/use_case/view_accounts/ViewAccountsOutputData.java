package use_case.view_accounts;

import entity.Account;
import java.util.List;

public class ViewAccountsOutputData {
    private final List<Account> accounts;

    public ViewAccountsOutputData(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}


