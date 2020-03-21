package de.wirvsvirus.testresult.backend.model;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
@CompoundIndex(def = "{'lab': 1, 'sampleId': 1}",unique = true,background = true)
public class TestProcess {

	String id;
	Result status;
	String dateOfBirth;
	String labName;
	String sampleId;
	
	public enum Result { POSITIVE, NEGATIVE, PENDING, NONE}
	
}
