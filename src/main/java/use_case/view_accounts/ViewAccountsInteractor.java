package use_case.view_accounts;

import entity.Account;
import use_case.account.AccountDataAccessInterface;
import java.util.List;

public class ViewAccountsInteractor implements ViewAccountsInputBoundary {
    private final AccountDataAccessInterface accountDataAccessObject;
    private final ViewAccountsOutputBoundary presenter;

    public ViewAccountsInteractor(AccountDataAccessInterface accountDataAccessObject,
                                  ViewAccountsOutputBoundary presenter) {
        this.accountDataAccessObject = accountDataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(ViewAccountsInputData inputData) {
        List<Account> accounts = accountDataAccessObject.getAllAccounts();
        ViewAccountsOutputData outputData = new ViewAccountsOutputData(accounts);
        presenter.present(outputData);
    }
}


