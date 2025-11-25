package view;

import interface_adaptor.monthly_summary.MonthlySummaryController;
import interface_adaptor.monthly_summary.MonthlySummaryState;
import interface_adaptor.monthly_summary.MonthlySummaryViewModel;
import interface_adaptor.monthly_summary.MonthlySummaryViewModel.MonthBarViewModel;
import interface_adaptor.month_transactions.MonthTransactionsController;
import use_case.month_transactions.MonthTransactionsResponseModel;
import use_case.month_transactions.MonthTransactionsOutputBoundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MonthlySummaryView extends JPanel implements PropertyChangeListener {

    private MonthlySummaryController monthlySummaryController;
    private MonthlySummaryViewModel monthlySummaryViewModel;
    private HistogramPanel histogramPanel;
    private MonthTransactionsController monthTransactionsController;
    private JPanel transactionListPanel;
    private JScrollPane transactionScrollPane;
    private MonthlySummaryTransactionPresenter transactionPresenter;

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

        // Transaction list panel (initially hidden)
        transactionListPanel = new JPanel();
        transactionListPanel.setLayout(new BoxLayout(transactionListPanel, BoxLayout.Y_AXIS));
        transactionListPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create scroll pane for transaction list with fixed height for max 5 rows
        JScrollPane transactionScrollPane = new JScrollPane(transactionListPanel);
        transactionScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        transactionScrollPane.setVisible(false);
        transactionScrollPane.setBorder(BorderFactory.createTitledBorder("Transactions"));

        // Set fixed size to show maximum 5 transaction rows (each row ~35px + padding)
        transactionScrollPane.setPreferredSize(new Dimension(800, 200));
        transactionScrollPane.setMaximumSize(new Dimension(800, 200));
        transactionScrollPane.setMinimumSize(new Dimension(800, 200));

        // Configure scroll pane
        transactionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        transactionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        transactionScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JButton refreshButton = new JButton("Refresh Data");
        refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        refreshButton.addActionListener(e -> {
            if (monthlySummaryController != null) {
                monthlySummaryController.loadSummary();
            }
        });

        // Initialize the custom transaction presenter
        transactionPresenter = new MonthlySummaryTransactionPresenter();

        // Store reference to scroll pane for visibility control
        this.transactionScrollPane = transactionScrollPane;

        this.add(Box.createVerticalStrut(10));
        this.add(titleLabel);
        this.add(Box.createVerticalStrut(10));
        this.add(histogramPanel);
        this.add(Box.createVerticalStrut(10));
        this.add(transactionScrollPane);
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

    public void setMonthTransactionsController(MonthTransactionsController monthTransactionsController) {
        this.monthTransactionsController = monthTransactionsController;
    }

    public MonthTransactionsOutputBoundary getTransactionPresenter() {
        return transactionPresenter;
    }

    public String getViewName() {
        return this.monthlySummaryViewModel.getViewName();
    }

    public void displayTransactionsForMonth(List<MonthTransactionsResponseModel.TransactionData> transactions, YearMonth month) {
        transactionListPanel.removeAll();

        if (transactions.isEmpty()) {
            JLabel noDataLabel = new JLabel("No transactions found for " + month.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            noDataLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            noDataLabel.setPreferredSize(new Dimension(760, 35));
            transactionListPanel.add(noDataLabel);
        } else {
            JLabel monthLabel = new JLabel("Transactions for " + month.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            monthLabel.setFont(new Font(monthLabel.getFont().getName(), Font.BOLD, 14));
            monthLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            monthLabel.setPreferredSize(new Dimension(760, 25));
            transactionListPanel.add(monthLabel);
            transactionListPanel.add(Box.createVerticalStrut(5));

            for (MonthTransactionsResponseModel.TransactionData transaction : transactions) {
                JPanel transactionRow = createTransactionRow(transaction);
                transactionListPanel.add(transactionRow);
                // Reduced spacing between rows to fit more in the scroll area
                transactionListPanel.add(Box.createVerticalStrut(2));
            }
        }

        transactionScrollPane.setVisible(true);
        transactionListPanel.revalidate();
        transactionListPanel.repaint();
        transactionScrollPane.revalidate();
        transactionScrollPane.repaint();
        this.revalidate();
        this.repaint();
    }

    private JPanel createTransactionRow(MonthTransactionsResponseModel.TransactionData transaction) {
        JPanel row = new JPanel(new BorderLayout());
        // Fixed height for consistent row sizing (35px per row)
        row.setPreferredSize(new Dimension(760, 35));
        row.setMaximumSize(new Dimension(760, 35));
        row.setMinimumSize(new Dimension(760, 35));
        row.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Left side: Category and Account
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel categoryLabel = new JLabel(transaction.category);
        JLabel accountLabel = new JLabel("(" + transaction.accountNumber + ")");
        accountLabel.setFont(accountLabel.getFont().deriveFont(Font.ITALIC));
        accountLabel.setForeground(Color.GRAY);
        leftPanel.add(categoryLabel);
        leftPanel.add(accountLabel);

        // Right side: Amount with color coding
        JLabel amountLabel = new JLabel(String.format("$%.2f", transaction.amount));
        amountLabel.setFont(amountLabel.getFont().deriveFont(Font.BOLD));

        // Color coding: green for income, red for spending
        if ("INCOME".equals(transaction.type)) {
            amountLabel.setForeground(new Color(76, 175, 80)); // Green
        } else {
            amountLabel.setForeground(new Color(244, 67, 54)); // Red
        }

        row.add(leftPanel, BorderLayout.WEST);
        row.add(amountLabel, BorderLayout.EAST);

        // Add subtle background
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        return row;
    }

    /**
     * Custom presenter that displays transactions directly in the MonthlySummaryView
     */
    private class MonthlySummaryTransactionPresenter implements MonthTransactionsOutputBoundary {
        private YearMonth currentMonth;

        public void setCurrentMonth(YearMonth month) {
            this.currentMonth = month;
        }

        @Override
        public void present(MonthTransactionsResponseModel responseModel) {
            displayTransactionsForMonth(responseModel.getTransactions(), currentMonth);
        }
    }

    /**
     * Custom panel that draws a histogram showing income and spending for each month.
     * Supports clicking on month bars to display detailed transaction list.
     */
    private class HistogramPanel extends JPanel {
        private List<MonthBarViewModel> bars;

        public HistogramPanel() {
            this.bars = List.of();
            setBackground(Color.WHITE);

            // Add mouse click listener
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    handleMouseClick(e.getX(), e.getY());
                }
            });
        }

        public void updateData(List<MonthBarViewModel> bars) {
            this.bars = bars;
            repaint();
        }

        private void handleMouseClick(int mouseX, int mouseY) {
            if (bars == null || bars.isEmpty()) {
                return;
            }

            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int padding = 60;
            int chartWidth = panelWidth - 2 * padding;
            int chartHeight = panelHeight - 2 * padding;

            int numBars = bars.size();
            int barWidth = chartWidth / (numBars * 3);
            int spacing = barWidth / 2;

            // Check if click is within the chart area
            if (mouseY < padding || mouseY > padding + chartHeight) {
                return;
            }

            // Determine which month was clicked
            for (int i = 0; i < bars.size(); i++) {
                MonthBarViewModel bar = bars.get(i);
                int x = padding + i * (barWidth * 2 + spacing * 2) + spacing;

                // Check if click is within this month's bar area (either income or spending bar)
                if (mouseX >= x && mouseX <= x + barWidth * 2 + spacing / 2) {
                    // Month clicked - load transactions
                    if (monthTransactionsController != null) {
                        transactionPresenter.setCurrentMonth(bar.month);
                        monthTransactionsController.selectMonth(bar.month);
                    }
                    break;
                }
            }
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