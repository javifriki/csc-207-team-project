package app;

import data_access.AccountDataAccessObject;
import interface_adaptor.ViewManagerViewModel;
import interface_adaptor.add_transaction.AddTransactionController;
import interface_adaptor.add_transaction.AddTransactionPresenter;
import interface_adaptor.add_transaction.AddTransactionViewModel;
import use_case.add_transaction.AddTransactionInteractor;
import use_case.add_transaction.AddTransactionOutputBoundary;
import view.AddTransactionView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerViewModel viewManagerViewModel = new ViewManagerViewModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerViewModel);

    private AddTransactionView addTransactionView;
    private AddTransactionViewModel addTransactionViewModel;

    final AccountDataAccessObject accountDataAccessObject = new AccountDataAccessObject("accounts.json");

    public AppBuilder() {
        this.cardPanel.setLayout(this.cardLayout);
    }

    public AppBuilder addAddTransactionView() {
        addTransactionViewModel = new AddTransactionViewModel();
        addTransactionView = new AddTransactionView(addTransactionViewModel);

        this.cardPanel.add(addTransactionView, addTransactionView.getViewName());

        return this;
    }

    public AppBuilder addAddTransactionUseCase() {
        final AddTransactionOutputBoundary addTransactionOutputPresenter = new AddTransactionPresenter(addTransactionViewModel);
        final AddTransactionInteractor addTransactionInteractor = new AddTransactionInteractor(accountDataAccessObject, addTransactionOutputPresenter);

        AddTransactionController addTransactionController = new AddTransactionController(addTransactionInteractor);
        addTransactionView.setAddTransactionController(addTransactionController);
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
