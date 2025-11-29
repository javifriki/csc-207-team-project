package use_case.monthly_report;

public class MonthlyReportInputData {
    private static int year;
    private static int month;

    public MonthlyReportInputData(int year, int month) {
        this.year = year;
        this.month = month;
    }

    static int getMonth() {
        return month;
    }

    static int getYear() {
        return year;
    }
}