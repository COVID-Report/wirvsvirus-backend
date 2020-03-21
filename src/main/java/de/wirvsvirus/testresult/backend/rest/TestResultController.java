package de.wirvsvirus.testresult.backend.rest;

import java.util.Optional;

import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.wirvsvirus.testresult.backend.model.TestResult;
import de.wirvsvirus.testresult.backend.service.TestResultService;
import de.wirvsvirus.testresult.backend.tools.HashCalculator;

@RestController
@RequestMapping("/tests")
public class TestResultController {

	
	@Autowired
	private TestResultService testResultService;
	
	@Autowired
	DateTimeFormatter dateTimeFormatter;
	
	@GetMapping("/{id}")
	public Optional<TestResult> getTestResult(@PathVariable("id")String id) {
		return testResultService.getTestResult(id);
		
	}
	
	@GetMapping
	public Optional<TestResult> getTestResult(@RequestParam("sampleId") String sampleId,@RequestParam("name")String name , @RequestParam("birthday") String birthday) {
		return testResultService.getTestResult(HashCalculator.calculcateId(sampleId, name, dateTimeFormatter.parseLocalDate(birthday)));
		
	}

	
	
	@PostMapping("/{id}")
	public void addTestResult(@PathVariable("id")String id,@RequestBody TestResult testResult){
		testResult.setId(id);
		testResultService.createTestProcess(testResult);
		//TODO execute push
		
	}
	
}
