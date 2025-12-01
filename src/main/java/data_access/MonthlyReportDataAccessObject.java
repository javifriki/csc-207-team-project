package data_access;

import entity.Account;
import entity.Transaction;
import org.json.JSONObject;
import use_case.account.AccountDataAccessInterface;

import java.util.List;

public class MonthlyReportDataAccessObject {

    private static final JSONObject yearTransactions = new JSONObject();
    //{
    //  "2024": {
    //            "1": {
    //                     "graph_data":  {
    //                                       "1": 0.0
    //                                       "2": 0.0
    //                                          ...
    //                                       "31": 0.0
    //                                          },
    //                     "category_totals":  {
    //                                          "DINING": 0.0,
    //                                          "GROCERIES": 0.0,
    //                                              ...
    //                                          "HEALTHCARE": 0.0
    //                                          }
    //                  },
    //             "2": {.....
    //                  }
    //              ...
    //             "12": {
    //                      ...
    //                    }
    //
    //            },
    //  "2025": {
    //           ...
    //           },
    //  "2026": {
    //          ...
    //          }
    //}

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

                JSONObject monthObj = getMonthData(year, month);

                JSONObject graphData = monthObj.getJSONObject("graph_data");
                double currentVal = graphData.getDouble(String.valueOf(day));

                // Line graph: income adds, expense subtracts
                if (t.getTransactionType() == Transaction.TransactionType.EXPENSE) {
                    graphData.put(String.valueOf(day), currentVal - Math.abs(amount));
                } else {
                    graphData.put(String.valueOf(day), currentVal + Math.abs(amount));
                }

                // Pie chart: expenses only
                if (t.getTransactionType() == Transaction.TransactionType.EXPENSE) {
                    JSONObject catTotals = monthObj.getJSONObject("category_totals");
                    double catVal = catTotals.optDouble(category, 0.0);
                    catTotals.put(category, catVal + Math.abs(amount));
                }
            }
        }
    }

    public static JSONObject getMonthData(int year, int month) {
        String yearKey = String.valueOf(year);
        String monthKey = String.valueOf(month);

        if (!yearTransactions.has(yearKey)) {
            initializeEmptyYear(year);
        }

        JSONObject yearObj = yearTransactions.getJSONObject(yearKey);

        if (!yearObj.has(monthKey)) {
            initializeEmptyMonth(yearObj, month);
        }

        return yearObj.getJSONObject(monthKey);
    }

    private static void initializeEmptyYear(int year) {
        JSONObject yearObj = new JSONObject();

        for (int month = 1; month <= 12; month++) {
            initializeEmptyMonth(yearObj, month);
        }

        yearTransactions.put(String.valueOf(year), yearObj);
    }

    private static void initializeEmptyMonth(JSONObject yearObj, int month) {
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

    public static double getOpeningBalance(int year, int month) {
        if (month <= 1) {
            return 0.0;
        }

        // Opening balance of this month = opening of previous month + net change in previous month
        double prevOpening = getOpeningBalance(year, month - 1);

        JSONObject prevMonth = getMonthData(year, month - 1);
        JSONObject prevGraph = prevMonth.getJSONObject("graph_data");

        double deltaPrevMonth = 0.0;
        for (int day = 1; day <= 31; day++) {
            String key = String.valueOf(day);
            deltaPrevMonth += prevGraph.optDouble(key, 0.0);
        }

        return prevOpening + deltaPrevMonth;
    }
}
