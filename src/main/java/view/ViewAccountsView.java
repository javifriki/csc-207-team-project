package view;

import entity.Account;
import interface_adaptor.view_accounts.ViewAccountsController;
import interface_adaptor.view_accounts.ViewAccountsTableModel;
import interface_adaptor.view_accounts.ViewAccountsViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ViewAccountsView extends JPanel {
    private ViewAccountsController viewAccountsController;
    private final ViewAccountsViewModel viewAccountsViewModel;
    private final JTable accountsTable;
    private final JLabel totalBalanceLabel;
    private final JButton refreshButton;
    EmptyBorder padding = new EmptyBorder(30, 30, 30, 30);

    public ViewAccountsView(ViewAccountsViewModel viewAccountsViewModel) {
        this.viewAccountsViewModel = viewAccountsViewModel;

        ViewAccountsTableModel tableModel = new ViewAccountsTableModel(viewAccountsViewModel.getState());
        this.accountsTable = new JTable(tableModel);
        refreshButton = new JButton("Refresh Data");
        refreshButton.addActionListener(e -> refreshData());
        refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("All Accounts");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 18));
        titleLabel.setBorder(padding);
        add(titleLabel);

        JScrollPane table = new JScrollPane(accountsTable);
        table.setBorder(padding);
        add(table);

        JPanel totalPanel = new JPanel();
        totalBalanceLabel = new JLabel("Total Balance: $0.00");
        totalPanel.add(totalBalanceLabel);
        totalPanel.add(refreshButton);
        totalPanel.setBorder(padding);
        add(totalPanel);
    }

    public void setViewAccountsController(ViewAccountsController viewAccountsController) {
        this.viewAccountsController = viewAccountsController;
        if (this.viewAccountsController != null) {
            refreshData();
        }
    }

    public void refreshData() {
        if (viewAccountsController != null) {
            viewAccountsController.execute();

            ViewAccountsTableModel newModel = new ViewAccountsTableModel(viewAccountsViewModel.getState());
            accountsTable.setModel(newModel);

            // Calculate total balance
            double totalBalance = 0.0;
            for (Account account : viewAccountsViewModel.getState().getAccounts()) {
                totalBalance += account.getAccountBalance();
            }

            java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0.00");
            totalBalanceLabel.setText("Total Balance: $" + df.format(totalBalance));

            revalidate();
            repaint();
        }
    }

    public String getViewName() {
        return this.viewAccountsViewModel.getViewName();
    }
}

