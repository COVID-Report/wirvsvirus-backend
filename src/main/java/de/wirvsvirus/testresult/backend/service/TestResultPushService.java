package de.wirvsvirus.testresult.backend.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.wirvsvirus.testresult.backend.model.TestResult;
import de.wirvsvirus.testresult.backend.model.TestResult.Result;
import de.wirvsvirus.testresult.backend.persistence.TestResultRepo;
import de.wirvsvirus.testresult.backend.tools.SmsServiceSms4;

@Component
public class TestResultPushService {
	private final String MOBILE_PATTERN = "/^([+]\\d{2})?\\d{10}$/";

	public void executePush(TestResult testProcess) {
		if (testProcess.getStatus() != Result.NEGATIVE) {
			// only push for negative
			return;
		}
		String contact = testProcess.getContact();
		if (contact.matches(MOBILE_PATTERN))
			try {
				SmsServiceSms4.sendNegativeResultSms(contact);
			} catch (IOException e) {
				// ignore, they can still look up the result
			}
	}

}
