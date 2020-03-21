package de.wirvsvirus.testresult.backend.model;

import lombok.Data;

@Data
public class PushMessage {

	private String contact;
	private String text;
	private String from;
	
}
