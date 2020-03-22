package de.wirvsvirus.testresult.backend.rest;

import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.TextNode;

import de.wirvsvirus.testresult.backend.tools.HashCalculator;

@RestController
@RequestMapping("/hashes")
public class HashController {
	@Autowired
	DateTimeFormatter dateTimeFormatter;
	
	@GetMapping
	public TextNode getHashString( @RequestParam("sampleId") String sampleId,@RequestParam("name")String name , @RequestParam("birthday") String birthday) {
		return TextNode.valueOf(HashCalculator.calculcateId(sampleId, name, dateTimeFormatter.parseLocalDate(birthday)));
	}

}
