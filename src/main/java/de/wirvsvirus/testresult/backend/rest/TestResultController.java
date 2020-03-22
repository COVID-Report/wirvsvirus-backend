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
import de.wirvsvirus.testresult.backend.service.TestResultPushService;
import de.wirvsvirus.testresult.backend.service.TestResultService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/tests")
@Slf4j
public class TestResultController {

	@Autowired
	private TestResultService testResultService;
	@Autowired
	private TestResultPushService pushService;

	@GetMapping("/{id}")
	public Optional<TestResult> getTestResult(@PathVariable("id") String id) {
		return testResultService.getTestResult(id);

	}

	@PostMapping("/{id}")
	public TestResult addTestResult(@PathVariable("id") String id, @RequestBody TestResult testResult) {
		testResult.setId(id);
		TestResult saveResult;
		try {
			testResult.setNotified(pushService.executePush(testResult));
		} catch (Exception ex) {
			log.error("error during push", ex);
		} finally {
			saveResult = testResultService.createTestProcess(testResult);
		}
		return saveResult;
	}

}
