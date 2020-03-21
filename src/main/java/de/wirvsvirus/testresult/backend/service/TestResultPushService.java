package de.wirvsvirus.testresult.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import de.wirvsvirus.testresult.backend.exceptions.SmsSendingException;
import de.wirvsvirus.testresult.backend.model.SmsMessage;
import de.wirvsvirus.testresult.backend.model.TestResult;
import de.wirvsvirus.testresult.backend.model.TestResult.Result;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TestResultPushService {
	private static  final String MOBILE_PATTERN = "[+|0]\\d+";
	private static final String MOBILE_CLEANUP_PATTERN = "[^(\\d|+)]";
	
	
	private static final String FROM_VALUE = "Krankenhaus";
	private static final String NEGATIV_TEXT = "Wir freuen uns Ihnen mitteilen zu können, dass ihr COVID-19 Testergebnis negativ ist. Der Virus konnte bei Ihnen nicht festegestellt werden.";


	@Autowired
	private SmsServiceProvider smsService;
	
	public void executePush(TestResult testProcess) {
		if (testProcess.getStatus() != Result.NEGATIVE ||StringUtils.isEmpty(testProcess.getContact() )) {
			// only push for negative
			return;
		}
		String contact = testProcess.getContact().replaceAll(MOBILE_CLEANUP_PATTERN, "");
		if (contact.matches(MOBILE_PATTERN))
			try {
				SmsMessage message = new SmsMessage();
				message.setFrom(FROM_VALUE);
				message.setNumber(contact);
				message.setText(NEGATIV_TEXT);
				smsService.sendNegativeResultSms(message);
			} catch (SmsSendingException e) {
				log.debug("sms sending failed",e);
			}
	}
}
