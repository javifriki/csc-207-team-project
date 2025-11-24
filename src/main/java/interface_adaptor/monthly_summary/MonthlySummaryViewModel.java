package interface_adaptor.monthly_summary;

import interface_adaptor.ViewModel;
import java.time.YearMonth;
import java.util.List;

public class MonthlySummaryViewModel extends ViewModel<MonthlySummaryState> {

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

    public MonthlySummaryViewModel() {
        super("monthlySummary");
        setState(new MonthlySummaryState());
    }
}