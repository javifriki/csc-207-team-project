package interface_adaptor.monthly_summary;

import java.util.ArrayList;
import java.util.List;

public class MonthlySummaryState {
    private List<MonthlySummaryViewModel.MonthBarViewModel> bars;

    public MonthlySummaryState() {
        this.bars = new ArrayList<>();
    }

    public List<MonthlySummaryViewModel.MonthBarViewModel> getBars() {
        return bars;
    }

    public void setBars(List<MonthlySummaryViewModel.MonthBarViewModel> bars) {
        this.bars = bars;
    }
}