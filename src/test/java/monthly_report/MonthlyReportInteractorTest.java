package monthly_report;

import entity.Account;
import entity.Transaction;
import org.junit.jupiter.api.Test;
import use_case.account.AccountDataAccessInterface;
import use_case.monthly_report.MonthlyReportInputData;
import use_case.monthly_report.MonthlyReportInteractor;
import use_case.monthly_report.MonthlyReportOutputBoundary;
import use_case.monthly_report.MonthlyReportOutputData;
import data_access.MonthlyReportDataAccessObject;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MonthlyReportInteractor.
 */
class MonthlyReportInteractorTest {

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

    static class InMemoryMonthlyReportPresenter implements MonthlyReportOutputBoundary {

        MonthlyReportOutputData lastOutput;
        String lastError;

        @Override
        public void present(MonthlyReportOutputData outputData) {
            this.lastOutput = outputData;
        }

        @Override
        public void presentError(String message) {
            this.lastError = message;
        }
    }

    static class NoGatewayReportDAO extends MonthlyReportDataAccessObject {
        @Override
        public void load(use_case.account.AccountDataAccessInterface accountGateway) {
            throw new RuntimeException();
        }
    }

    @Test
    void testGenerateImages() {
        FakeAccountDAO fakeDAO = new FakeAccountDAO();
        InMemoryMonthlyReportPresenter presenter = new InMemoryMonthlyReportPresenter();
        MonthlyReportDataAccessObject monthlyReportDAO = new MonthlyReportDataAccessObject();

        List<Transaction> acc1Tx = Arrays.asList(
                new Transaction(1000.0, Transaction.TransactionType.INCOME,
                        Transaction.TransactionCategory.SALARY,
                        LocalDate.of(2025, 1, 5), "A1"),
                new Transaction(200.0, Transaction.TransactionType.EXPENSE,
                        Transaction.TransactionCategory.GROCERIES,
                        LocalDate.of(2025, 1, 10), "A1"),
                new Transaction(500.0, Transaction.TransactionType.INCOME,
                        Transaction.TransactionCategory.SALARY,
                        LocalDate.of(2025, 2, 3), "A1")
        );
        Account acc1 = new Account("A1", Account.AccountType.CHECKING, acc1Tx, 800.0);

        fakeDAO.addAccount(acc1);

        MonthlyReportInteractor interactor =
                new MonthlyReportInteractor(monthlyReportDAO, presenter, fakeDAO);

        MonthlyReportInputData input = new MonthlyReportInputData(2025, 1);

        interactor.generateReport(input);

        assertNull(presenter.lastError, "There should be no error message.");
        assertNotNull(presenter.lastOutput, "Presenter should receive an output data object.");

        MonthlyReportOutputData out = presenter.lastOutput;

        assertEquals(2025, out.getYear());
        assertEquals(1, out.getMonth());

        BufferedImage line = out.getLineGraph();
        BufferedImage pie  = out.getPieChart();

        assertNotNull(line, "Line chart not null.");
        assertNotNull(pie, "Pie chart not null.");
    }

    @Test
    void testGenerateReportHandlesException() {
        FakeAccountDAO fakeDAO = new FakeAccountDAO();
        InMemoryMonthlyReportPresenter presenter = new InMemoryMonthlyReportPresenter();
        NoGatewayReportDAO noGatewayReportDAO = new NoGatewayReportDAO();

        MonthlyReportInteractor interactor =
                new MonthlyReportInteractor(noGatewayReportDAO, presenter, fakeDAO);

        MonthlyReportInputData input = new MonthlyReportInputData(2025, 1);

        interactor.generateReport(input);

        assertNull(presenter.lastOutput, "When an exception occurs, no normal output should be set.");
        assertNotNull(presenter.lastError, "Error message should be passed to presenter.");
        assertTrue(
                presenter.lastError.startsWith("Failed to load monthly report"),
                "Error message should start with the interactor's error prefix."
        );
    }
}