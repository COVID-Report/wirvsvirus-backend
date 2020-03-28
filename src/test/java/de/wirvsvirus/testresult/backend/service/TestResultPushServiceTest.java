package de.wirvsvirus.testresult.backend.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.wirvsvirus.testresult.backend.model.TestResult;
import de.wirvsvirus.testresult.backend.model.TestResult.Result;

@ExtendWith(MockitoExtension.class)
public class TestResultPushServiceTest {

	@Mock
	private SmsServiceProvider smsService;

	@Mock
	private EmailService emailService;

	@InjectMocks
	TestResultPushService service;

	@Test
	public void sendEmailForNegativeResult() {

		TestResult tr = new TestResult();
		tr.setContact("mail@test.com");
		tr.setStatus(Result.NEGATIVE);
		boolean sentFlag = service.executePush(tr);
		assertAll("email push is done and flag indicates it", () -> assertTrue(sentFlag),
				() -> verify(emailService, times(1)).sendMail(any()),
				() -> verify(smsService, never()).sendNegativeResultSms(any()));
	}

	@ParameterizedTest
	@EnumSource(Result.class)
	public void doNotSendEmailSentForNonNegativeResult(Result nonNegative) {

		if (nonNegative.equals(Result.NEGATIVE))
			return;

		TestResult tr = new TestResult();
		tr.setContact("mail@test.com");
		tr.setStatus(nonNegative);
		boolean sentFlag = service.executePush(tr);
		assertAll("no push is done and flag indicates it", () -> assertFalse(sentFlag),
				() -> verify(emailService, never()).sendMail(any()),
				() -> verify(smsService, never()).sendNegativeResultSms(any()));
	}

	@ParameterizedTest
	@ValueSource(strings = { "004915555", "015555", "+491555" })
	public void sendSmsForNegativeResultAndValidNumbers(String validNumber) {

		TestResult tr = new TestResult();
		tr.setContact(validNumber);
		tr.setStatus(Result.NEGATIVE);
		boolean sentFlag = service.executePush(tr);
		assertAll("sms push is done and flag indicates it", () -> assertTrue(sentFlag),
				() -> verify(emailService, never()).sendMail(any()),
				() -> verify(smsService, times(1)).sendNegativeResultSms(any()));
	}

	@ParameterizedTest
	@EnumSource(Result.class)
	public void doNotSendSmsForNonNegativeResult(Result nonNegative) {

		if (nonNegative.equals(Result.NEGATIVE))
			return;

		TestResult tr = new TestResult();
		tr.setContact("017555");
		tr.setStatus(nonNegative);
		boolean sentFlag = service.executePush(tr);
		assertAll("no push is done and flag indicates it", () -> assertFalse(sentFlag),
				() -> verify(emailService, never()).sendMail(any()),
				() -> verify(smsService, never()).sendNegativeResultSms(any()));
	}

	@ParameterizedTest
	@ValueSource(strings = { "+4855523", "07555", "foobar@bla", "foo.de", "-12314", "0047123123" })
	public void doNotSendNotificationForInvalidContactPatterns(String invalidContact) {

		TestResult tr = new TestResult();
		tr.setContact(invalidContact);
		tr.setStatus(Result.NEGATIVE);
		boolean sentFlag = service.executePush(tr);
		assertAll("no push is done and flag indicates it", () -> assertFalse(sentFlag),
				() -> verify(emailService, never()).sendMail(any()),
				() -> verify(smsService, never()).sendNegativeResultSms(any()));
	}

}
