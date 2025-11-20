package use_case.monthly_report;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import use_case.monthly_report.MonthlyReportView;

/**
 * QuickChartService
 *
 * A small wrapper around the QuickChart HTTP API:
 *   https://quickchart.io/chart
 *
 * It takes Java Map data and returns chart images as BufferedImage objects
 * that you can draw in a Swing JPanel.
 */
public class MonthlyReportAPI {

    // Base URL for the QuickChart API
    private static final String QUICKCHART_URL = "https://quickchart.io/chart";
    // format: https://quickchart.io/chart?c={type:'bar',data:{labels:[2012,2013,2014,2015, 2016],
    // datasets:[{label:'Users',data:[120,60,50,180,120]}]}}

    // Shared HTTP client
    private final HttpClient client = HttpClient.newHttpClient();

    /**
     * Generate a line chart image from day -> amount data.
     *
     * @param dayToAmount map of day-of-month to value (e.g. {1=100.0, 4=200.0})
     * @param title       chart title (e.g. "Daily Spending 2025/11")
     * @return BufferedImage PNG of the chart
     * @throws IOException          if the response cannot be read as an image
     * @throws InterruptedException if the HTTP request is interrupted
     */
    public BufferedImage generateLineChart(Map<Integer, Double> dayToAmount,
                                           String title) throws IOException, InterruptedException {

        // Build labels and data arrays from the map
        JSONArray labels = new JSONArray();
        JSONArray values = new JSONArray();

        JSONObject line_data = MonthlyReportViewModel.getLineGraph(int year, int month);


//        dayToAmount.keySet().stream().sorted().forEach(day -> {
//            labels.put(String.valueOf(day));
//            values.put(dayToAmount.get(day));
//        });
//
//        // Build Chart.js config: https://www.chartjs.org/docs/latest/
//        JSONObject config = new JSONObject();
//        config.put("type", "line");
//
//        JSONObject data = new JSONObject();
//        data.put("labels", labels);
//
//        JSONObject dataset = new JSONObject();
//        dataset.put("label", title);
//        dataset.put("data", values);
//        // you could also set colors, line tension, etc. here
//
//        data.put("datasets", new JSONArray().put(dataset));
//        config.put("data", data);
//
//        // optional options (responsive, etc.)
//        JSONObject options = new JSONObject();
//        options.put("responsive", true);
//        config.put("options", options);
//
//        // Wrap Chart.js config in the body QuickChart expects
//        JSONObject body = new JSONObject();
//        body.put("chart", config);

        // Build HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(QUICKCHART_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        // Send request
        HttpResponse<InputStream> response =
                client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() != 200) {
            throw new IOException("QuickChart line API error: HTTP " + response.statusCode());
        }

        // Convert the response stream (PNG) into a BufferedImage
        return ImageIO.read(response.body());
    }

    /**
     * Generate a pie chart image from category -> amount data.
     *
     * @param categoryToAmount map of category to value
     *                         (e.g. {"DINING"=800.0, "GROCERIES"=300.0})
     * @param title            chart title (e.g. "Spending by Category 2025/11")
     * @return BufferedImage PNG of the chart
     * @throws IOException          if the response cannot be read as an image
     * @throws InterruptedException if the HTTP request is interrupted
     */
//    public BufferedImage generatePieChart(Map<String, Double> categoryToAmount,
//                                          String title) throws IOException, InterruptedException {
//
//        JSONArray labels = new JSONArray();
//        JSONArray values = new JSONArray();
//
//        for (Map.Entry<String, Double> entry : categoryToAmount.entrySet()) {
//            labels.put(entry.getKey());
//            values.put(entry.getValue());
//        }
//
//        JSONObject config = new JSONObject();
//        config.put("type", "pie");
//
//        JSONObject data = new JSONObject();
//        data.put("labels", labels);
//
//        JSONObject dataset = new JSONObject();
//        dataset.put("data", values);
//        data.put("datasets", new JSONArray().put(dataset));
//
//        config.put("data", data);
//
//        JSONObject options = new JSONObject();
//        // Example: put legend at the bottom
//        JSONObject plugins = new JSONObject();
//        JSONObject legend = new JSONObject();
//        legend.put("position", "bottom");
//        plugins.put("legend", legend);
//        options.put("plugins", plugins);
//
//        config.put("options", options);
//
//        // Wrap chart config
//        JSONObject body = new JSONObject();
//        body.put("chart", config);
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(QUICKCHART_URL))
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
//                .build();
//
//        HttpResponse<InputStream> response =
//                client.send(request, HttpResponse.BodyHandlers.ofInputStream());
//
//        if (response.statusCode() != 200) {
//            throw new IOException("QuickChart pie API error: HTTP " + response.statusCode());
//        }
//
//        return ImageIO.read(response.body());
    }
}
