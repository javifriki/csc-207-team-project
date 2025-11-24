package interface_adaptor.monthly_report;

import data_access.MonthlyReportDataAccessObject;
import entity.Account;
import org.json.JSONObject;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import entity.Transaction;
import use_case.account.AccountDataAccessInterface;
import use_case.monthly_report.MonthlyReportAPI;

public class MonthlyReportViewModel {


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

    public static BufferedImage getLineChart(int year, int month) throws IOException, InterruptedException {
        return MonthlyReportAPI.generateLineChart(year, month);
    }

    public static BufferedImage getPieChart(int year, int month) throws IOException, InterruptedException {
        return MonthlyReportAPI.generatePieChart(year, month);
    }


}