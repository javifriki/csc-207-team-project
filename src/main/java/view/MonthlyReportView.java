package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.LocalDate;


import interface_adaptor.ViewManagerViewModel;
import interface_adaptor.monthly_report.MonthlyReportController;
import interface_adaptor.monthly_report.MonthlyReportViewModel;

import static interface_adaptor.monthly_report.MonthlyReportViewModel.getLineGraph;

/**
 * User Interface for the Monthly Report Use Case.
 * Responsible for displaying final monthly report on the screen.
 * Reads data from MonthlyReportViewModel.
 */

public class MonthlyReportView extends JPanel implements ActionListener, PropertyChangeListener {

    private final MonthlyReportViewModel viewModel;
    private final MonthlyReportController controller;
    private final ViewManagerViewModel viewManagerViewModel;


    private JComboBox<Integer> yearBox;
    private JComboBox<Integer> monthBox;
    private JLabel wrappedLineChartImage;
    private JLabel wrappedPieChartImage;


    LocalDate today = LocalDate.now();
    int currentYear = today.getYear();
    int currentMonth = today.getMonthValue();


    public MonthlyReportView(MonthlyReportViewModel viewModel,
                             MonthlyReportController controller,
                             ViewManagerViewModel viewManagerViewModel) throws IOException, InterruptedException {
        this.viewModel = viewModel;
        this.controller = controller;
        this.viewManagerViewModel = viewManagerViewModel;
        this.viewManagerViewModel.addPropertyChangeListener(this);

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
            BufferedImage lineChartImage = null;
            try {
                lineChartImage = getLineGraph(currentYear, currentMonth);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            wrappedLineChartImage = new JLabel();
            wrappedLineChartImage.setIcon(new ImageIcon(lineChartImage));
            lineGraphPanel.add(wrappedLineChartImage);

            JPanel pieChartPanel = new JPanel();
            BufferedImage pieChartImage = null;
            try {
                pieChartImage = MonthlyReportViewModel.getPieChart(currentYear, currentMonth);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            wrappedPieChartImage = new JLabel();
            wrappedPieChartImage.setIcon(new ImageIcon(pieChartImage));
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
        try {
            controller.showMonthlyReport(year, month);
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("viewChange".equals(evt.getPropertyName())) {
            String newView = (String) evt.getNewValue();
            if ("monthlyReport".equals(newView)) {
                int year = today.getYear();
                int month = today.getMonthValue();
                try {
                    controller.showMonthlyReport(year, month);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}