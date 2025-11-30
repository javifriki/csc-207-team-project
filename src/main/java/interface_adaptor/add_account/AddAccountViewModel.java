package interface_adaptor.add_account;

import interface_adaptor.ViewModel;

public class AddAccountViewModel extends ViewModel<AddAccountState> {

    public AddAccountViewModel() {
        super("addAccount");
        setState(new AddAccountState());
    }
}
