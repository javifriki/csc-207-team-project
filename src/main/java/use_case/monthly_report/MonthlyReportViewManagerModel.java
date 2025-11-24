package use_case.monthly_report;

import interface_adaptor.monthly_report.MonthlyReportViewModel;

/**
 * MonthlyReportViewManagerModel tells the UI which screen show be showing.
 */
public class MonthlyReportViewManagerModel extends MonthlyReportViewModel {
    public MonthlyReportViewManagerModel() {
        super("monthly report");
        this.setState("");
    }
}
