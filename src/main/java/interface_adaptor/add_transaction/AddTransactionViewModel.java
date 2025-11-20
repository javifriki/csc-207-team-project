package interface_adaptor.add_transaction;

import interface_adaptor.ViewModel;

public class AddTransactionViewModel extends ViewModel<AddTransactionState> {
    public AddTransactionViewModel() {
        super("addTransaction"); // sets the add transaction view model NAME
        setState(new AddTransactionState());
    }
}
