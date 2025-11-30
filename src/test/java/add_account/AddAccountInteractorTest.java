package add_account;

import data_access.InMemoryAccountDataAccessObject;
import entity.Account;
import org.junit.jupiter.api.Test;
import use_case.account.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AddAccountInteractorTest {
    @Test
    void accountAlreadyExistsTest() {
        AddAccountInputData addAccountInputData = new AddAccountInputData(
                "ACER123",
                Account.AccountType.CHECKING
        );

        AccountDataAccessInterface accountDataAccessObject = new InMemoryAccountDataAccessObject();

        // Create Account that already exists
        Account account = new Account("ACER123", Account.AccountType.CHECKING, new ArrayList<>(), 0.0);
        accountDataAccessObject.saveAccount(account);

        AddAccountOutputBoundary addAccountOutputPresenter = new AddAccountOutputBoundary() {
            @Override
            public void prepareAccountSuccessView(AddAccountOutputData addAccountOutputData) {
                fail("Should not execute this as the account already exists.");
            }

            @Override
            public void prepareAccountFailView(String errorMessage) {
                assertEquals("Account number ACER123 already exists.", errorMessage);
            }
        };

        AddAccountInputBoundary addAccountInputInteractor = new AddAccountInteractor(accountDataAccessObject, addAccountOutputPresenter);
        addAccountInputInteractor.execute(addAccountInputData);
    }

    @Test
    void accountAddSuccessfulTest() {
        AddAccountInputData addAccountInputData = new AddAccountInputData(
                "AWER123",
                Account.AccountType.SAVINGS
        );

        AccountDataAccessInterface accountDataAccessObject = new InMemoryAccountDataAccessObject();

        AddAccountOutputBoundary addAccountOutputPresenter = new AddAccountOutputBoundary() {
            @Override
            public void prepareAccountSuccessView(AddAccountOutputData addAccountOutputData) {
                assertEquals("AWER123", addAccountOutputData.getAddedAccountNumber());
                assertEquals("SAVINGS", addAccountOutputData.getAddedAccountType().toString());
            }

            @Override
            public void prepareAccountFailView(String errorMessage) {
                fail("Should not execute this as the account can be added.");
            }
        };

        AddAccountInputBoundary addAccountInputInteractor = new AddAccountInteractor(accountDataAccessObject, addAccountOutputPresenter);
        addAccountInputInteractor.execute(addAccountInputData);

        assertEquals(1, accountDataAccessObject.getAllAccounts().size());
        assertEquals("SAVINGS", accountDataAccessObject.getAccount("AWER123").getAccountType().toString());
        assertEquals(0.0, accountDataAccessObject.getAccount("AWER123").getAccountBalance());
        assertEquals("AWER123", accountDataAccessObject.getAccount("AWER123").getAccountNumber());
        assertEquals(0, accountDataAccessObject.getAccount("AWER123").getAccountTransactions().size());
    }
}
