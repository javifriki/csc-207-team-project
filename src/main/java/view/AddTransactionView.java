package view;

import entity.Transaction;
import interface_adaptor.add_transaction.AddTransactionController;
import interface_adaptor.add_transaction.AddTransactionState;
import interface_adaptor.add_transaction.AddTransactionViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.Arrays;

public class AddTransactionView extends JPanel implements PropertyChangeListener {

    private AddTransactionController addTransactionController;
    private AddTransactionViewModel addTransactionViewModel;

    private JTextField transactionAmountText = new JTextField(10);
    private JTextField transactionAccountNumber = new JTextField(10);
    private JComboBox<String> transactionTypeDropDown = new JComboBox<>(
            Arrays.stream(Transaction.TransactionType.values())
                    .map(type -> type.toString())
                    .toArray(String[]::new));
    private JComboBox<String> transactionCategoryDropDown = new JComboBox<>(
            Arrays.stream(Transaction.TransactionCategory.values())
                    .map(type -> type.toString())
                    .toArray(String[]::new));
    private JButton addTransactionButton = new JButton("Add Transaction");

    public AddTransactionView(AddTransactionViewModel addTransactionViewModel) {
        this.addTransactionViewModel = addTransactionViewModel;
        this.addTransactionViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel amountPanel = new JPanel();
        JLabel amountLabel = new JLabel("Transaction Amount: ");
        amountPanel.add(amountLabel);
        amountPanel.add(transactionAmountText);

        JPanel accNumPanel = new JPanel();
        JLabel accNumLabel = new JLabel("Account Number: ");
        accNumPanel.add(accNumLabel);
        accNumPanel.add(transactionAccountNumber);

        JPanel typePanel = new JPanel();
        JLabel typeLabel = new JLabel("Transaction Type: ");
        typePanel.add(typeLabel);
        typePanel.add(transactionTypeDropDown);

        JPanel categoryPanel = new JPanel();
        JLabel categoryLabel = new JLabel("Transaction Category: ");
        categoryPanel.add(categoryLabel);
        categoryPanel.add(transactionCategoryDropDown);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addTransactionButton);

        addTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(transactionAmountText.getText());
                String accountNumber = transactionAccountNumber.getText();
                String type = (String) transactionTypeDropDown.getSelectedItem();
                String category = (String) transactionCategoryDropDown.getSelectedItem();
                String date = LocalDate.now().toString();

                addTransactionController.execute(
                        amount,
                        accountNumber,
                        type,
                        category,
                        date
                );
            }
        });

        this.add(amountPanel);
        this.add(accNumPanel);
        this.add(typePanel);
        this.add(categoryPanel);
        this.add(buttonPanel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("transactionSuccess")) {
            AddTransactionState addTransactionState = (AddTransactionState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, addTransactionState.getPopupMessage());
        } else if (evt.getPropertyName().equals("transactionFail")) {
            AddTransactionState addTransactionState = (AddTransactionState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, addTransactionState.getPopupMessage());
        }
    }

    public void setAddTransactionController(AddTransactionController addTransactionController) {
        this.addTransactionController = addTransactionController;
    }

    public String getViewName() {
        return this.addTransactionViewModel.getViewName();
    }
}
