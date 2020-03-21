package de.wirvsvirus.testresult.backend.tools;

import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.LocalDate;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HashCalculator {
	
	private static final String DELIMITER = ":";
	
	public static String calculcateId(String sampleId,String name , LocalDate birthday) {
		
		return DigestUtils.sha256Hex(sampleId+DELIMITER+name+DELIMITER+birthday.toString());
	}
	

}
