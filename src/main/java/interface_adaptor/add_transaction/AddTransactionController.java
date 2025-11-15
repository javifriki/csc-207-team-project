package interface_adaptor.add_transaction;

import entity.Transaction;
import use_case.add_transaction.AddTransactionInputData;
import use_case.add_transaction.AddTransactionInteractor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AddTransactionController {
    private final AddTransactionInteractor addTransactionInteractor;
    private Map<String, Transaction.TransactionType> transactionTypeHashMap = new HashMap<>();
    private Map<String, Transaction.TransactionCategory> transactionCategoryHashMap = new HashMap<>();

    public AddTransactionController(AddTransactionInteractor addTransactionInteractor) {
        this.addTransactionInteractor = addTransactionInteractor;

        for (Transaction.TransactionType type : Transaction.TransactionType.values()) {
            transactionTypeHashMap.put(type.toString(), type);
        }

        for (Transaction.TransactionCategory type : Transaction.TransactionCategory.values()) {
            transactionCategoryHashMap.put(type.toString(), type);
        }
    }

    public void execute(double amount, String accountNumber, String type, String category, String date) {
        Transaction.TransactionType transactionType = transactionTypeHashMap.get(type.toUpperCase());
        Transaction.TransactionCategory transactionCategory = transactionCategoryHashMap.get(category.toUpperCase());
        LocalDate transactionDate = LocalDate.parse(date); // in format 2024-01-19
        final AddTransactionInputData addTransactionInputData = new AddTransactionInputData(
                amount,
                accountNumber,
                transactionType,
                transactionCategory,
                transactionDate
        );
        addTransactionInteractor.execute(addTransactionInputData);
    }
}
