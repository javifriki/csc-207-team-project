package use_case.monthly_report;

import data_access.MonthlyReportDataAccessObject;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MonthlyReportAPI {

    private static final String QUICKCHART_URL = "https://quickchart.io/chart?c=";
    private static final HttpClient client = HttpClient.newHttpClient();


    public static BufferedImage generateLineChart(int year, int month)
            throws IOException, InterruptedException {

        JSONObject monthData = MonthlyReportDataAccessObject.getMonthData(year, month);
        JSONObject lineData  = monthData.getJSONObject("graph_data");

        List<String> labels = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();

        // Calculate Balances
        double openingBalance = MonthlyReportDataAccessObject.getOpeningBalance(year, month);
        double cumulative = openingBalance;

        for (int day = 1; day <= daysInMonth; day++) {
            String key = String.valueOf(day);
            double valForDay = lineData.optDouble(key, 0.0);
            cumulative += valForDay;

            labels.add(key);
            values.add(cumulative);
        }

        // Line Graph API Request Format
        // {
        //  "type": "line",
        //  "data": {
        //    "labels": ["1", "2", ..., "31"],
        //    "datasets": [
        //      {
        //        "label": "Current Balance",
        //        "data": [0.0, ... 0.0],
        //        "borderColor": "black",
        //        "fill": false
        //      }
        //    ]
        //  },
        //  "options": {
        //    "title": {
        //      "display": true,
        //      "text": "Balance Tracker for year-month"
        //    },
        //    "legend": {
        //      "display": false
        //    },
        //    "scales": {
        //      "yAxes": [
        //        {
        //          "ticks": { "beginAtZero": true },
        //          "scaleLabel": {
        //            "display": true,
        //            "labelString": "Current Balance"
        //          }
        //        }
        //      ],
        //      "xAxes": [
        //        {
        //          "scaleLabel": {
        //            "display": true,
        //            "labelString": "Day of Month"
        //          }
        //        }
        //      ]
        //    }
        //  }
        //}

        String title = "Balance Tracker for " + year + "-" + month;

        JSONObject config = new JSONObject()
                .put("type", "line")
                .put("data", new JSONObject()
                        .put("labels", labels)
                        .put("datasets", new JSONArray()
                                .put(new JSONObject()
                                        .put("label", "Current Balance")
                                        .put("data", values)
                                        .put("borderColor", "black")
                                        .put("fill", false)
                                )
                        )
                )
                .put("options", new JSONObject()
                        .put("title", new JSONObject()
                                .put("display", true)
                                .put("text", title)
                        )
                        .put("legend", new JSONObject()
                                .put("display", false)
                        )
                        .put("scales", new JSONObject()
                                // Y-axis
                                .put("yAxes", new JSONArray()
                                        .put(new JSONObject()
                                                .put("ticks", new JSONObject()
                                                        .put("beginAtZero", true)
                                                )
                                                .put("scaleLabel", new JSONObject()
                                                        .put("display", true)
                                                        .put("labelString", "Current Balance")
                                                )
                                        )
                                )
                                // X-axis
                                .put("xAxes", new JSONArray()
                                        .put(new JSONObject()
                                                .put("scaleLabel", new JSONObject()
                                                        .put("display", true)
                                                        .put("labelString", "Day of Month")
                                                )
                                        )
                                )
                        )

                );

        String encoded = URLEncoder.encode(config.toString(), StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(QUICKCHART_URL + encoded))
                .GET()
                .build();

        HttpResponse<InputStream> response =
                client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() != 200) {
            throw new IOException("QuickChart line API error: HTTP " + response.statusCode());
        }

        return ImageIO.read(response.body());
    }




    public static BufferedImage generatePieChart(int year, int month)
            throws IOException, InterruptedException {

        JSONObject monthData = MonthlyReportDataAccessObject.getMonthData(year, month);
        JSONObject pieData = monthData.getJSONObject("category_totals");

        List<String> labels = new ArrayList<>();
        List<Double> values = new ArrayList<>();
        // this is for labels to show the amount instead of percentages
//        List<String> values = new ArrayList<>();
//        for (Iterator<String> it = pieData.keys(); it.hasNext(); ) {
//            String key = it.next();
//            Object value = pieData.get(key);
//            labels.add(key);
//            values.add(String.valueOf(value));
//        }

        // Calculate percentages
        double total = 0.0;
        for (Iterator<String> it = pieData.keys(); it.hasNext(); ) {
            String key = it.next();
            double amount = pieData.optDouble(key, 0.0);
            total += amount;
        }

        for (Iterator<String> it = pieData.keys(); it.hasNext(); ) {
            String key = it.next();
            double amount = pieData.optDouble(key, 0.0);

            double percent;
            if (total == 0.0) {
                percent = 0.0;
            } else {
                percent = Math.round(amount * 100.0 / total);
            }

            labels.add(key);
            values.add(percent);
        }

        // Pie Chart API Request Format
        // {
        //  "type": "pie",
        //  "plugins": ["datalabels"],
        //  "data": {
        //    "labels": ["DINING", "GROCERIES", ..., "HEALTHCARE"],
        //    "datasets": [
        //      {
        //        "data": [40, ... , 0]
        //      }
        //    ]
        //  },
        //  "options": {
        //    "title": {
        //      "display": true,
        //      "text": "Spending Categories YEAR-MONTH"
        //    },
        //    "legend": {
        //      "display": true,
        //      "position": "top"
        //    },
        //    "plugins": {
        //      "datalabels": {
        //        "display": true,
        //        "color": "white",
        //        "font": { "size": 14 },
        //        "formatter": "function(value){ if(value === 0) return ''; return value + '%'; }"
        //      }
        //    }
        //  }
        //}

        String title = "Spending Categories " + year + "-" + month;

        String config = new JSONObject()
                .put("type", "pie")
                .put("plugins", new JSONArray().put("datalabels"))
                .put("data", new JSONObject()
                        .put("labels", labels)
                        .put("datasets", new JSONArray()
                                .put(new JSONObject()
                                        .put("data", values)
                                )
                        )
                )
                .put("options", new JSONObject()
                        .put("title", new JSONObject()
                                .put("display", true)
                                .put("text", title)
                        )
                        .put("legend", new JSONObject()
                                .put("display", true)
                                .put("position", "top")
                        )
                        .put("plugins", new JSONObject()
                                .put("datalabels", new JSONObject()
                                        .put("display", true)
                                        .put("color", "white")
                                        .put("font", new JSONObject().put("size", 14))
                                        .put("formatter",
                                                "function(value){ " +
                                                        "  if (value === 0) return ''; " +
                                                        "  return value + '%'; " +
                                                        "}"
                                        )
                                )
                        )
                )
                .toString();

        String encoded = URLEncoder.encode(config, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(QUICKCHART_URL + encoded))
                .GET()
                .build();

        HttpResponse<InputStream> response =
                client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() != 200) {
            throw new IOException("QuickChart pie API error: HTTP " + response.statusCode());
        }

        return ImageIO.read(response.body());
    }
}
