package de.wirvsvirus.testresult.backend.service;

import de.wirvsvirus.testresult.backend.exceptions.MailSendingException;
import de.wirvsvirus.testresult.backend.model.PushMessage;

public interface EmailService {

	void sendMail(PushMessage message)throws  MailSendingException;

	
}
