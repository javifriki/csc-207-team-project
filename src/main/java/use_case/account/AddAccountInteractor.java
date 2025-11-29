package use_case.account;

import entity.Account;
import entity.Transaction;
import use_case.add_transaction.AddTransactionOutputBoundary;

import java.util.ArrayList;
import java.util.List;

public class AddAccountInteractor implements AddAccountInputBoundary {

    private final AccountDataAccessInterface accountDataAccessObject;
    private final AddAccountOutputBoundary addAccountPresenter;

    public AddAccountInteractor (AccountDataAccessInterface accountDataAccessObject, AddAccountOutputBoundary addAccountPresenter) {
        this.accountDataAccessObject = accountDataAccessObject;
        this.addAccountPresenter = addAccountPresenter;
    }

    @Override
    public void execute(AddAccountInputData addAccountInputData) {
        final String accountNumber = addAccountInputData.getAddedAccountNumber();
        final Account.AccountType accountType = addAccountInputData.getAddedAccountType();

        final List<String> allAccountNumbers = this.accountDataAccessObject.getAllAccountNumbers();
        if (allAccountNumbers.contains(accountNumber)) {
            this.addAccountPresenter.prepareAccountFailView("Account number " + accountNumber + " already exists.");
        } else {
            Account newCreatedAccount = new Account(accountNumber, accountType, new ArrayList<>(), 0.0);
            this.accountDataAccessObject.saveAccount(newCreatedAccount);

            AddAccountOutputData addAccountOutputData = new AddAccountOutputData(accountNumber, accountType);
            this.addAccountPresenter.prepareAccountSuccessView(addAccountOutputData);
        }

    }

}
