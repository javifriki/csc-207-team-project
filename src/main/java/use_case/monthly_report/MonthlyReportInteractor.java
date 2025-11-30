package use_case.monthly_report;

import data_access.MonthlyReportDataAccessObject;
import use_case.account.AccountDataAccessInterface;

import java.awt.image.BufferedImage;

/**
 * Interactor for the Monthly Report Use Case.
 *
 */

public class MonthlyReportInteractor implements MonthlyReportInputBoundary {

    private final MonthlyReportDataAccessObject monthlyReportDAO;
    private final MonthlyReportOutputBoundary presenter;
    private final AccountDataAccessInterface accountGateway;

    public MonthlyReportInteractor(MonthlyReportDataAccessObject monthlyReportDAO,
                                   MonthlyReportOutputBoundary presenter,
                                   AccountDataAccessInterface accountGateway) {
        this.monthlyReportDAO = monthlyReportDAO;
        this.presenter = presenter;
        this.accountGateway = accountGateway;
    }

    @Override
    public void generateReport(MonthlyReportInputData inputData) {
        try {
            int year = inputData.getYear();
            int month = inputData.getMonth();

            monthlyReportDAO.init(accountGateway);
            monthlyReportDAO.load(accountGateway);


            BufferedImage line = MonthlyReportAPI.generateLineChart(year, month);
            BufferedImage pie  = MonthlyReportAPI.generatePieChart(year, month);

            MonthlyReportOutputData outputData =
                    new MonthlyReportOutputData(year, month, line, pie);

            presenter.present(outputData);

        } catch (Exception e) {
            presenter.presentError("Failed to load monthly report: " + e.getMessage());
        }
    }
}