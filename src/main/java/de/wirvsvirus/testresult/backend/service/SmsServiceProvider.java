package de.wirvsvirus.testresult.backend.service;

import de.wirvsvirus.testresult.backend.exceptions.SmsSendingException;
import de.wirvsvirus.testresult.backend.model.PushMessage;

public interface SmsServiceProvider {
	public void sendNegativeResultSms(PushMessage message) throws SmsSendingException;
}
