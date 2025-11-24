package view;

import interface_adaptor.monthly_summary.MonthlySummaryController;
import interface_adaptor.monthly_summary.MonthlySummaryState;
import interface_adaptor.monthly_summary.MonthlySummaryViewModel;
import interface_adaptor.monthly_summary.MonthlySummaryViewModel.MonthBarViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MonthlySummaryView extends JPanel implements PropertyChangeListener {

    private MonthlySummaryController monthlySummaryController;
    private MonthlySummaryViewModel monthlySummaryViewModel;
    private HistogramPanel histogramPanel;

    public MonthlySummaryView(MonthlySummaryViewModel monthlySummaryViewModel) {
        this.monthlySummaryViewModel = monthlySummaryViewModel;
        this.monthlySummaryViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Monthly Summary - Income vs Spending");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        histogramPanel = new HistogramPanel();
        histogramPanel.setPreferredSize(new Dimension(800, 400));
        histogramPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton refreshButton = new JButton("Refresh Data");
        refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        refreshButton.addActionListener(e -> {
            if (monthlySummaryController != null) {
                monthlySummaryController.loadSummary();
            }
        });

        this.add(Box.createVerticalStrut(10));
        this.add(titleLabel);
        this.add(Box.createVerticalStrut(10));
        this.add(histogramPanel);
        this.add(Box.createVerticalStrut(10));
        this.add(refreshButton);
        this.add(Box.createVerticalStrut(10));
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("summaryUpdated")) {
            MonthlySummaryState state = (MonthlySummaryState) evt.getNewValue();
            histogramPanel.updateData(state.getBars());
            repaint();
        }
    }

    public void setMonthlySummaryController(MonthlySummaryController monthlySummaryController) {
        this.monthlySummaryController = monthlySummaryController;
    }

    public String getViewName() {
        return this.monthlySummaryViewModel.getViewName();
    }

    /**
     * Custom panel that draws a histogram showing income and spending for each month
     */
    private static class HistogramPanel extends JPanel {
        private List<MonthBarViewModel> bars;

        public HistogramPanel() {
            this.bars = List.of();
            setBackground(Color.WHITE);
        }

        public void updateData(List<MonthBarViewModel> bars) {
            this.bars = bars;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (bars == null || bars.isEmpty()) {
                g2d.setFont(new Font("Arial", Font.PLAIN, 14));
                g2d.setColor(Color.GRAY);
                String message = "No data available. Click 'Refresh Data' to load monthly summary.";
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(message)) / 2;
                int y = getHeight() / 2;
                g2d.drawString(message, x, y);
                return;
            }
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int padding = 60;
            int chartWidth = panelWidth - 2 * padding;
            int chartHeight = panelHeight - 2 * padding;

            // Find max value for scaling
            double maxValue = 0;
            for (MonthBarViewModel bar : bars) {
                maxValue = Math.max(maxValue, Math.max(bar.income, bar.spending));
            }
            if (maxValue == 0) maxValue = 1; // Avoid division by zero

            int numBars = bars.size();
            int barWidth = chartWidth / (numBars * 3); // Each month has 2 bars (income + spending) + spacing
            int spacing = barWidth / 2;

            // Draw axes
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            // Y-axis
            g2d.drawLine(padding, padding, padding, padding + chartHeight);
            // X-axis
            g2d.drawLine(padding, padding + chartHeight, padding + chartWidth, padding + chartHeight);

            // Draw Y-axis label
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString("Amount ($)", 10, padding / 2);

            // Draw bars and labels
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");
            for (int i = 0; i < bars.size(); i++) {
                MonthBarViewModel bar = bars.get(i);
                int x = padding + i * (barWidth * 2 + spacing * 2) + spacing;

                // Draw income bar (green)
                int incomeHeight = (int) (bar.income / maxValue * chartHeight);
                int incomeY = padding + chartHeight - incomeHeight;
                g2d.setColor(new Color(76, 175, 80)); // Green
                g2d.fillRect(x, incomeY, barWidth, incomeHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, incomeY, barWidth, incomeHeight);
                // Draw spending bar (red)
                int spendingHeight = (int) (bar.spending / maxValue * chartHeight);
                int spendingY = padding + chartHeight - spendingHeight;
                g2d.setColor(new Color(244, 67, 54)); // Red
                g2d.fillRect(x + barWidth + spacing / 2, spendingY, barWidth, spendingHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x + barWidth + spacing / 2, spendingY, barWidth, spendingHeight);

                // Draw month label
                String monthLabel = bar.month.format(formatter);
                FontMetrics fm = g2d.getFontMetrics();
                int labelX = x + barWidth - fm.stringWidth(monthLabel) / 2;
                g2d.setColor(Color.BLACK);
                g2d.drawString(monthLabel, labelX, padding + chartHeight + 20);

                // Draw value labels on bars
                g2d.setFont(new Font("Arial", Font.PLAIN, 10));
                if (incomeHeight > 15) {
                    String incomeLabel = String.format("$%.0f", bar.income);
                    int incomeLabelX = x + (barWidth - fm.stringWidth(incomeLabel)) / 2;
                    g2d.setColor(Color.WHITE);
                    g2d.drawString(incomeLabel, incomeLabelX, incomeY + incomeHeight / 2 + 5);
                }
                if (spendingHeight > 15) {
                    String spendingLabel = String.format("$%.0f", bar.spending);
                    int spendingLabelX = x + barWidth + spacing / 2 + (barWidth - fm.stringWidth(spendingLabel)) / 2;
                    g2d.setColor(Color.WHITE);
                    g2d.drawString(spendingLabel, spendingLabelX, spendingY + spendingHeight / 2 + 5);
                }
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            }
            // Draw Y-axis scale
            g2d.setColor(Color.BLACK);
            int numTicks = 5;
            for (int i = 0; i <= numTicks; i++) {
                int y = padding + chartHeight - (i * chartHeight / numTicks);
                double value = maxValue * i / numTicks;
                String label = String.format("$%.0f", value);
                FontMetrics fm = g2d.getFontMetrics();
                int labelX = padding - fm.stringWidth(label) - 5;
                g2d.drawString(label, labelX, y + 5);
                g2d.drawLine(padding - 5, y, padding, y);
            }

            // Draw legend
            int legendX = padding + chartWidth - 150;
            int legendY = padding - 30;
            g2d.setColor(new Color(76, 175, 80));
            g2d.fillRect(legendX, legendY, 15, 15);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(legendX, legendY, 15, 15);
            g2d.drawString("Income", legendX + 20, legendY + 12);

            g2d.setColor(new Color(244, 67, 54));
            g2d.fillRect(legendX + 70, legendY, 15, 15);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(legendX + 70, legendY, 15, 15);
            g2d.drawString("Spending", legendX + 90, legendY + 12);
        }
    }
}