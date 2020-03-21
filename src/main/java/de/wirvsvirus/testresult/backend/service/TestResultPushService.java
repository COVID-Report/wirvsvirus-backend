package de.wirvsvirus.testresult.backend.service;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import de.wirvsvirus.testresult.backend.model.TestResult;
import de.wirvsvirus.testresult.backend.model.TestResult.Result;
import de.wirvsvirus.testresult.backend.tools.SmsServiceVonage;

@Component
public class TestResultPushService {
	private final String MOBILE_PATTERN = "[+|0]\\d+";
	private final String MOBILE_CLEANUP_PATTERN = "[^(\\d|+)]";

	public void executePush(TestResult testProcess) {
		if (testProcess.getStatus() != Result.NEGATIVE ||StringUtils.isEmpty(testProcess.getContact() )) {
			// only push for negative
			return;
		}
		String contact = testProcess.getContact().replaceAll(MOBILE_CLEANUP_PATTERN, "");
		if (contact.matches(MOBILE_PATTERN))
			try {
				SmsServiceVonage.sendNegativeResultSms(contact);
			} catch (IOException e) {
				// ignore, they can still look up the result
			}
	}
}
