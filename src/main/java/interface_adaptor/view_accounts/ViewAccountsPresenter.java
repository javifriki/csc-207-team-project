package interface_adaptor.view_accounts;

import use_case.view_accounts.ViewAccountsOutputBoundary;
import use_case.view_accounts.ViewAccountsOutputData;

public class ViewAccountsPresenter implements ViewAccountsOutputBoundary {
    private final ViewAccountsViewModel viewModel;

    public ViewAccountsPresenter(ViewAccountsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(ViewAccountsOutputData outputData) {
        ViewAccountsState state = viewModel.getState();
        state.setAccounts(outputData.getAccounts());
        viewModel.firePropertyChange("accountsUpdated");
    }
}


