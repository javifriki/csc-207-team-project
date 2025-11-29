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
        JButton addAccountButton = new JButton("Add Account");

        // Style buttons
        styleButton(addTransactionButton);
        styleButton(monthlySummaryButton);
        styleButton(addAccountButton);

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

        addAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewManagerViewModel.setState("addAccount");
                viewManagerViewModel.firePropertyChange("viewChange");
            }
        });

        add(addTransactionButton);
        add(monthlySummaryButton);
        add(addAccountButton);
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