package interface_adaptor.add_transaction;

import entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class AddTransactionState {
    private List<Transaction> allTransactionsList = new ArrayList<>();
    private String popupMessage = "";

    public List<Transaction> getAllTransactionsList() {
        return this.allTransactionsList;
    }

    public String getPopupMessage() {
        return this.popupMessage;
    }

    public void addTransactionToList(Transaction transaction) {
        this.allTransactionsList.add(transaction);
    }

    public void setPopupMessage(String message) {
        this.popupMessage = message;
    }
}
