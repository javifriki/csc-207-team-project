package interface_adaptor.add_transaction;

import entity.Transaction;
import use_case.add_transaction.AddTransactionOutputBoundary;
import use_case.add_transaction.AddTransactionOutputData;

public class AddTransactionPresenter implements AddTransactionOutputBoundary {

    private final AddTransactionViewModel addTransactionViewModel;

    public AddTransactionPresenter(AddTransactionViewModel addTransactionViewModel) {
        this.addTransactionViewModel = addTransactionViewModel;
    }

    @Override
    public void prepareTransactionSuccessView(AddTransactionOutputData addTransactionOutputData) {
        // with a popup, display the success message with info of the transaction
        String accountNumber = addTransactionOutputData.getAccountNumber();
        double newBalance = addTransactionOutputData.getNewBalance();
        Transaction transaction = addTransactionOutputData.getNewTransaction();

        AddTransactionState addTransactionState = this.addTransactionViewModel.getState();
        addTransactionState.setPopupMessage(
                "Transaction added successfully to account " + accountNumber
                        + "with new balance of " + newBalance
                        + "\nTransaction Type: " + transaction.getTransactionType().toString()
                        + "\nTransaction Amount: " + transaction.getTransactionAmount()
        );

        addTransactionState.addTransactionToList(transaction);
        this.addTransactionViewModel.firePropertyChange("transactionSuccess");
    }

    @Override
    public void prepareTransactionFailView(String errorMessage) {
        // with a popup, display the errorMessage as to why the transaction failed.
        AddTransactionState addTransactionState = this.addTransactionViewModel.getState();
        addTransactionState.setPopupMessage(errorMessage);
        this.addTransactionViewModel.firePropertyChange("transactionFail");
    }
}
