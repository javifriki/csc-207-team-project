package data_access;

import entity.Account;
import use_case.account.AccountDataAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// FOR UNIT TESTING PURPOSES ONLY
public class InMemoryAccountDataAccessObject implements AccountDataAccessInterface {

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

    @Override
    public List<String> getAllAccountNumbers() {
        return new ArrayList<>(accountNumberToAccount.keySet());
    }
}
