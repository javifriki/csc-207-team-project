package use_case.monthly_report;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;


import use_case.monthly_report.MonthlyReportAPI;
import use_case.monthly_report.MonthlyReportViewModel;
import use_case.monthly_report.MonthlyReportController;

/**
 * User Interface for the Monthly Report Use Case.
 * Responsible for displaying final monthly report on the screen.
 * Reads data from MonthlyReportViewModel.
 */



public class MonthlyReportView extends JPanel {

    private final MonthlyReportViewModel viewModel;
    private final MonthlyReportController controller;
    private final MonthlyReportAPI chartService;

    private BufferedImage lineChartImage;
    private BufferedImage pieChartImage;

    private final JComboBox<Integer> yearBox;
    private final JComboBox<Integer> monthBox;
    private final JButton loadButton;

    private final String accountNumber; //

    public MonthlyReportView(MonthlyReportViewModel viewModel,
                             MonthlyReportController controller,
                             MonthlyReportAPI chart,
                             String accountNumber) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.chartService = chart;
        this.accountNumber = accountNumber;

        setLayout(null); // or any layout you prefer

        yearBox = new JComboBox<>(new Integer[]{2023, 2024, 2025});
        monthBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        loadButton = new JButton("Load Monthly Report");

        yearBox.setBounds(20, 20, 100, 25);
        monthBox.setBounds(130, 20, 80, 25);
        loadButton.setBounds(220, 20, 200, 25);

        add(yearBox);
        add(monthBox);
        add(loadButton);

        // When the user clicks the button, call the controller
        // Listen to ViewModel changes
    }

}