package app;

import data_access.AccountDataAccessObject;
import data_access.MonthlyReportDataAccessObject;
import interface_adaptor.ViewManagerViewModel;
import interface_adaptor.add_account.AddAccountController;
import interface_adaptor.add_account.AddAccountPresenter;
import interface_adaptor.add_account.AddAccountViewModel;
import interface_adaptor.add_transaction.AddTransactionController;
import interface_adaptor.add_transaction.AddTransactionPresenter;
import interface_adaptor.add_transaction.AddTransactionViewModel;
import interface_adaptor.monthly_report.MonthlyReportController;
import interface_adaptor.monthly_report.MonthlyReportPresenter;
import interface_adaptor.monthly_report.MonthlyReportViewModel;
import interface_adaptor.monthly_summary.MonthlySummaryController;
import interface_adaptor.monthly_summary.MonthlySummaryPresenter;
import interface_adaptor.monthly_summary.MonthlySummaryViewModel;
import use_case.account.AddAccountInteractor;
import use_case.account.AddAccountOutputBoundary;
import interface_adaptor.month_transactions.MonthTransactionsController;
import use_case.add_transaction.AddTransactionInteractor;
import use_case.add_transaction.AddTransactionOutputBoundary;
import use_case.monthly_report.MonthlyReportInteractor;
import use_case.monthly_summary.MonthlySummaryInputBoundary;
import use_case.monthly_summary.MonthlySummaryInteractor;
import use_case.monthly_summary.MonthlySummaryOutputBoundary;
import use_case.month_transactions.MonthTransactionsInputBoundary;
import use_case.month_transactions.MonthTransactionsInteractor;
import use_case.month_transactions.MonthTransactionsOutputBoundary;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerViewModel viewManagerViewModel = new ViewManagerViewModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerViewModel);

    private AddTransactionView addTransactionView;
    private AddTransactionViewModel addTransactionViewModel;
    private MonthlySummaryView monthlySummaryView;
    private MonthlySummaryViewModel monthlySummaryViewModel;
    private AddAccountView addAccountView;
    private AddAccountViewModel addAccountViewModel;
    private MonthlyReportView monthlyReportView;
    private MonthlyReportViewModel monthlyReportViewModel;

    final AccountDataAccessObject accountDataAccessObject = new AccountDataAccessObject("accounts.json");

    public AppBuilder() {
        this.cardPanel.setLayout(this.cardLayout);
    }
    public AppBuilder addAddTransactionView() {
        addTransactionViewModel = new AddTransactionViewModel();
        addTransactionView = new AddTransactionView(addTransactionViewModel);

        ViewWithNavigation viewWithNav = new ViewWithNavigation(addTransactionView, viewManagerViewModel);
        this.cardPanel.add(viewWithNav, addTransactionView.getViewName());

        return this;
    }

    public AppBuilder addAddTransactionUseCase() {
        final AddTransactionOutputBoundary addTransactionOutputPresenter = new AddTransactionPresenter(addTransactionViewModel);
        final AddTransactionInteractor addTransactionInteractor = new AddTransactionInteractor(accountDataAccessObject, addTransactionOutputPresenter);

        AddTransactionController addTransactionController = new AddTransactionController(addTransactionInteractor);
        addTransactionView.setAddTransactionController(addTransactionController);
        return this;
    }
    public AppBuilder addMonthlySummaryView() {
        monthlySummaryViewModel = new MonthlySummaryViewModel();
        monthlySummaryView = new MonthlySummaryView(monthlySummaryViewModel);

        ViewWithNavigation viewWithNav = new ViewWithNavigation(monthlySummaryView, viewManagerViewModel);
        this.cardPanel.add(viewWithNav, monthlySummaryView.getViewName());

        return this;
    }

    public AppBuilder addMonthlySummaryUseCase() {
        final MonthlySummaryOutputBoundary monthlySummaryOutputPresenter = new MonthlySummaryPresenter(monthlySummaryViewModel);
        final MonthlySummaryInputBoundary monthlySummaryInteractor = new MonthlySummaryInteractor(accountDataAccessObject, monthlySummaryOutputPresenter);

        MonthlySummaryController monthlySummaryController = new MonthlySummaryController(monthlySummaryInteractor);
        monthlySummaryView.setMonthlySummaryController(monthlySummaryController);

        // Add month transactions use case for the monthly summary view
        final MonthTransactionsOutputBoundary monthTransactionsOutputPresenter = monthlySummaryView.getTransactionPresenter();
        final MonthTransactionsInputBoundary monthTransactionsInteractor = new MonthTransactionsInteractor(accountDataAccessObject, monthTransactionsOutputPresenter);

        MonthTransactionsController monthTransactionsController = new MonthTransactionsController(monthTransactionsInteractor);
        monthlySummaryView.setMonthTransactionsController(monthTransactionsController);

        return this;
    }

    public AppBuilder addAddAccountView() {
        addAccountViewModel = new AddAccountViewModel();
        addAccountView = new AddAccountView(addAccountViewModel);

        ViewWithNavigation viewWithNav = new ViewWithNavigation(addAccountView, viewManagerViewModel);
        this.cardPanel.add(viewWithNav, addAccountView.getViewName());

        return this;
    }

    public AppBuilder addMonthlyReportView() {
        monthlyReportViewModel = new MonthlyReportViewModel();
        monthlyReportView = new MonthlyReportView(monthlyReportViewModel);

        ViewWithNavigation viewWithNav = new ViewWithNavigation(monthlyReportView, viewManagerViewModel);
        this.cardPanel.add(viewWithNav, monthlyReportView.getViewName());

        return this;
    }

    public AppBuilder addAddAccountUseCase() {
        final AddAccountOutputBoundary addAccountOutputPresenter = new AddAccountPresenter(addAccountViewModel);
        final AddAccountInteractor addAccountInteractor = new AddAccountInteractor(accountDataAccessObject, addAccountOutputPresenter);

        AddAccountController addAccountController = new AddAccountController(addAccountInteractor);
        addAccountView.setAddAccountController(addAccountController);
        return this;
    }

    public AppBuilder addMonthlyReportUseCase() {
        MonthlyReportDataAccessObject monthlyReportDAO = new MonthlyReportDataAccessObject();
        MonthlyReportPresenter presenter = new MonthlyReportPresenter(monthlyReportViewModel);
        MonthlyReportInteractor interactor =
                new MonthlyReportInteractor(monthlyReportDAO, presenter, accountDataAccessObject);
        MonthlyReportController controller =
                new MonthlyReportController(interactor);

        monthlyReportView.setMonthlyReportController(controller);
        return this;
    }

    public JFrame build() {
        JFrame application = new JFrame("Financial Portfolio App");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(this.cardPanel);

        viewManagerViewModel.setState("addTransaction"); // home page
        viewManagerViewModel.firePropertyChange("viewChange");

        return application;
    }
}