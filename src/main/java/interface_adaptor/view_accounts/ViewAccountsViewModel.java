package interface_adaptor.view_accounts;

import interface_adaptor.ViewModel;

public class ViewAccountsViewModel extends ViewModel<ViewAccountsState> {
    public ViewAccountsViewModel() {
        super("viewAccounts");
        setState(new ViewAccountsState());
    }
}


