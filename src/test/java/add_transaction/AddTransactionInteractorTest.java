package add_transaction;

import data_access.InMemoryAccountDataAccessObject;
import entity.Account;
import entity.Transaction;
import org.junit.jupiter.api.Test;
import use_case.account.AccountDataAccessInterface;
import use_case.add_transaction.AddTransactionInputData;
import use_case.add_transaction.AddTransactionInteractor;
import use_case.add_transaction.AddTransactionOutputBoundary;
import use_case.add_transaction.AddTransactionOutputData;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AddTransactionInteractorTest {
    @Test
    void transactionSuccessfulTest() {
        // Transaction Input Data
        AddTransactionInputData addTransactionInputData = new AddTransactionInputData(
                11.00,
                "AWER123",
                Transaction.TransactionType.EXPENSE,
                Transaction.TransactionCategory.GROCERIES,
                LocalDate.of(2025, 11, 16)
        );

        AccountDataAccessInterface accountDataAccessObject = new InMemoryAccountDataAccessObject();

        // Add the Account to temporary DAO so the transaction can be added
        Account account = new Account("AWER123", Account.AccountType.CHECKING, new ArrayList<>(), 1000.0);
        accountDataAccessObject.saveAccount(account);

        // Mock Presenter
        AddTransactionOutputBoundary addTransactionPresenter = new AddTransactionOutputBoundary() {
            @Override
            public void prepareTransactionSuccessView(AddTransactionOutputData addTransactionOutputData) {
                // Test the output data is correct
                assertEquals("AWER123", addTransactionOutputData.getAccountNumber());
                assertEquals(989.00, addTransactionOutputData.getNewBalance());

                assertEquals(11.00, addTransactionOutputData.getNewTransaction().getTransactionAmount());
                assertEquals("EXPENSE", addTransactionOutputData.getNewTransaction().getTransactionType().toString());
                assertEquals("GROCERIES", addTransactionOutputData.getNewTransaction().getTransactionCategory().toString());
                assertEquals("2025-11-16", addTransactionOutputData.getNewTransaction().getTransactionDate().toString());
                assertEquals("AWER123", addTransactionOutputData.getNewTransaction().getAccountNumber());
            }

            @Override
            public void prepareTransactionFailView(String errorMessage) {
                fail("Unexpected fail");
            }
        };

        AddTransactionInteractor addTransactionInteractor = new AddTransactionInteractor(accountDataAccessObject, addTransactionPresenter);
        addTransactionInteractor.execute(addTransactionInputData);

        // Check the DAO has stored the account and the transaction within it
        assertEquals("AWER123", accountDataAccessObject.getAccount("AWER123").getAccountNumber());
        assertEquals("CHECKING", accountDataAccessObject.getAccount("AWER123").getAccountType().toString());
        assertEquals(989.00, accountDataAccessObject.getAccount("AWER123").getAccountBalance());

        // Now the List<Transaction> in Account
        assertEquals(1, accountDataAccessObject.getAccount("AWER123").getAccountTransactions().size());
        assertEquals(11.00, accountDataAccessObject.getAccount("AWER123").getAccountTransactions().get(0).getTransactionAmount());
        assertEquals("EXPENSE", accountDataAccessObject.getAccount("AWER123").getAccountTransactions().get(0).getTransactionType().toString());
        assertEquals("GROCERIES", accountDataAccessObject.getAccount("AWER123").getAccountTransactions().get(0).getTransactionCategory().toString());
        assertEquals("2025-11-16", accountDataAccessObject.getAccount("AWER123").getAccountTransactions().get(0).getTransactionDate().toString());
        assertEquals("AWER123", accountDataAccessObject.getAccount("AWER123").getAccountTransactions().get(0).getAccountNumber());
    }

    @Test
    void accountDoesNotExistTest() {
        AddTransactionInputData addTransactionInputData = new AddTransactionInputData(
                11.00,
                "AWER123",
                Transaction.TransactionType.EXPENSE,
                Transaction.TransactionCategory.GROCERIES,
                LocalDate.of(2025, 11, 16)
        );

        AccountDataAccessInterface accountDataAccessObject = new InMemoryAccountDataAccessObject();

        AddTransactionOutputBoundary addTransactionPresenter = new AddTransactionOutputBoundary() {
            @Override
            public void prepareTransactionSuccessView(AddTransactionOutputData addTransactionOutputData) {
                fail("Should not be calling this.");
            }

            @Override
            public void prepareTransactionFailView(String errorMessage) {
                assertEquals("Account number: AWER123 does not exist", errorMessage);
            }
        };

        AddTransactionInteractor addTransactionInteractor = new AddTransactionInteractor(accountDataAccessObject, addTransactionPresenter);
        addTransactionInteractor.execute(addTransactionInputData);
    }

    @Test
    void insufficientFundsInAccountTest() {
        AddTransactionInputData addTransactionInputData = new AddTransactionInputData(
                500.00,
                "AWER123",
                Transaction.TransactionType.EXPENSE,
                Transaction.TransactionCategory.GROCERIES,
                LocalDate.of(2025, 11, 25)
        );

        AccountDataAccessInterface accountDataAccessObject = new InMemoryAccountDataAccessObject();

        Account account = new Account("AWER123", Account.AccountType.CHECKING, new ArrayList<>(), 100.0);
        accountDataAccessObject.saveAccount(account);

        AddTransactionOutputBoundary addTransactionPresenter = new AddTransactionOutputBoundary() {
            @Override
            public void prepareTransactionSuccessView(AddTransactionOutputData addTransactionOutputData) {
                fail("Should not be calling this.");
            }

            @Override
            public void prepareTransactionFailView(String errorMessage) {
                assertEquals("Insufficient funds in account AWER123", errorMessage);
            }
        };

        AddTransactionInteractor addTransactionInteractor = new AddTransactionInteractor(accountDataAccessObject, addTransactionPresenter);
        addTransactionInteractor.execute(addTransactionInputData);
    }

    @Test
    void incomeTransactionNotHaveInsufficentFundsTest() {
        AddTransactionInputData addTransactionInputData = new AddTransactionInputData(
                1000.0,
                "AWER123",
                Transaction.TransactionType.INCOME,
                Transaction.TransactionCategory.SALARY,
                LocalDate.of(2025, 11, 17)
        );

        AccountDataAccessInterface accountDataAccessObject = new InMemoryAccountDataAccessObject();
        Account account = new Account("AWER123", Account.AccountType.SAVINGS, new ArrayList<>(), 10.0);
        accountDataAccessObject.saveAccount(account);

        AddTransactionOutputBoundary addTransactionPresenter = new AddTransactionOutputBoundary() {
            @Override
            public void prepareTransactionSuccessView(AddTransactionOutputData addTransactionOutputData) {
                assertEquals("AWER123", addTransactionOutputData.getAccountNumber());
                assertEquals(1010.0, addTransactionOutputData.getNewBalance());

                assertEquals(1000.0, addTransactionOutputData.getNewTransaction().getTransactionAmount());
                assertEquals("INCOME", addTransactionOutputData.getNewTransaction().getTransactionType().toString());
                assertEquals("SALARY", addTransactionOutputData.getNewTransaction().getTransactionCategory().toString());
                assertEquals("2025-11-17", addTransactionOutputData.getNewTransaction().getTransactionDate().toString());
                assertEquals("AWER123", addTransactionOutputData.getNewTransaction().getAccountNumber());
            }

            @Override
            public void prepareTransactionFailView(String errorMessage) {
                fail("Income should not receive a failure message like insuffient funds");
            }
        };

        AddTransactionInteractor addTransactionInteractor = new AddTransactionInteractor(accountDataAccessObject, addTransactionPresenter);
        addTransactionInteractor.execute(addTransactionInputData);

        // Check the DAO has stored the account and the transaction within it
        assertEquals("AWER123", accountDataAccessObject.getAccount("AWER123").getAccountNumber());
        assertEquals("SAVINGS", accountDataAccessObject.getAccount("AWER123").getAccountType().toString());
        assertEquals(1010.0, accountDataAccessObject.getAccount("AWER123").getAccountBalance());

        // Now the List<Transaction> in Account
        assertEquals(1, accountDataAccessObject.getAccount("AWER123").getAccountTransactions().size());
        assertEquals(1000.0, accountDataAccessObject.getAccount("AWER123").getAccountTransactions().get(0).getTransactionAmount());
        assertEquals("INCOME", accountDataAccessObject.getAccount("AWER123").getAccountTransactions().get(0).getTransactionType().toString());
        assertEquals("SALARY", accountDataAccessObject.getAccount("AWER123").getAccountTransactions().get(0).getTransactionCategory().toString());
        assertEquals("2025-11-17", accountDataAccessObject.getAccount("AWER123").getAccountTransactions().get(0).getTransactionDate().toString());
        assertEquals("AWER123", accountDataAccessObject.getAccount("AWER123").getAccountTransactions().get(0).getAccountNumber());
    }

}
