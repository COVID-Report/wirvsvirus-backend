package de.wirvsvirus.testresult.backend.tools;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class TestSmsService {

	// @Test
	// test will be successful, already tested once, 4 more test sms possible, using
	// "test=1" in query allows to test without actual sms sending
	void smsSendTestSms4() {
		try {
			SmsServiceSms4.sendNegativeResultSms("put in your number");
		} catch (IOException e) {
			fail("Message send not successful");
		}
	}

	// @Test
	void smsSendTestVoyage() {
		try {
			// https://dashboard.nexmo.com/test-numbers for test numbers
			SmsServiceVonage.sendNegativeResultSms("put in your number");
		} catch (IOException e) {
			fail("Message send not successful");
		}
	}

}
