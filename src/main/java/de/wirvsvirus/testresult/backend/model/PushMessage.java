package de.wirvsvirus.testresult.backend.model;

import lombok.Data;

@Data
public class PushMessage {

	private String contact;
	private String text;
	private String from;


	PushMessage(String contact, String text, String from) {
		this.contact = contact;
		this.text = text;
		this.from = from;
	}

	public String getContact() {
		return contact;
	}

	public String getText() {
		return text;
	}

	public String getFrom() {
		return from;
	}
	
}
