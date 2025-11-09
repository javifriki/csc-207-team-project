package use_case.account;

import entity.Account;

import java.util.List;

public interface AccountDataAccessInterface {
    void saveAccount(Account account);
    Account getAccount(String accountNumber);
    List<Account> getAllAccounts();
}
