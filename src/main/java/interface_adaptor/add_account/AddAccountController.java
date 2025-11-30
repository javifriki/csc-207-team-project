package interface_adaptor.add_account;

import entity.Account;
import use_case.account.AddAccountInputBoundary;
import use_case.account.AddAccountInputData;
import use_case.account.AddAccountInteractor;

import java.util.HashMap;
import java.util.Map;

public class AddAccountController {
    private final AddAccountInputBoundary addAccountInteractor;
    private Map<String, Account.AccountType> accountTypeMap = new HashMap<>();

    public AddAccountController(AddAccountInputBoundary addAccountInteractor) {
        this.addAccountInteractor = addAccountInteractor;

        for (Account.AccountType accountType : Account.AccountType.values()) {
            accountTypeMap.put(accountType.toString(), accountType);
        }
    }

    public void execute(String accountNumber, String accountType) {
        Account.AccountType theAccountType = accountTypeMap.get(accountType.toUpperCase());
        final AddAccountInputData addAccountInputData = new AddAccountInputData(
                accountNumber,
                theAccountType
        );
        this.addAccountInteractor.execute(addAccountInputData);
    }
}
