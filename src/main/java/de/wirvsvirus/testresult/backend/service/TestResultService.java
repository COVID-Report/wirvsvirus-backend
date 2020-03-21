package de.wirvsvirus.testresult.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.wirvsvirus.testresult.backend.model.TestResult;
import de.wirvsvirus.testresult.backend.persistence.TestResultRepo;

@Component
public class TestResultService {
	@Autowired
	private TestResultRepo testRepo;
	
	public Optional<TestResult> getTestResult(String id) {
		
		return testRepo.findById(id);
	}
	
	public void createTestProcess(TestResult p) {
		
		TestResult save = new TestResult();
		save.setId(p.getId());
		save.setName(p.getName());
		save.setStatus(p.getStatus());
		testRepo.save(save);
	}


}
