package de.wirvsvirus.testresult.backend.model;

import java.util.Date;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
@CompoundIndex(def = "{'lab': 1, 'sampleId': 1}",unique = true,background = true)
public class TestResult {

	Status status;
	Date birthday;
	Laboratory lab;
	String sampleId;
	
	public enum Status { POSITIVE, NEGATIVE, PENDING, NONE}
	
}
