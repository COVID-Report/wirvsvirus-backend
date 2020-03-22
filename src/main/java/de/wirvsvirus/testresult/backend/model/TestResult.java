package de.wirvsvirus.testresult.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class TestResult {

	String id;
	Result status;
	String contact;
	boolean notified;
	
	public enum Result { POSITIVE, NEGATIVE, PENDING, NONE}
	
}
