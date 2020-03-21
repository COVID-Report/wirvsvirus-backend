package de.wirvsvirus.testresult.backend.exceptions;

public class SmsSendingException extends Exception{
	private static final long serialVersionUID = 1L;

	public SmsSendingException(String string) {
		super(string);
	}

	public SmsSendingException(Throwable e) {
		super(e);
	}


}
