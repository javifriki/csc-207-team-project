package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;


import interface_adaptor.monthly_report.MonthlyReportController;
import interface_adaptor.monthly_report.MonthlyReportViewModel;

/**
 * User Interface for the Monthly Report Use Case.
 * Responsible for displaying final monthly report on the screen.
 * Reads data from MonthlyReportViewModel.
 */

public class MonthlyReportView extends JPanel implements ActionListener {

    private final MonthlyReportViewModel viewModel;
    private final MonthlyReportController controller;


    private final JComboBox<Integer> yearBox;
    private final JComboBox<Integer> monthBox;

    LocalDate today = LocalDate.now();
    int currentYear = today.getYear();
    int currentMonth = today.getMonthValue();


    public MonthlyReportView(MonthlyReportViewModel viewModel,
                             MonthlyReportController controller) throws IOException, InterruptedException {
        this.viewModel = viewModel;
        this.controller = controller;

        JPanel selectBar = new JPanel();
        Integer[] years = {2023, 2024, 2025};
        yearBox = new JComboBox<>(years);
        JLabel yearLabel = new JLabel("Year:");

        Integer[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        monthBox = new JComboBox<>(months);
        JLabel monthLabel = new JLabel("Month:");

        selectBar.add(yearLabel);
        selectBar.add(yearBox);
        selectBar.add(Box.createHorizontalStrut(10));
        selectBar.add(monthLabel);
        selectBar.add(monthBox);

        JPanel lineGraphPanel = new JPanel();
        BufferedImage lineChartImage = MonthlyReportViewModel.getLineChart(currentYear, currentMonth);
        JLabel wrappedLineChartImage = new JLabel(new ImageIcon(lineChartImage));
        lineGraphPanel.add(wrappedLineChartImage);

        JPanel pieChartPanel = new JPanel();
        BufferedImage pieChartImage = MonthlyReportViewModel.getPieChart(currentYear, currentMonth);
        JLabel wrappedPieChartImage = new JLabel(new ImageIcon(pieChartImage));
        pieChartPanel.add(wrappedPieChartImage);

        yearBox.addActionListener(e -> actionPerformed(e));
        monthBox.addActionListener(e -> actionPerformed(e));

        this.add(selectBar);
        this.add(lineGraphPanel);
        this.add(pieChartPanel);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        int year;
        int month;
        try {
            year = (int) yearBox.getSelectedItem();
            month = (int) monthBox.getSelectedItem();
        } catch (Exception exception) {
            year = today.getYear();
            month = today.getMonthValue();
        }
        controller.showMonthlyReport(year, month);
    }
}