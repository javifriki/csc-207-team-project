package data_access;

import entity.Account;
import entity.Transaction;
import use_case.account.AccountDataAccessInterface;

import org.json.JSONObject;
import java.util.*;

public class MonthlyReportDataAccessObject {

    private static final JSONObject yearTransactions = new JSONObject();

    private static final List<String> PIE_CATEGORIES = List.of(
            "DINING",
            "GROCERIES",
            "RENT",
            "UTILITIES",
            "ENTERTAINMENT",
            "TRANSPORTATION",
            "MORTGAGE",
            "HEALTHCARE"
    );

    public void init(AccountDataAccessInterface accountGateway) {

        int currentYear = java.time.LocalDate.now().getYear();
        String yearKey = String.valueOf(currentYear);

        JSONObject yearObj = new JSONObject();
        yearTransactions.put(yearKey, yearObj);

        for (int month = 1; month <= 12; month++) {
            JSONObject monthObj = new JSONObject();

            JSONObject graph = new JSONObject();
            for (int day = 1; day <= 31; day++) {
                graph.put(String.valueOf(day), 0.0);
            }
            monthObj.put("graph_data", graph);

            JSONObject categoryTotals = new JSONObject();
            for (String cat : PIE_CATEGORIES) {
                categoryTotals.put(cat, 0.0);
            }
            monthObj.put("category_totals", categoryTotals);

            yearObj.put(String.valueOf(month), monthObj);
        }
    }

    public void load(AccountDataAccessInterface accountGateway) {
        List<Account> accounts = accountGateway.getAllAccounts();

        for (Account acc : accounts) {
            for (Transaction t : acc.getAccountTransactions()) {

                int year = t.getTransactionDate().getYear();
                int month = t.getTransactionDate().getMonthValue();
                int day = t.getTransactionDate().getDayOfMonth();
                double amount = t.getTransactionAmount();
                String category = t.getTransactionCategory().toString();

                JSONObject monthObj = yearTransactions
                        .getJSONObject(String.valueOf(year))
                        .getJSONObject(String.valueOf(month));


                JSONObject graphData = monthObj.getJSONObject("graph_data");
                double currentVal = graphData.getDouble(String.valueOf(day));
                graphData.put(String.valueOf(day), currentVal + amount);

                JSONObject catTotals = monthObj.getJSONObject("category_totals");
                double catVal = catTotals.optDouble(category, 0.0);
                catTotals.put(category, catVal + amount);
            }
        }
    }

    public JSONObject getYearData(int year) {
        return yearTransactions.getJSONObject(String.valueOf(year));
    }

    public static JSONObject getMonthData(int year, int month) {
        return yearTransactions
                .getJSONObject(String.valueOf(year))
                .getJSONObject(String.valueOf(month));
    }
}

