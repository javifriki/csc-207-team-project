package data_access;

import entity.Account;
import entity.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.account.AccountDataAccessInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountDataAccessObject implements AccountDataAccessInterface {
    private Map<String, Account> accountNumberToAccount = new HashMap<>();
    private Map<String, Account.AccountType> accountTypeHashMap = new HashMap<>();
    private Map<String, Transaction.TransactionType> transactionTypeHashMap = new HashMap<>();
    private Map<String, Transaction.TransactionCategory> transactionCategoryHashMap = new HashMap<>();
    private String filename;

    public AccountDataAccessObject(String filename) {
        this.filename = filename;

        for (Account.AccountType type : Account.AccountType.values()) {
            accountTypeHashMap.put(type.toString(), type);
        }

        for (Transaction.TransactionType type : Transaction.TransactionType.values()) {
            transactionTypeHashMap.put(type.toString(), type);
        }

        for (Transaction.TransactionCategory type : Transaction.TransactionCategory.values()) {
            transactionCategoryHashMap.put(type.toString(), type);
        }

        loadAllAccountData();
    }

    // JSON Structure
    /*
    {
        "ACC12345": {
            "accountType": "CHECKING",
            "balance": 1250.50,
            "transactionList": [
                {
                    "amount": 200.0,
                    "transactionType": "INCOME",
                    "transactionCategory": "SALARY",
                    "date": "2025-10-01"
                },
                {
                    "amount": 50.0,
                    "transactionType": "EXPENSE",
                    "transactionCategory": "DINING",
                    "date": "2025-10-02"
                },
                {
                    "amount": 100.0,
                    "transactionType": "EXPENSE",
                    "transactionCategory": "GROCERIES",
                    "date": "2025-10-03"
                }
            ]
        },
        "ACC67890": {
            "accountType": "SAVINGS",
            "balance": 5000.00,
            "transactionList": [
                {
                    "amount": 300.0,
                    "transactionType": "INCOME",
                    "transactionCategory": "SALARY",
                    "date": "2025-10-05"
                },
                {
                    "amount": 100.0,
                    "transactionType": "EXPENSE",
                    "transactionCategory": "UTILITIES",
                    "date": "2025-10-08"
                }
            ]
        }
    }
     */

    // Read all accounts from JSON file and load them all into HashMap
    private void loadAllAccountData() {
        try {
            String contents = Files.readString(Path.of(this.filename)); // in JSON format
            JSONObject accountNumbersToAccount = new JSONObject(contents);
            for (String accountNumberKey : accountNumbersToAccount.keySet()) {
                JSONObject accountObj = accountNumbersToAccount.getJSONObject(accountNumberKey);

                Account.AccountType accountType = this.accountTypeHashMap.get(accountObj.getString("accountType"));
                double balance = accountObj.getDouble("balance");

                List<Transaction> transactions = new ArrayList<>();
                JSONArray transactionList = accountObj.getJSONArray("transactionList");
                for (int i = 0; i < transactionList.length(); i++) {
                    JSONObject transactionObj = transactionList.getJSONObject(i);
                    double transactionAmount = transactionObj.getDouble("amount");
                    Transaction.TransactionType transactionType = this.transactionTypeHashMap.get(transactionObj.getString("transactionType"));
                    Transaction.TransactionCategory transactionCategory = this.transactionCategoryHashMap.get(transactionObj.getString("transactionCategory"));
                    LocalDate transactionDate = LocalDate.parse(transactionObj.getString("date"));

                    Transaction theTransaction = new Transaction(
                            transactionAmount,
                            transactionType,
                            transactionCategory,
                            transactionDate,
                            accountNumberKey
                    );
                    transactions.add(theTransaction);
                }

                Account account = new Account(accountNumberKey, accountType, transactions, balance);
                this.accountNumberToAccount.put(accountNumberKey, account);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to Read Account Data JSON file");
        }
    }

    // Save a single newly added account into JSON file
    private void saveAccountData() {
        try {
            JSONObject baseRoot = new JSONObject();
            for (String accountNumberKey : this.accountNumberToAccount.keySet()) {
                Account account = this.accountNumberToAccount.get(accountNumberKey);
                Account.AccountType accountType = account.getAccountType();
                List<Transaction> transactionList = account.getAccountTransactions();
                double accountBalance = account.getAccountBalance();

                JSONObject accountObj = new JSONObject();
                accountObj.put("accountType", accountType);
                accountObj.put("balance", accountBalance);

                JSONArray transactionsJSONArr = new JSONArray();
                for (Transaction transaction : transactionList) {
                    double transactionAmount = transaction.getTransactionAmount();
                    String transactionType = transaction.getTransactionType().toString();
                    String transactionCategory = transaction.getTransactionCategory().toString();
                    String transactionDate = transaction.getTransactionDate().toString();

                    JSONObject transactionObj = new JSONObject();
                    transactionObj.put("amount", transactionAmount);
                    transactionObj.put("transactionType", transactionType);
                    transactionObj.put("transactionCategory", transactionCategory);
                    transactionObj.put("date", transactionDate);

                    transactionsJSONArr.put(transactionObj);
                }

                accountObj.put("transactionList", transactionsJSONArr);

                baseRoot.put(accountNumberKey, accountObj);
            }

            Files.writeString(Path.of(this.filename), baseRoot.toString(4));

        } catch (IOException e) {
            throw new RuntimeException("Failed to write Account Data to the JSON file");
        }
    }

    // Adds a new Account
    @Override
    public void saveAccount(Account account) {
        this.accountNumberToAccount.put(account.getAccountNumber(), account);
        this.saveAccountData();
    }

    @Override
    public Account getAccount(String accountNumber) {
        return this.accountNumberToAccount.get(accountNumber);
    }

    @Override
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accountNumberToAccount.values());
    }
}
