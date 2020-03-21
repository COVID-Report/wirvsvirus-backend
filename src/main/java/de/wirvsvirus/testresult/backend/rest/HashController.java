package de.wirvsvirus.testresult.backend.rest;

import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.wirvsvirus.testresult.backend.tools.HashCalculator;

@RestController
@RequestMapping("/hashes")
public class HashController {
	@Autowired
	DateTimeFormatter dateTimeFormatter;
	
	@GetMapping
	public String getHashString( @RequestParam("sampleId") String sampleId,@RequestParam("name")String name , @RequestParam("birthday") String birthday) {
		return HashCalculator.calculcateId(sampleId, name, dateTimeFormatter.parseLocalDate(birthday));
	}

}
