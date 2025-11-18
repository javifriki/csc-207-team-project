package interface_adaptor.add_transaction;

import interface_adaptor.ViewModel;

public class AddTransactionViewModel extends ViewModel<AddTransactionState> {
    public AddTransactionViewModel() {
        setState(new AddTransactionState());
    }
}
