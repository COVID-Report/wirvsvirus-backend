package de.wirvsvirus.testresult.backend.model;

import lombok.Data;

@Data
public class SmsMessage {

	private String number;
	private String text;
	private String from;
	
}
