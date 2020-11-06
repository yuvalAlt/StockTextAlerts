package twilio;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import util.Configuration;

import java.io.IOException;

public class TwilioClient {
    Configuration configuration = new Configuration();

    public TwilioClient() {
        configuration.init();
        Twilio.init(configuration.getTwilioSid(), configuration.getTwilioToken());
    }

    public void sendText(String textContent) {
        Message message = Message.creator(
                new PhoneNumber(configuration.getPhoneTo()),
                new PhoneNumber(configuration.getPhoneFrom()),
                textContent
                ).create();
    }

    public void closeClient() throws IOException {
        Twilio.destroy();
        configuration.close();
    }
}