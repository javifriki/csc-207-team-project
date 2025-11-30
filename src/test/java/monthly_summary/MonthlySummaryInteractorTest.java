package monthly_summary;

import entity.Account;
import entity.Transaction;
import org.junit.jupiter.api.Test;
import use_case.account.AccountDataAccessInterface;
import use_case.monthly_summary.MonthlySummaryInteractor;
import use_case.monthly_summary.MonthlySummaryOutputBoundary;
import use_case.monthly_summary.MonthlySummaryResponseModel;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MonthlySummaryInteractorTest {

    /**
     * Simple fake DAO for tests: holds accounts in memory.
     */
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

        @Override
        public List<String> getAllAccountNumbers() {
            return List.of();
        }
    }

    static class InMemoryMonthlySummaryPresenter implements MonthlySummaryOutputBoundary {
        MonthlySummaryResponseModel lastResponse;

        @Override
        public void present(MonthlySummaryResponseModel responseModel) {
            this.lastResponse = responseModel;
        }
    }

    @Test
    void testMonthlySummaryWithTwoAccountsAndMultipleMonths() {
        FakeAccountDAO fakeDAO = new FakeAccountDAO();
        InMemoryMonthlySummaryPresenter presenter = new InMemoryMonthlySummaryPresenter();

        // Account 1
        List<Transaction> acc1Tx = Arrays.asList(
                // January income + spending
                new Transaction(1000.0, Transaction.TransactionType.INCOME, Transaction.TransactionCategory.SALARY,
                        LocalDate.of(2025, 1, 10), "A1"),
                new Transaction(200.0, Transaction.TransactionType.EXPENSE, Transaction.TransactionCategory.GROCERIES,
                        LocalDate.of(2025, 1, 15), "A1"),
                // February spending only
                new Transaction(150.0, Transaction.TransactionType.EXPENSE, Transaction.TransactionCategory.ENTERTAINMENT,
                        LocalDate.of(2025, 2, 2), "A1")
        );
        Account acc1 = new Account("A1", Account.AccountType.CHECKING, acc1Tx, 650.0);

        // Account 2
        List<Transaction> acc2Tx = Arrays.asList(
                // January extra income
                new Transaction(500.0, Transaction.TransactionType.INCOME, Transaction.TransactionCategory.GROCERIES,
                        LocalDate.of(2025, 1, 20), "A2"),
                // March income + spending
                new Transaction(800.0, Transaction.TransactionType.INCOME, Transaction.TransactionCategory.SALARY,
                        LocalDate.of(2025, 3, 3), "A2"),
                new Transaction(300.0, Transaction.TransactionType.EXPENSE, Transaction.TransactionCategory.UTILITIES,
                        LocalDate.of(2025, 3, 25), "A2")
        );
        Account acc2 = new Account("A2", Account.AccountType.SAVINGS, acc2Tx, 2000.0);

        fakeDAO.addAccount(acc1);
        fakeDAO.addAccount(acc2);

        MonthlySummaryInteractor interactor =
                new MonthlySummaryInteractor(fakeDAO, presenter);

        interactor.generateSummary();

        assertNotNull(presenter.lastResponse, "Presenter should have received a response.");
        List<MonthlySummaryResponseModel.MonthSummary> summaries =
                presenter.lastResponse.getLastSixMonths();

        assertEquals(3, summaries.size(), "Should have 3 month summaries");

        MonthlySummaryResponseModel.MonthSummary jan = summaries.get(0);
        MonthlySummaryResponseModel.MonthSummary feb = summaries.get(1);
        MonthlySummaryResponseModel.MonthSummary mar = summaries.get(2);

        assertEquals(YearMonth.of(2025, 1), jan.month);
        assertEquals(YearMonth.of(2025, 2), feb.month);
        assertEquals(YearMonth.of(2025, 3), mar.month);

        // January: income = 1000 + 500 = 1500, spending = 200
        assertEquals(1500.0, jan.totalIncome, 0.0001);
        assertEquals(200.0, jan.totalSpending, 0.0001);

        // February: income = 0, spending = 150
        assertEquals(0.0, feb.totalIncome, 0.0001);
        assertEquals(150.0, feb.totalSpending, 0.0001);

        // March: income = 800, spending = 300
        assertEquals(800.0, mar.totalIncome, 0.0001);
        assertEquals(300.0, mar.totalSpending, 0.0001);
    }
}
