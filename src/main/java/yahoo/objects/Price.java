package yahoo.objects;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {
    private Map<String, String> regularMarketChangePercent;

    public Map<String, String> getRegularMarketChangePercent() {
        return regularMarketChangePercent;
    }

    public void setRegularMarketChangePercent(Map<String, String> regularMarketChangePercent) {
        this.regularMarketChangePercent = regularMarketChangePercent;
    }
}