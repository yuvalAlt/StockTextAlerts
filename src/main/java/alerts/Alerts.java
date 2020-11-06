package alerts;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.type.TypeReference;
import twilio.TwilioClient;
import yahoo.Stocks;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Alerts {
    public static void main(String[] args) throws IOException {
        Stocks yahooClient = new Stocks();
        TwilioClient twilioClient = new TwilioClient();

        String textFormat = "%s has gone up by %s!";
        float priceChange;
        StringBuilder textMessage = null;

        ClassLoader classLoader = Alerts.class.getClassLoader();
        File watchlist = new File(classLoader.getResource("watchlist.json").getFile());

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = (ObjectNode) objectMapper.readTree(watchlist);
        JsonNode jsonNode = objectNode.get("tickers");

        ArrayList<String> watchListTickers = objectMapper.readValue(jsonNode.traverse(), new TypeReference<ArrayList<String>>(){});

        for (String ticker : watchListTickers) {
            Map<String, String> response = yahooClient.getStock(ticker).getPrice().getRegularMarketChangePercent();
            priceChange = Float.parseFloat(response.get("raw")) * 100;

            if (priceChange > 5) {
                if (textMessage == null) {
                    textMessage = new StringBuilder(String.format(textFormat, ticker, response.get("fmt")) + "\n");
                }
                else {
                    textMessage.append(String.format(textFormat, ticker, response.get("fmt")) + "\n");
                }
            }

            watchListTickers.set(watchListTickers.indexOf(ticker), ticker + " " + response.get("fmt"));
        }

        if (textMessage != null && !textMessage.toString().isEmpty()) {
            twilioClient.sendText(textMessage.toString().trim());
//            System.out.println(textMessage.toString().trim());
        }

        StringBuilder dailySummary = new StringBuilder("Today's changes:");
        for (String stockDailyChange : watchListTickers) {
            dailySummary.append("\n" + stockDailyChange);
        }

//        System.out.println(dailySummary.toString());
        twilioClient.sendText(dailySummary.toString());

        twilioClient.closeClient();
    }
}