package de.wirvsvirus.testresult.backend.service;

import de.wirvsvirus.testresult.backend.exceptions.SmsSendingException;
import de.wirvsvirus.testresult.backend.model.SmsMessage;

public interface SmsServiceProvider {
	public void sendNegativeResultSms(SmsMessage message) throws SmsSendingException;
}
