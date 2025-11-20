package month_transactions;

import entity.Account;
import entity.Transaction;
import org.junit.jupiter.api.Test;
import use_case.account.AccountDataAccessInterface;
import use_case.month_transactions.MonthTransactionsInteractor;
import use_case.month_transactions.MonthTransactionsOutputBoundary;
import use_case.month_transactions.MonthTransactionsResponseModel;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MonthTransactionsInteractorTest {

    static class FakeAccountDAO implements AccountDataAccessInterface {
        private final List<Account> accounts = new ArrayList<>();

        public void addAccount(Account account) {
            accounts.add(account);
        }

        @Override
        public void saveAccount(Account account) {
        }

        @Override
        public Account getAccount(String accountNumber) {
            return accounts.stream()
                    .filter(a -> a.getAccountNumber().equals(accountNumber))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public List<Account> getAllAccounts() {
            return new ArrayList<>(accounts);
        }
    }

    static class InMemoryMonthTransactionsPresenter implements MonthTransactionsOutputBoundary {
        MonthTransactionsResponseModel lastResponse;

        @Override
        public void present(MonthTransactionsResponseModel responseModel) {
            this.lastResponse = responseModel;
        }
    }

    @Test
    void testGetTransactionsForSpecificMonthSortedByDate() {
        FakeAccountDAO fakeDAO = new FakeAccountDAO();
        InMemoryMonthTransactionsPresenter presenter = new InMemoryMonthTransactionsPresenter();

        // Account 1 has transactions in March & April 2025
        List<Transaction> acc1Tx = Arrays.asList(
                new Transaction(100.0, Transaction.TransactionType.EXPENSE, Transaction.TransactionCategory.GROCERIES,
                        LocalDate.of(2025, 3, 10), "A1"),
                new Transaction(50.0, Transaction.TransactionType.EXPENSE, Transaction.TransactionCategory.ENTERTAINMENT,
                        LocalDate.of(2025, 3, 5), "A1"),  // earlier in March
                new Transaction(200.0, Transaction.TransactionType.INCOME, Transaction.TransactionCategory.SALARY,
                        LocalDate.of(2025, 4, 1), "A1")
        );
        Account acc1 = new Account("A1", Account.AccountType.CHECKING, acc1Tx, 0.0);

        // Account 2 has a March income
        List<Transaction> acc2Tx = List.of(
                new Transaction(300.0, Transaction.TransactionType.INCOME, Transaction.TransactionCategory.SALARY,
                        LocalDate.of(2025, 3, 20), "A2")
        );
        Account acc2 = new Account("A2", Account.AccountType.SAVINGS, acc2Tx, 0.0);

        fakeDAO.addAccount(acc1);
        fakeDAO.addAccount(acc2);

        MonthTransactionsInteractor interactor =
                new MonthTransactionsInteractor(fakeDAO, presenter);

        interactor.getTransactionsForMonth(YearMonth.of(2025, 3));

        assertNotNull(presenter.lastResponse, "Presenter should have received a response.");
        List<MonthTransactionsResponseModel.TransactionData> txs =
                presenter.lastResponse.getTransactions();

        assertEquals(3, txs.size());

        assertEquals(LocalDate.of(2025, 3, 5), txs.get(0).date);
        assertEquals(LocalDate.of(2025, 3, 10), txs.get(1).date);
        assertEquals(LocalDate.of(2025, 3, 20), txs.get(2).date);

        assertEquals("A1", txs.get(0).accountNumber);
        assertEquals("A1", txs.get(1).accountNumber);
        assertEquals("A2", txs.get(2).accountNumber);

        assertTrue(txs.stream().noneMatch(td -> td.date.getMonthValue() == 4));
    }
}
