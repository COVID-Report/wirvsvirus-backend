package de.wirvsvirus.testresult.backend.rest;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.wirvsvirus.testresult.backend.model.TestProcess;
import de.wirvsvirus.testresult.backend.service.TestProcessService;

@RestController
@RequestMapping("/tests")
public class TestProcessController {

	
	@Autowired
	private TestProcessService testProcessService;
	
	private DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
	
	@GetMapping("/{labName}/{testId}/{dateOfBirth}")
	public TestProcess getTestResult(@PathVariable("labName")String labName,@PathVariable("testId") String sampleId, @PathVariable("dateOfBirth") String birthday) {
		return testProcessService.getTestProcess(labName, sampleId, formatter.parseLocalDate(birthday));
		
	}
	
	@PostMapping("/{labName}/{testId}/{dateOfBirth}")
	public void addTestProcess(@PathVariable("labName")String labName,@PathVariable("testId") String sampleId, @PathVariable("dateOfBirth") String birthday){
		testProcessService.createTestProcess(labName, sampleId, formatter.parseLocalDate(birthday));
	}
	
	
	@PutMapping("/{labName}/{testId}/result")
	public void addTestProcessResult(@PathVariable("labName")String labName,@PathVariable("testId") String sampleId, @RequestBody  TestProcess.Result result ){
		System.out.println(result);
		testProcessService.updateResultForProcess(labName,sampleId,result);
	}

	
}
