package interface_adaptor.add_account;

import entity.Account;
import use_case.account.AddAccountOutputBoundary;
import use_case.account.AddAccountOutputData;

public class AddAccountPresenter implements AddAccountOutputBoundary {

    private final AddAccountViewModel addAccountViewModel;

    public AddAccountPresenter(AddAccountViewModel addAccountViewModel) {
        this.addAccountViewModel = addAccountViewModel;
    }

    @Override
    public void prepareAccountSuccessView(AddAccountOutputData addAccountOutputData) {
        final String theAccountNumber = addAccountOutputData.getAddedAccountNumber();
        final Account.AccountType theAccountType = addAccountOutputData.getAddedAccountType();
        AddAccountState addAccountState = this.addAccountViewModel.getState();
        addAccountState.setPopupMessage("Account number " + theAccountNumber +
                " added successfully. It is a " + theAccountType.toString() + " account.");
        this.addAccountViewModel.firePropertyChange("addAccountSuccessful");
    }

    @Override
    public void prepareAccountFailView(String errorMessage) {
        AddAccountState addAccountState = this.addAccountViewModel.getState();
        addAccountState.setPopupMessage(errorMessage);
        this.addAccountViewModel.firePropertyChange("addAccountFail");
    }
}
