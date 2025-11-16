package data_access;

import entity.StockAlert;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class StockAlertDataAccessObject {  private List<StockAlert> stockAlerts = new ArrayList<>();
    private String filename;

    public StockAlertDataAccessObject(String filename) {
        this.filename = filename;
        loadAlerts();
    }

    private void loadAlerts() {
        try {
            String contents = Files.readString(Path.of(this.filename));
            JSONArray alertsArray = new JSONArray(contents);
            for (int i = 0; i < alertsArray.length(); i++) {
                JSONObject alertObj = alertsArray.getJSONObject(i);
                StockAlert alert = new StockAlert(
                        alertObj.getString("userId"),
                        alertObj.getString("stockSymbol"),
                        alertObj.getDouble("targetPrice"),
                        alertObj.getBoolean("isTriggered")
                );
                stockAlerts.add(alert);
            }
        } catch (IOException e) {
            // File doesn't exist yet, start with empty list
        }
    }

    private void saveAlerts() {
        JSONArray alertsArray = new JSONArray();
        for (StockAlert alert : stockAlerts) {
            JSONObject alertObj = new JSONObject();
            alertObj.put("userId", alert.getUserId());
            alertObj.put("stockSymbol", alert.getStockSymbol());
            alertObj.put("targetPrice", alert.getTargetPrice());
            alertObj.put("isTriggered", alert.isTriggered());
            alertsArray.put(alertObj);
        }

        try {
            Files.writeString(Path.of(this.filename), alertsArray.toString(2));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save stock alerts", e);
        }
    }

    public void addAlert(StockAlert alert) {
        stockAlerts.add(alert);
        saveAlerts();
    }

    public List<StockAlert> getUserAlerts(String userId) {
        List<StockAlert> userAlerts = new ArrayList<>();
        for (StockAlert alert : stockAlerts) {
            if (alert.getUserId().equals(userId) && !alert.isTriggered()) {
                userAlerts.add(alert);
            }
        }
        return userAlerts;
    }

    public void updateAlert(StockAlert alert) {
        // Remove old alert and add updated one
        stockAlerts.removeIf(a ->
                a.getUserId().equals(alert.getUserId()) &&
                        a.getStockSymbol().equals(alert.getStockSymbol())
        );
        stockAlerts.add(alert);
        saveAlerts();
    }
}
