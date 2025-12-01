package interface_adaptor.view_accounts;

import entity.Account;
import java.util.ArrayList;
import java.util.List;

public class ViewAccountsState {
    private List<Account> accounts = new ArrayList<>();

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}


