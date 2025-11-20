package use_case.monthly_report;

import javax.swing.*;
package view;

/**
 * User Interface for the Monthly Report Use Case.
 * Responsible for displaying final monthly report on the screen.
 * Reads data from MonthlyReportViewModel.
 */


import data_access.QuickChartService;
import use_case.monthly_report.MonthlyReportViewModel;
import use_case.monthly_report.MonthlyReportController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class MonthlyReportView extends JPanel {

    private final MonthlyReportViewModel viewModel;
    private final MonthlyReportController controller;
    private final QuickChartService chartService;

    private BufferedImage lineChartImage;
    private BufferedImage pieChartImage;

    private final JComboBox<Integer> yearBox;
    private final JComboBox<Integer> monthBox;
    private final JButton loadButton;

    private final String accountNumber; // assume you have this from login

    public MonthlyReportView(MonthlyReportViewModel viewModel,
                             MonthlyReportController controller,
                             QuickChartService chartService,
                             String accountNumber) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.chartService = chartService;
        this.accountNumber = accountNumber;

        setLayout(null); // or any layout you prefer

        yearBox = new JComboBox<>(new Integer[]{2023, 2024, 2025});
        monthBox = new JComboBox<>(new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12});
        loadButton = new JButton("Load Monthly Report");

        yearBox.setBounds(20, 20, 100, 25);
        monthBox.setBounds(130, 20, 80, 25);
        loadButton.setBounds(220, 20, 200, 25);

        add(yearBox);
        add(monthBox);
        add(loadButton);

        // When the user clicks the button, call the controller
        loadButton.addActionListener(e -> onLoadReport());

        // Listen to ViewModel changes
        this.viewModel.addPropertyChangeListener(evt -> refreshCharts());
    }

    private void onLoadReport() {
        int year = (int) yearBox.getSelectedItem();
        int month = (int) monthBox.getSelectedItem();
        controller.showMonthlyReport(accountNumber, year, month);
    }

    /** Called when the ViewModel's state changes (presenter fired property changed). */
    private void refreshCharts() {
        MonthlyReportState state = viewModel.getState();

        // If there was an error, you can show a dialog
        if (state.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage(),
                    "Monthly Report Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Build day -> amount map for line chart
        Map<Integer, Double> dayToAmount = new HashMap<>();
        List<String> dates = state.getLineDates();
        List<Double> amounts = state.getLineAmounts();
        if (dates != null && amounts != null) {
            for (int i = 0; i < dates.size() && i < amounts.size(); i++) {
                try {
                    int day = Integer.parseInt(dates.get(i));
                    dayToAmount.put(day, amounts.get(i));
                } catch (NumberFormatException ignored) {}
            }
        }

        // Build category -> amount map for pie chart
        Map<String, Double> categoryToAmount = new LinkedHashMap<>();
        List<String> labels = state.getPieLabels();
        List<Double> values = state.getPieValues();
        if (labels != null && values != null) {
            for (int i = 0; i < labels.size() && i < values.size(); i++) {
                categoryToAmount.put(labels.get(i), values.get(i));
            }
        }

        try {
            lineChartImage = chartService.generateLineChart(
                    dayToAmount,
                    "Daily Spending " + state.getYear() + "/" + state.getMonth()
            );

            pieChartImage = chartService.generatePieChart(
                    categoryToAmount,
                    "Spending by Category " + state.getYear() + "/" + state.getMonth()
            );

            repaint();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to load charts from API.",
                    "Chart Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (lineChartImage != null) {
            g.drawImage(lineChartImage, 20, 70, 400, 200, null);
        }
        if (pieChartImage != null) {
            g.drawImage(pieChartImage, 20, 290, 400, 200, null);
        }
    }
}
