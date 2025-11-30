package app;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addAddTransactionView()
                .addAddTransactionUseCase()
                .addMonthlySummaryView()
                .addMonthlySummaryUseCase()
                .addAddAccountView()
                .addAddAccountUseCase()
                .addMonthlyReportView()
                .addMonthlyReportUseCase()
                .addCurrencyConverterView()
                .addCurrencyConverterUseCase()

                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
