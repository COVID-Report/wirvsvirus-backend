package de.wirvsvirus.testresult.backend.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import de.wirvsvirus.testresult.backend.exceptions.SmsSendingException;
import de.wirvsvirus.testresult.backend.model.PushMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Profile("twilio")
@Slf4j
public class SmsServiceTwilio implements SmsServiceProvider {

    @Value("$(twilio.accountSid)")
    private String accountSid;
    @Value("$(twilio.authToken)")
    private String authToken;
    @Value("$(twilio.phoneNumerFrom)")
    private String phoneNumerFrom;

    public void sendNegativeResultSms(PushMessage message) throws SmsSendingException {
        Twilio.init(accountSid, authToken);

        Message twilioMessage = Message
                                .creator(new PhoneNumber(message.getContact()),
                                         new PhoneNumber(phoneNumerFrom),
                                         message.getText())
                                .create();

        log.info("Send message, response status={}", twilioMessage.getStatus());
    }

}