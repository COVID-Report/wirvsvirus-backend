package de.wirvsvirus.testresult.backend.exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FalseInformedException extends Exception {

	public FalseInformedException(IOException ex) {
		super(ex);
	}

	public FalseInformedException(String string) {
		super(string);
	}

}
