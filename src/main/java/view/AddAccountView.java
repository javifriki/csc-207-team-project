package view;

import entity.Account;
import entity.Transaction;
import interface_adaptor.add_account.AddAccountController;
import interface_adaptor.add_account.AddAccountState;
import interface_adaptor.add_account.AddAccountViewModel;
import interface_adaptor.add_transaction.AddTransactionController;
import interface_adaptor.add_transaction.AddTransactionState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

public class AddAccountView extends JPanel implements PropertyChangeListener {

    private AddAccountController addAccountController;
    private AddAccountViewModel addAccountViewModel;

    private JTextField accountIdTextField = new JTextField(10);
    private JComboBox<String> accountTypeDropDown = new JComboBox<>(
            Arrays.stream(Account.AccountType.values())
                    .map(type -> type.toString())
                    .toArray(String[]::new));
    private JButton addAccountButton = new JButton("Add Account");

    public AddAccountView(AddAccountViewModel addAccountViewModel) {
        this.addAccountViewModel = addAccountViewModel;
        this.addAccountViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel accountIdLabel = new JLabel("Account ID");
        JPanel accountIdPanel = new JPanel();
        accountIdPanel.add(accountIdLabel);
        accountIdPanel.add(accountIdTextField);

        JLabel accountTypeLabel = new JLabel("Account Type");
        JPanel accountTypePanel = new JPanel();
        accountTypePanel.add(accountTypeLabel);
        accountTypePanel.add(accountTypeDropDown);

        JPanel addAccountButtonPanel = new JPanel();
        addAccountButtonPanel.add(addAccountButton);

        addAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountIdTextField.getText();
                String accountType = (String) accountTypeDropDown.getSelectedItem();
                addAccountController.execute(accountNumber, accountType);
            }
        });

        this.add(accountIdPanel);
        this.add(accountTypePanel);
        this.add(addAccountButtonPanel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("addAccountSuccessful")) {
            AddAccountState addAccountState = (AddAccountState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, addAccountState.getPopupMessage());
        } else if (evt.getPropertyName().equals("addAccountFail")) {
            AddAccountState addAccountState = (AddAccountState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, addAccountState.getPopupMessage());
        }
    }

    public void setAddAccountController(AddAccountController addAccountController) {
        this.addAccountController = addAccountController;
    }

    public String getViewName() {
        return this.addAccountViewModel.getViewName();
    }
}
