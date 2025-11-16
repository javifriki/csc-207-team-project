package service;

import data_access.StockAlertDataAccessObject;
import entity.StockAlert;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AlertCheckerService {
    private final StockAlertDataAccessObject alertDataAccess;
    private final StockPriceService stockService;
    private Timer timer;

    public AlertCheckerService(StockAlertDataAccessObject alertDataAccess, StockPriceService stockService) {
        this.alertDataAccess = alertDataAccess;
        this.stockService = stockService;
    }

    public void startChecking(String userId) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkAllAlerts(userId);
            }
        }, 0, 60000); // Check every 60 seconds
    }

    public void stopChecking() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void checkAllAlerts(String userId) {
        List<StockAlert> alerts = alertDataAccess.getUserAlerts(userId);
        for (StockAlert alert : alerts) {
            double currentPrice = stockService.getCurrentPrice(alert.getStockSymbol());

            if (currentPrice >= alert.getTargetPrice()) {
                // Trigger alert
                StockAlert triggeredAlert = alert.markAsTriggered();
                alertDataAccess.updateAlert(triggeredAlert);

                // Send notification
                sendNotification(alert, currentPrice);
            }
        }
    }

    private void sendNotification(StockAlert alert, double currentPrice) {
        // Simple console notification - you can enhance this
        System.out.println("🚨 STOCK ALERT 🚨");
        System.out.println("Stock: " + alert.getStockSymbol());
        System.out.println("Target: $" + alert.getTargetPrice());
        System.out.println("Current: $" + currentPrice);
        System.out.println("Time: " + java.time.LocalDateTime.now());
        System.out.println("------------------------");
    }
}