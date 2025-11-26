package use_case.monthly_report;

import interface_adaptor.monthly_report.MonthlyReportViewModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MonthlyReportAPI {


    private static final String QUICKCHART_URL = "https://quickchart.io/chart";

    private static final HttpClient client = HttpClient.newHttpClient();


    public static BufferedImage generateLineChart(
            int year, int month) throws IOException, InterruptedException {

        JSONObject line_data = MonthlyReportViewModel.getLineData(year, month);
        ArrayList<String> line_keysList = new ArrayList<>();
        ArrayList<String> line_valuesList = new ArrayList<>();
        for (Iterator<String> it = line_data.keys(); it.hasNext(); ) {
            String key = it.next();
            Object value = line_data.get("key");
            line_keysList.add(key);
            line_valuesList.add(String.valueOf(value));
        }
        String line_title = "Line Graph for " + year + "-" + month;
        String line_config = new JSONObject()
                .put("type", "line")
                .put("data", new JSONObject()
                        .put("labels", line_keysList)
                        .put("datasets", new JSONArray()
                                .put(new JSONObject()
                                        .put("label", line_title)
                                        .put("data", line_valuesList)
                                        .put("borderColor", "black")
                                        .put("fill", false)
                                )
                        )
                )
                .put("options", new JSONObject()
                        .put("plugins", new JSONObject()
                                .put("title", new JSONObject()
                                        .put("display", true)
                                        .put("text", line_title)
                                )
                        )
                )
                .toString();

        String encoded = URLEncoder.encode(line_config, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(QUICKCHART_URL + encoded))
                .GET()
                .build();

        HttpResponse<InputStream> response =
                client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() != 200) {
            throw new IOException("QuickChart pie API error: HTTP " + response.statusCode());
        }
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        return ImageIO.read(response.body());
    }


    public static BufferedImage generatePieChart(int year, int month) throws IOException, InterruptedException {
        JSONObject pie_data = MonthlyReportViewModel.getPieData(year, month);
        ArrayList<String> pie_keysList = new ArrayList<>();
        ArrayList<String> pie_valuesList = new ArrayList<>();
        for (Iterator<String> it = pie_data.keys(); it.hasNext(); ) {
            String key = it.next();
            Object value = pie_data.get("key");
            pie_keysList.add(key);
            pie_valuesList.add(String.valueOf(value));
        }

        String pie_title = "Pie Chart for " + year + "-" + month;
        String pie_config = new JSONObject()
                .put("type", "pie")
                .put("data", new JSONObject()
                        .put("labels", pie_keysList)
                        .put("datasets", new JSONArray()
                                .put(new JSONObject()
                                        .put("label", pie_title)
                                        .put("data", pie_valuesList)
                                        .put("borderColor", "black")
                                        .put("fill", false)
                                )
                        )
                )
                .put("options", new JSONObject()
                        .put("plugins", new JSONObject()
                                .put("title", new JSONObject()
                                        .put("display", true)
                                        .put("text", pie_title)
                                )
                        )
                )
                .toString();

        String encoded = URLEncoder.encode(pie_config, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(QUICKCHART_URL + encoded))
                .GET()
                .build();


        HttpResponse<InputStream> response =
                client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() != 200) {
            throw new IOException("QuickChart pie API error: HTTP " + response.statusCode());
        }
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        return ImageIO.read(response.body());

    }
}