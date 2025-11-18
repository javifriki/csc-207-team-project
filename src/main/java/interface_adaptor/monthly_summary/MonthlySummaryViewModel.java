package interface_adaptor.monthly_summary;

import java.time.YearMonth;
import java.util.List;

public class MonthlySummaryViewModel {

    public static class MonthBarViewModel {
        public final YearMonth month;
        public final double income;
        public final double spending;

        public MonthBarViewModel(YearMonth month, double income, double spending) {
            this.month = month;
            this.income = income;
            this.spending = spending;
        }
    }

    private List<MonthBarViewModel> bars;

    public List<MonthBarViewModel> getBars() {
        return bars;
    }

    public void setBars(List<MonthBarViewModel> bars) {
        this.bars = bars;
    }
}
