package use_case.stock_alert;

public class SetStockAlertInputData {
    private final String userId;
    private final String stockSymbol;
    private final double targetPrice;

    public SetStockAlertInputData(String userId, String stockSymbol, double targetPrice) {
        this.userId = userId;
        this.stockSymbol = stockSymbol;
        this.targetPrice = targetPrice;
    }

    // Getters
    public String getUserId() { return userId; }
    public String getStockSymbol() { return stockSymbol; }
    public double getTargetPrice() { return targetPrice; }
}