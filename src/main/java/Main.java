import data_access.AccountDataAccessObject;
import entity.Account;
import entity.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AccountDataAccessObject accountDataAccessObject = new AccountDataAccessObject("accounts.json");

        List<Transaction> newTransactions = new ArrayList<>();
        Transaction transaction1 = new Transaction(1059, Transaction.TransactionType.INCOME, Transaction.TransactionCategory.ENTERTAINMENT, LocalDate.of(2024, 1, 20), "bfa");
        newTransactions.add(transaction1);
        Account newAccount = new Account("ad1234", Account.AccountType.SAVINGS, newTransactions, 6767);
        accountDataAccessObject.saveAccount(newAccount);
    }
}
