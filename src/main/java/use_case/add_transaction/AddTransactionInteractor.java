package use_case.add_transaction;

import data_access.AccountDataAccessObject;
import entity.Account;
import entity.Transaction;
import use_case.account.AccountDataAccessInterface;

import java.time.LocalDate;

public class AddTransactionInteractor implements AddTransactionInputBoundary {

    private final AccountDataAccessInterface accountDataAccessObject;
    private final AddTransactionOutputBoundary addTransactionPresenter;

    public AddTransactionInteractor (AccountDataAccessInterface accountDataAccessObject, AddTransactionOutputBoundary addTransactionPresenter) {
        this.accountDataAccessObject = accountDataAccessObject;
        this.addTransactionPresenter = addTransactionPresenter;
    }

    @Override
    public void execute(AddTransactionInputData addTransactionInputData) {
        final double transactionAmount = addTransactionInputData.getAmount();
        final String accountNumber = addTransactionInputData.getAccountNumber();
        final LocalDate transactionDate = addTransactionInputData.getDate();
        final Transaction.TransactionType transactionType = addTransactionInputData.getType();
        final Transaction.TransactionCategory transactionCategory = addTransactionInputData.getCategory();

        Account account = accountDataAccessObject.getAccount(accountNumber);
        if (account == null) {
            addTransactionPresenter.prepareTransactionFailView("Account number: " + accountNumber + " does not exist");
            return;
        }

        if (account.getAccountBalance() - transactionAmount < 0 && transactionType == Transaction.TransactionType.EXPENSE && account.getAccountType() != Account.AccountType.CREDIT) {
            addTransactionPresenter.prepareTransactionFailView("Insufficient funds in account " + accountNumber);
            return;
        }

        Transaction transaction = new Transaction(
                transactionAmount,
                transactionType,
                transactionCategory,
                transactionDate,
                accountNumber
        );
        account.applyTransaction(transaction);
        accountDataAccessObject.saveAccount(account); // persist the updated account after transaction occurred

        final AddTransactionOutputData addTransactionOutputData = new AddTransactionOutputData(
                accountNumber,
                account.getAccountBalance(),
                transaction
        );

        addTransactionPresenter.prepareTransactionSuccessView(addTransactionOutputData);
    }
}
