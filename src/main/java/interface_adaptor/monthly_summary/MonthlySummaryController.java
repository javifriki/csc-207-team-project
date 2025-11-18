package interface_adaptor.monthly_summary;

import use_case.monthly_summary.MonthlySummaryInputBoundary;

public class MonthlySummaryController {

    private final MonthlySummaryInputBoundary interactor;

    public MonthlySummaryController(MonthlySummaryInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void loadSummary() {
        interactor.generateSummary();
    }
}
