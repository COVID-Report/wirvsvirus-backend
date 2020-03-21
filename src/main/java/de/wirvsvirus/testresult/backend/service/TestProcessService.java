package de.wirvsvirus.testresult.backend.service;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.wirvsvirus.testresult.backend.model.TestProcess;
import de.wirvsvirus.testresult.backend.model.TestProcess.Result;
import de.wirvsvirus.testresult.backend.persistence.TestProcessRepo;

@Component
public class TestProcessService {
	@Autowired
	private TestProcessRepo testRepo;
	
	public TestProcess getTestProcess(String labName, String sampleId, LocalDate dateOfBirth) {
		
		return testRepo.findByLabNameAndSampleIdAndDateOfBirth(labName, sampleId, dateOfBirth.toString());
	}
	
	public void createTestProcess(String labName, String sampleId, LocalDate dateOfBirth) {
		TestProcess p = new TestProcess();
		p.setDateOfBirth(dateOfBirth.toString());
		p.setLabName(labName);
		p.setSampleId(sampleId);
		p.setStatus(Result.PENDING);
		testRepo.save(p);
	}

	public void updateResultForProcess(String labName, String sampleId, Result result) {
		TestProcess p = testRepo.findByLabNameAndSampleId(labName, sampleId);
		p.setStatus(result);
		testRepo.save(p);
	}
	

}
