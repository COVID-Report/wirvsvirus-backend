package de.wirvsvirus.testresult.backend.exceptions;

import java.io.IOException;

public class MailSendingException extends Exception {

	public MailSendingException(IOException ex) {
		super(ex);
	}

	public MailSendingException(String string) {
		super(string);
	}

}
