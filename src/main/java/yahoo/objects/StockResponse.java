package yahoo.objects;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StockResponse {
    private Price price;

    private Map<Object, Object> props;

    public Price getPrice() {
        return this.price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    @JsonAnyGetter
    public Map<Object, Object> getProps() {
        return props;
    }

    @JsonAnySetter
    public void setProps(Map<Object, Object> props) {
        this.props = props;
    }
}