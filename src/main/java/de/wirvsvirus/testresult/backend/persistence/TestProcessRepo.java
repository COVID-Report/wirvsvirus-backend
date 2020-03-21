package de.wirvsvirus.testresult.backend.persistence;

import org.springframework.data.repository.CrudRepository;

import de.wirvsvirus.testresult.backend.model.TestProcess;

public interface TestProcessRepo extends CrudRepository<TestProcess, String> {

	public TestProcess findByLabNameAndSampleIdAndDateOfBirth(String labName, String sampleId, String dateOfBirth);

	public TestProcess findByLabNameAndSampleId(String labName, String sampleId);
	
}
