package de.wirvsvirus.testresult.backend.service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import de.wirvsvirus.testresult.backend.exceptions.MailSendingException;
import de.wirvsvirus.testresult.backend.exceptions.SmsSendingException;
import de.wirvsvirus.testresult.backend.model.PushMessage;
import de.wirvsvirus.testresult.backend.model.TestResult;
import de.wirvsvirus.testresult.backend.model.TestResult.Result;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TestResultPushService {
	private static final String MOBILE_PATTERN = "[+491|01]\\d+";
	private static final String MOBILE_CLEANUP_PATTERN = "[^(\\d|+)]";
	
	private static final String FROM_VALUE = "Krankenhaus";
	private static final String NEGATIV_TEXT = "Wir freuen uns Ihnen mitteilen zu können, dass ihr COVID-19 Testergebnis negativ ist. Der Virus konnte bei Ihnen nicht festegestellt werden.";


	@Autowired
	private SmsServiceProvider smsService;
	
	@Autowired
	private EmailService emailService;
	
	public boolean executePush(TestResult testProcess) {
		if (testProcess.getStatus() != Result.NEGATIVE ||StringUtils.isEmpty(testProcess.getContact() )) {
			// only push for negative
			return false;
		}
		
		String contact = StringUtils.trimWhitespace(testProcess.getContact());
		
		PushMessage message = createMessage();
		
		boolean pushDone=false;
		
		if(isValidEmailAddress(contact)) {
			message.setContact(contact);
			try {
				emailService.sendMail(message);
				pushDone=true;
			} catch (MailSendingException e) {
				log.debug("sending mail failed",e);
			}
		}else if (contact.replaceAll(MOBILE_CLEANUP_PATTERN, "").matches(MOBILE_PATTERN)) {
			try {
				message.setContact(contact.replaceAll(MOBILE_CLEANUP_PATTERN, ""));
				smsService.sendNegativeResultSms(message);
				pushDone=true;
			} catch (SmsSendingException e) {
				log.debug("sms sending failed",e);
			}
		}
		return pushDone;
	}

	private PushMessage createMessage() {
		PushMessage message = new PushMessage();
		message.setFrom(FROM_VALUE);
		message.setText(NEGATIV_TEXT);
		return message;
	}
	
	public static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
		}
}
