package use_case.monthly_report;

import data_access.AccountDataAccessObject;
import entity.Account;
import org.json.JSONObject;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import entity.Account;
import entity.Transaction;
import use_case.account.AccountDataAccessInterface;
import data_access.AccountDataAccessObject;

public class MonthlyReportState {
    private String year;
    private String month;

    // line graph
    private List<String> dates;
    private List<Double> balances;
    private Map<String, Double> lineGraph;

    // pie chart
    private List<String> labels;
    private List<Double> values;
    private Map<String, Double> pieChart;


    public String getYear() {
        return year;
    }
    public String getMonth() {
        return month;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public void setMonth(String month) {
        this.month = month;
    }

    public void loadData(AccountDataAccessInterface accountDataAccessInterface)  {
        List<Account> allAccounts = accountDataAccessInterface.getAllAccounts();
        Map<Integer, Map<Integer, Double>> yearTransactions = new HashMap<>();
        for (Account account : allAccounts) {
            List<Transaction> getTransaction = account.getAccountTransactions();
            for (Transaction transaction : getTransaction) {
                int year = transaction.getTransactionDate().getYear();
                // if year not in
                int month = transaction.getTransactionDate().getMonthValue();
                double amount = transaction.getTransactionAmount();
                String category = String.valueOf(transaction.getTransactionCategory());
                // new month JSON object
                // new line graph JSON Object
                // new category total JSON Object

                JSONObject yearObj = yearTransactions.optJSONObject(yearKey);
                if (yearObj == null) {
                    yearObj = new JSONObject();
                    yearTransactions.put(yearKey, yearObj);
                }

                // ---------- LEVEL 2: MONTH ----------
                JSONObject monthObj = yearObj.optJSONObject(monthKey);
                if (monthObj == null) {
                    monthObj = new JSONObject();
                    yearObj.put(monthKey, monthObj);
                }

                // ---------- LEVEL 3a: GRAPH DATA (day -> amount) ----------
                JSONObject graphData = monthObj.optJSONObject("graph_data");
                if (graphData == null) {
                    graphData = new JSONObject();
                    monthObj.put("graph_data", graphData);
                }

            }
        }

        ArrayList<String> balances = new ArrayList<>();
        Map<String, Double> lineGraph = new HashMap<>();
    }

    public class MonthlyReportData {
        private final Map<Integer, Double> graphData = new HashMap<>();
        private final Map<String, Double> categoryTotals = new HashMap<>();

        public Map<Integer, Double> getGraphData() {
            return graphData;
        }

        public Map<String, Double> getCategoryTotals() {
            return categoryTotals;
        }

        public void addTransaction(int day, double amount, String category) {
            // Update line graph data (day -> sum of amounts)
            graphData.merge(day, amount, Double::sum);

            // Update pie chart data (ignore SALARY)
            if (!"SALARY".equalsIgnoreCase(category)) {
                categoryTotals.merge(category, amount, Double::sum);
            }
        }
    }


//     public Account(String accountNumber, AccountType type, double balance) {
//        this.accountNumber = accountNumber;
//        this.type = type;
//        this.transactions = new ArrayList<>();
//        this.balance = balance;
//    }
//
//    public String getAccountNumber() {
//        return this.accountNumber;
//    }
//
//    public AccountType getAccountType() {
//        return this.type;
//    }
//
//    public List<Transaction> getAccountTransactions() {
//        return this.transactions;
//    }
//
//    public double getAccountBalance() {
//        return this.balance;
//    }

//    public Transaction(double amount, TransactionType type, TransactionCategory category, LocalDate date, String accountNumber) {
//        this.amount = amount;
//        this.type = type;
//        this.category = category;
//        this.date = date;
//        this.accountNumber = accountNumber;
//    }
//
//    public double getTransactionAmount() {
//        return this.amount;
//    }
//
//    public TransactionType getTransactionType() {
//        return this.type;
//    }
//
//    public TransactionCategory getTransactionCategory() {
//        return this.category;
//    }
//
//    public LocalDate getTransactionDate() {
//        return this.date;
//    }
//
//    public String getAccountNumber() {
//        return this.accountNumber;
//    }


}
