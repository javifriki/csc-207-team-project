package use_case.stock_alert;

import data_access.StockAlertDataAccessObject;
import entity.StockAlert;
import service.StockPriceService;

public class SetStockAlertInteractor implements SetStockAlertInputBoundary {
    private final StockAlertDataAccessObject alertDataAccess;
    private final StockPriceService stockService;

    public SetStockAlertInteractor(StockAlertDataAccessObject alertDataAccess, StockPriceService stockService) {
        this.alertDataAccess = alertDataAccess;
        this.stockService = stockService;
    }

    @Override
    public void execute(SetStockAlertInputData inputData) {
        // Create new alert
        StockAlert alert = new StockAlert(
                inputData.getUserId(),
                inputData.getStockSymbol(),
                inputData.getTargetPrice(),
                false
        );

        alertDataAccess.addAlert(alert);

        // Check immediately if alert should trigger
        checkAndTriggerAlert(alert);
    }

    private void checkAndTriggerAlert(StockAlert alert) {
        double currentPrice = stockService.getCurrentPrice(alert.getStockSymbol());

        if (currentPrice >= alert.getTargetPrice()) {
            // Trigger the alert
            StockAlert triggeredAlert = alert.markAsTriggered();
            alertDataAccess.updateAlert(triggeredAlert);

            // Notify user (you can implement this)
            System.out.println("ALERT: " + alert.getStockSymbol() +
                    " reached target price of $" + alert.getTargetPrice() +
                    "! Current price: $" + currentPrice);
        }
    }
}