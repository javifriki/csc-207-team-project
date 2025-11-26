package interface_adaptor.monthly_report;

import data_access.MonthlyReportDataAccessObject;
import org.json.JSONObject;


import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.List;

import use_case.monthly_report.MonthlyReportAPI;

public class MonthlyReportViewModel {
    private String title;
    private BufferedImage lineGraph;
    private BufferedImage pieChart;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private static final List<String> pie_chart_categories = List.of(
            "DINING",
            "GROCERIES",
            "RENT",
            "UTILITIES",
            "ENTERTAINMENT",
            "TRANSPORTATION",
            "MORTGAGE",
            "HEALTHCARE"
    );


    public JSONObject getMonthData(int year, int month) {
        return MonthlyReportDataAccessObject.getMonthData(year, month);
    }

    public static JSONObject getLineData(int year, int month) {
        JSONObject month_data = MonthlyReportDataAccessObject.getMonthData(year, month);
        return month_data.getJSONObject("graph_data");
    }

    public static JSONObject getPieData(int year, int month) {
        JSONObject month_data = MonthlyReportDataAccessObject.getMonthData(year, month);
        return month_data.getJSONObject("category_totals");
    }

    public static BufferedImage getLineGraph(int year, int month) throws IOException, InterruptedException {
        return MonthlyReportAPI.generateLineChart(year, month);
    }

    public static BufferedImage getPieChart(int year, int month) throws IOException, InterruptedException {
        return MonthlyReportAPI.generatePieChart(year, month);
    }


    public void setLineChartImage(BufferedImage lineGraph) {
        this.lineGraph = lineGraph;
    }

    public void setPieChartImage(BufferedImage pieChart) {
        this.pieChart = pieChart;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }


}