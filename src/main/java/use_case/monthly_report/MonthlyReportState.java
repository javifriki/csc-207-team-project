package use_case.monthly_report;

import data_access.AccountDataAccessObject;
import entity.Account;
import org.json.JSONObject;


import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import entity.Account;
import entity.Transaction;
import use_case.account.AccountDataAccessInterface;
import data_access.AccountDataAccessObject;

public class MonthlyReportState {


    JSONObject yearTransactions = new JSONObject();

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

    public static class currentDate {
        static LocalDate today = LocalDate.now();
        static int currentYear = today.getYear();
        static int currentMonth = today.getMonthValue();
        static int currentDay = today.getDayOfMonth();
    }

    public class getAllData {
        return JSONObject yearTransactions;
    }

    public class getYearData(int year) {
        String str_year = String.valueOf(year);
        return yearTransactions.getJSONObject(str_year);
    }



    public void initData(AccountDataAccessInterface accountDataAccessInterface) {

        String yearKey = String.valueOf(currentDate.currentYear);

        JSONObject yearObj = yearTransactions.optJSONObject(yearKey);
        if (yearObj == null) {
            yearObj = new JSONObject();
            yearTransactions.put(yearKey, yearObj);
        }
        for (int month = 1; month <= 12; month++) {
            JSONObject monthObj = new JSONObject();
            // graph_data with each day initialized to be 0
            monthObj.put("graph_data", new JSONObject());
            JSONObject daily_balance = new JSONObject();
            for (int day = 1; day <= 31; day++) {
                String str_day = String.valueOf(day);
                daily_balance.put(str_day, 0.0);
            }

            // category amounts with categories initialized
            JSONObject categoryTotals = new JSONObject();
            for (String cat : pie_chart_categories) {
                categoryTotals.put(cat, 0.0);
            }
            monthObj.put("category_totals", categoryTotals);

            yearObj.put(String.valueOf(month), monthObj);
        }

    }

    public void loadData(AccountDataAccessInterface accountDataAccessInterface) {

        List<Account> allAccounts = accountDataAccessInterface.getAllAccounts();

        for (Account account : allAccounts) {

            List<Transaction> getTransaction = account.getAccountTransactions();

            for (Transaction transaction : getTransaction) {
                int year = transaction.getTransactionDate().getYear();
                int month = transaction.getTransactionDate().getMonthValue();
                int day = transaction.getTransactionDate().getDayOfMonth();
                double amount = transaction.getTransactionAmount();
                String category = String.valueOf(transaction.getTransactionCategory());

                String yearKey = String.valueOf(year);
                String monthKey = String.valueOf(month);
                String dayKey = String.valueOf(day);

                JSONObject month_data = yearTransactions.optJSONObject(yearKey).optJSONObject(monthKey);
                // graph data, for each day, make a key and the value is the balance

                JSONObject graphData = month_data.optJSONObject("graph_data");
                double current = graphData.optDouble(dayKey);
                double updated = current + amount;
                graphData.put(dayKey, updated);

                // category totals, for each transaction, add to the category's value by the transaction amount
                JSONObject categoryTotals = month_data.optJSONObject("category_totals");
                double current_category = categoryTotals.optDouble(category);
                double updated_category = current_category + amount;
                categoryTotals.put(category, updated_category);


            }
        }
    }

}