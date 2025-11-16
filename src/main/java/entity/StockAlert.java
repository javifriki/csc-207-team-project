package entity;

public class StockAlert {
    private final String userId;
    private final String stockSymbol;
    private final double targetPrice;
    private final boolean isTriggered;

    public StockAlert(String userId, String stockSymbol, double targetPrice, boolean isTriggered) {
        this.userId = userId;
        this.stockSymbol = stockSymbol;
        this.targetPrice = targetPrice;
        this.isTriggered = isTriggered;
    }

    // Getters
    public String getUserId() { return userId; }
    public String getStockSymbol() { return stockSymbol; }
    public double getTargetPrice() { return targetPrice; }
    public boolean isTriggered() { return isTriggered; }

    // Create a triggered version
    public StockAlert markAsTriggered() {
        return new StockAlert(userId, stockSymbol, targetPrice, true);
    }
}