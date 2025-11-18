package use_case.monthly_report;

public class MonthlyReportInputData {
    private final String year;
    private final String month;

    public MonthlyReportInputData(String year, String month) {
        this.year = year;
        this.month = month;
    }

    String getMonth() {
        return month;
    }

    String getYear() {
        return year;
    }
}
