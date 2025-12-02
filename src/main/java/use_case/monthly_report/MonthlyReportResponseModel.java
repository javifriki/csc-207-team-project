package use_case.monthly_report;

import java.awt.image.BufferedImage;

public class MonthlyReportResponseModel {
    private final int year;
    private final int month;
    private final BufferedImage lineGraph;
    private final BufferedImage pieChart;

    public MonthlyReportResponseModel(int year,
                                      int month,
                                      BufferedImage lineGraph,
                                      BufferedImage pieChart) {
        this.year = year;
        this.month = month;
        this.lineGraph = lineGraph;
        this.pieChart = pieChart;
    }
    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
    public BufferedImage getLineGraph() {
        return lineGraph;
    }
    public BufferedImage getPieChart() {
        return pieChart;
    }
}
