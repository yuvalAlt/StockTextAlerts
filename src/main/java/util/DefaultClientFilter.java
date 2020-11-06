package util;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;

import java.util.Map;
import java.util.Optional;

public class DefaultClientFilter extends ClientFilter {
    Map<String, String> headers;

    public DefaultClientFilter(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setHeaders(Map<String, String> newHeaders) {
        this.headers = newHeaders;
    }

    public void addHeaders(Map<String, String> newHeaders) {
        this.headers.putAll(newHeaders);
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public ClientResponse handle(ClientRequest clientRequest) throws ClientHandlerException {
        if (Optional.ofNullable(headers).isPresent() && !headers.isEmpty()) {
            for (Map.Entry<String, String> header : headers.entrySet() ) {
                if (!clientRequest.getHeaders().containsKey(header.getKey())) {
                    clientRequest.getHeaders().add(header.getKey(), header.getValue());
                }
            }
        }

        return getNext().handle(clientRequest);
    }
}