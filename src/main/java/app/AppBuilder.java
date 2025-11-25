package app;

import data_access.AccountDataAccessObject;
import interface_adaptor.ViewManagerViewModel;
import interface_adaptor.add_transaction.AddTransactionController;
import interface_adaptor.add_transaction.AddTransactionPresenter;
import interface_adaptor.add_transaction.AddTransactionViewModel;
import interface_adaptor.monthly_summary.MonthlySummaryController;
import interface_adaptor.monthly_summary.MonthlySummaryPresenter;
import interface_adaptor.monthly_summary.MonthlySummaryViewModel;
import use_case.add_transaction.AddTransactionInteractor;
import use_case.add_transaction.AddTransactionOutputBoundary;
import use_case.monthly_summary.MonthlySummaryInputBoundary;
import use_case.monthly_summary.MonthlySummaryInteractor;
import use_case.monthly_summary.MonthlySummaryOutputBoundary;
import view.AddTransactionView;
import view.MonthlySummaryView;
import view.ViewManager;
import view.ViewWithNavigation;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerViewModel viewManagerViewModel = new ViewManagerViewModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerViewModel);

    private AddTransactionView addTransactionView;
    private AddTransactionViewModel addTransactionViewModel;
    private MonthlySummaryView monthlySummaryView;
    private MonthlySummaryViewModel monthlySummaryViewModel;

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