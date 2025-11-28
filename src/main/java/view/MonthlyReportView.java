package view;

import interface_adaptor.monthly_report.MonthlyReportController;
import interface_adaptor.monthly_report.MonthlyReportState;
import interface_adaptor.monthly_report.MonthlyReportViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.time.LocalDate;

public class MonthlyReportView extends JPanel implements ActionListener {

    private final MonthlyReportViewModel viewModel;
    private MonthlyReportController controller;

    private JComboBox<Integer> yearBox;
    private JComboBox<Integer> monthBox;
    private JLabel lineChartLabel;
    private JLabel pieChartLabel;

    private final LocalDate today = LocalDate.now();

    private static final int CHART_WIDTH = 450;
    private static final int CHART_HEIGHT = 300;

    public MonthlyReportView(MonthlyReportViewModel viewModel) {
        this.viewModel = viewModel;

        this.viewModel.addPropertyChangeListener(evt -> {
            if ("monthlyReport".equals(evt.getPropertyName())) {
                updateFromState();
            }
        });

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

        yearBox.addActionListener(this);
        monthBox.addActionListener(this);

        JPanel linePanel = new JPanel();
        lineChartLabel = new JLabel();
        linePanel.add(lineChartLabel);

        JPanel piePanel = new JPanel();
        pieChartLabel = new JLabel();
        piePanel.add(pieChartLabel);

        setLayout(new BorderLayout());
        add(selectBar, BorderLayout.NORTH);

        JPanel charts = new JPanel(new GridLayout(1, 2));
        charts.add(linePanel);
        charts.add(piePanel);
        add(charts, BorderLayout.CENTER);
    }

    public void setMonthlyReportController(MonthlyReportController controller) {
        this.controller = controller;

        int year = today.getYear();
        int month = today.getMonthValue();
        yearBox.setSelectedItem(year);
        monthBox.setSelectedItem(month);
        controller.showMonthlyReport(year, month);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (controller == null) {
            return;
        }

        int year;
        int month;
        try {
            year = (int) yearBox.getSelectedItem();
            month = (int) monthBox.getSelectedItem();
        } catch (Exception ex) {
            year = today.getYear();
            month = today.getMonthValue();
        }
        controller.showMonthlyReport(year, month);
    }

    private ImageIcon resizeImage(BufferedImage img, int width, int height) {
        Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    private void updateFromState() {
        MonthlyReportState state = viewModel.getState();

        if (state.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(
                    this,
                    state.getErrorMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (state.getLineChartImage() != null) {
            lineChartLabel.setIcon(
                    resizeImage(state.getLineChartImage(), CHART_WIDTH, CHART_HEIGHT)
            );
        }

        if (state.getPieChartImage() != null) {
            pieChartLabel.setIcon(
                    resizeImage(state.getPieChartImage(), CHART_WIDTH, CHART_HEIGHT)
            );
        }

        revalidate();
        repaint();
    }

    public String getViewName() {
        return viewModel.getViewName();
    }

}
