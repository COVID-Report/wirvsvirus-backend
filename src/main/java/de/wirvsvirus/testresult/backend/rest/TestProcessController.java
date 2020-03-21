package de.wirvsvirus.testresult.backend.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.wirvsvirus.testresult.backend.model.TestResult;
import de.wirvsvirus.testresult.backend.service.TestProcessService;

@RestController
@RequestMapping("/tests")
public class TestProcessController {

	
	@Autowired
	private TestProcessService testProcessService;
	
	@GetMapping("/{id}")
	public Optional<TestResult> getTestResult(@PathVariable("id")String id) {
		return testProcessService.getTestProcess(id);
		
	}
	
	@PostMapping("/{id}")
	public void addTestResult(@RequestBody TestResult testProcess){
		
		//TODO save
		//TODO execute push
		
	}
	
}
