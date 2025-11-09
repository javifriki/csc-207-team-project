package data_access;

import entity.Account;
import use_case.account.AccountDataAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountDataAccessObject implements AccountDataAccessInterface {
    // Temporary Storage DAO, can incorporate persistent storage later by storing ALL ACCOUNT class fields in JSON
    private Map<String, Account> accountNumberToAccount = new HashMap<>();

    @Override
    public void saveAccount(Account account) {
        this.accountNumberToAccount.put(account.getAccountNumber(), account);
    }

    @Override
    public Account getAccount(String accountNumber) {
        return this.accountNumberToAccount.get(accountNumber);
    }

    @Override
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accountNumberToAccount.values());
    }
}
