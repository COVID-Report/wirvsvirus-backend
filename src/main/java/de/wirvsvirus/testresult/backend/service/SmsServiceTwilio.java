package de.wirvsvirus.testresult.backend.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import de.wirvsvirus.testresult.backend.model.PushMessage;

public class SmsServiceTwilio implements SmsServiceProvider {

    private static final String ACCOUNT_SID = "AC280fe240b4af20dc7c4718ee34404b4e";
    private static final String AUTH_TOKEN = "03738f34c8c12e70950a12f883bd91b5";
    private static final String FROM = "+12058464551";
    private static final String TO = "+491799966985";

    public void sendNegativeResultSms(PushMessage message) throws SmsSendingException {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message twilioMessage = Message
                                // .creator(new PhoneNumber(message.getContact()),
                                .creator(new PhoneNumber(TO),
                                         new PhoneNumber(FROM),
                                         message.getText())
                                .create();
    }

}