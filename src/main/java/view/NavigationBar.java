package view;

import interface_adaptor.ViewManagerViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationBar extends JPanel {
    private final ViewManagerViewModel viewManagerViewModel;

    public NavigationBar(ViewManagerViewModel viewManagerViewModel) {
        this.viewManagerViewModel = viewManagerViewModel;
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        setBackground(Color.WHITE);

        JButton addTransactionButton = new JButton("Add Transaction");
        JButton monthlySummaryButton = new JButton("Monthly Summary");
        JButton addAssetAndLiabilityButton = new JButton("Add Asset/Liability");
        JButton assetAndLiabilityListButton = new JButton("Asset/Liability List");
        JButton addAccountButton = new JButton("Add Account");
        JButton monthlyReportButton = new JButton("Monthly Report");

        // Style buttons
        styleButton(addTransactionButton);
        styleButton(monthlySummaryButton);
        styleButton(addAssetAndLiabilityButton);
        styleButton(assetAndLiabilityListButton);
        styleButton(monthlyReportButton);
        styleButton(addAccountButton);
        styleButton(monthlyReportButton);

        // Add action listeners
        addTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewManagerViewModel.setState("addTransaction");
                viewManagerViewModel.firePropertyChange("viewChange");
            }
        });

        monthlySummaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewManagerViewModel.setState("monthlySummary");
                viewManagerViewModel.firePropertyChange("viewChange");
            }
        });

        addAssetAndLiabilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewManagerViewModel.setState("addAssetAndLiability");
                viewManagerViewModel.firePropertyChange("viewChange");
            }
        });

        assetAndLiabilityListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewManagerViewModel.setState("assetAndLiabilityApplyRate");
                viewManagerViewModel.firePropertyChange("viewChange");
            }
        });

        addAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewManagerViewModel.setState("addAccount");
                viewManagerViewModel.firePropertyChange("viewChange");
            }
        });

        monthlyReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewManagerViewModel.setState("monthlyReport");
                viewManagerViewModel.firePropertyChange("viewChange");
            }
        });

        add(addTransactionButton);
        add(monthlySummaryButton);
        add(addAssetAndLiabilityButton);
        add(assetAndLiabilityListButton);
        add(addAccountButton);
        add(monthlyReportButton);
    }

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(150, 35));
        button.setFont(new Font(button.getFont().getName(), Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBackground(new Color(240, 240, 240));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}