package use_case.monthly_report;

import data_access.MonthlyReportDataAccessObject;
import org.json.JSONObject;

/**
 * Interactor for the Monthly Report Use Case.
 *
 */

public class MonthlyReportInteractor implements MonthlyReportInputBoundary {

    private final MonthlyReportDataAccessObject monthlyReportDAO;
    private final MonthlyReportOutputBoundary presenter;

    public MonthlyReportInteractor(MonthlyReportDataAccessObject monthlyReportDAO,
                                   MonthlyReportOutputBoundary presenter) {
        this.monthlyReportDAO = monthlyReportDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(int year, int month) {

        JSONObject monthObj = monthlyReportDAO.getMonthData(year, month);

        JSONObject graphJson = monthObj.getJSONObject("graph_data");
        JSONObject categoryJson = monthObj.getJSONObject("category_totals");

        presenter.prepareSuccessView(year, month, graphJson, categoryJson);
    }

}
