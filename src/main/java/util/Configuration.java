package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class Configuration {
    private String twilioSid = "TWILIO_ACCCOUNT_SID";
    private String twilioToken = "TWILIO_AUTH_TOKEN";
    private String phoneTo = "TWILIO_PHONE_NUMBER_TO";
    private String phoneFrom = "TWILIO_PHONE_NUMBER_FROM";
    private String yahooAuthKey = "YAHOO_AUTH_KEY";
    private String yahooHost = "YAHOO_HOST";

    Properties properties = new Properties();
    String configFileName = "config.properties";
    InputStream inputStream;

    public Configuration() throws RuntimeException {}

    public void init() {
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(configFileName);

            if (!Optional.ofNullable(inputStream).isPresent()) {
                throw new FileNotFoundException("Properties file could not be found");
            }

            properties.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() throws IOException {
        inputStream.close();
    }

    public String getTwilioSid() {
        return properties.getProperty(twilioSid);
    }

    public String getTwilioToken() {
        return properties.getProperty(twilioToken);
    }

    public String getPhoneTo() {
        return properties.getProperty(phoneTo);
    }

    public String getPhoneFrom() {
        return properties.getProperty(phoneFrom);
    }

    public String getYahooAuthKey() {
        return properties.getProperty(yahooAuthKey);
    }

    public String getYahooHost() {
        return properties.getProperty(yahooHost);
    }
}