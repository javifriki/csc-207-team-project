package interface_adaptor.monthly_report;

import java.awt.image.BufferedImage;

public class MonthlyReportState {

    private int year;
    private int month;
    private BufferedImage lineChartImage;
    private BufferedImage pieChartImage;
    private String errorMessage;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public BufferedImage getLineChartImage() {
        return lineChartImage;
    }

    public void setLineChartImage(BufferedImage lineChartImage) {
        this.lineChartImage = lineChartImage;
    }

    public BufferedImage getPieChartImage() {
        return pieChartImage;
    }

    public void setPieChartImage(BufferedImage pieChartImage) {
        this.pieChartImage = pieChartImage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
