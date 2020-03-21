package de.wirvsvirus.testresult.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.wirvsvirus.testresult.backend.model.TestResult;
import de.wirvsvirus.testresult.backend.persistence.TestResultRepo;

@Component
public class TestProcessService {
	@Autowired
	private TestResultRepo testRepo;
	
	public Optional<TestResult> getTestProcess(String id) {
		
		return testRepo.findById(id);
	}
	
	public void createTestProcess(TestResult p) {
		p.setContact(null);
		testRepo.save(p);
	}


}
