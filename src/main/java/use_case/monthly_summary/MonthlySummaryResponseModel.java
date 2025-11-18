package use_case.monthly_summary;

import java.time.YearMonth;
import java.util.List;

public class MonthlySummaryResponseModel {

    public static class MonthSummary {
        public final YearMonth month;
        public final double totalIncome;
        public final double totalSpending;

        public MonthSummary(YearMonth month, double totalIncome, double totalSpending) {
            this.month = month;
            this.totalIncome = totalIncome;
            this.totalSpending = totalSpending;
        }
    }

    private final List<MonthSummary> lastSixMonths;

    public MonthlySummaryResponseModel(List<MonthSummary> lastSixMonths) {
        this.lastSixMonths = lastSixMonths;
    }

    public List<MonthSummary> getLastSixMonths() {
        return lastSixMonths;
    }
}
