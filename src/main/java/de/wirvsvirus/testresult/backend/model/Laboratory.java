package de.wirvsvirus.testresult.backend.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Laboratory {

	@Indexed(unique = true)
	String name;
	
}
