package yahoo;


import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import util.Configuration;
import util.DefaultClientFilter;
import yahoo.objects.StockResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Stocks {

    private WebResource source;

    public Stocks() throws IOException {
        DefaultClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJaxbJsonProvider.class);
        Client client = Client.create(config);

        Configuration propertiesConfig = new Configuration();
        propertiesConfig.init();

        Map<String, String> headers = new HashMap();
        headers.put("x-rapidapi-key", propertiesConfig.getYahooAuthKey());
        headers.put("x-rapidapi-host", propertiesConfig.getYahooHost());

        client.addFilter(new DefaultClientFilter(headers));
        this.source = client.resource("https://rapidapi.p.rapidapi.com/stock/v2/get-statistics");

        propertiesConfig.close();
    }

    public StockResponse getStock(String stockTicker) throws IOException {
        return source.queryParam("symbol", stockTicker).queryParam("region", "US").get(StockResponse.class);
    }
}