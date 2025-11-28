package use_case.monthly_report;

import data_access.MonthlyReportDataAccessObject;
import use_case.account.AccountDataAccessInterface;

import java.awt.image.BufferedImage;
import java.io.IOException;

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
    public void generateReport(MonthlyReportInputData monthlyReportInputData) throws IOException, InterruptedException {
        int year = MonthlyReportInputData.getYear();
        int month = MonthlyReportInputData.getMonth();

        monthlyReportDAO.init(accountGateway);
        monthlyReportDAO.load(accountGateway);

        BufferedImage line = MonthlyReportAPI.generateLineChart(year, month);
        BufferedImage pie = MonthlyReportAPI.generatePieChart(year, month);

        presenter.present(new MonthlyReportOutputData(year,
                                                    month,
                                                    line,
                                                    pie)
        );

    }
}