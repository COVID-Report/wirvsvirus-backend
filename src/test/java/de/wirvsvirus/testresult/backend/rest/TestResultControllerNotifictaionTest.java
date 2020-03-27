package de.wirvsvirus.testresult.backend.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.wirvsvirus.testresult.backend.exceptions.FalseInformedException;
import de.wirvsvirus.testresult.backend.model.TestResult;
import de.wirvsvirus.testresult.backend.model.TestResult.Result;
import de.wirvsvirus.testresult.backend.service.TestResultPushService;
import de.wirvsvirus.testresult.backend.service.TestResultService;

@ExtendWith(MockitoExtension.class)
public class TestResultControllerNotifictaionTest {

	@Mock
	TestResultService testResultService;
	@Mock
	TestResultPushService pushService;
	
	@InjectMocks
	TestResultController controller;
	
	@Test
	public void pushNotifiactionTriggeredWhenIdUnknownSoFar() throws FalseInformedException {
		TestResult tr = new TestResult();
		tr.setContact("some@mail.com");
		tr.setNotified(false);
		tr.setStatus(Result.NEGATIVE);
		
		when(testResultService.getTestResult(eq("123"))).thenReturn(Optional.empty());
		when(pushService.executePush(any())).thenReturn(Boolean.TRUE);
		
		controller.addTestResult("123", tr);
		//check existing
		verify(testResultService,times(1)).getTestResult(eq("123"));
		//do push
		verify(pushService, times(1)).executePush(any());
		//save testresult
		verify(testResultService,times(1)).createTestProcess(any());
	}
	@Test
	public void pushNotifiactionTriggeredWhenStatusIsTheSameButNotYetNotified() throws FalseInformedException {
		
		TestResult existing = new TestResult();
		existing.setContact("some@mail.com");
		existing.setNotified(false);
		existing.setStatus(Result.NEGATIVE);
		
		TestResult tr = new TestResult();
		tr.setContact("some@mail.com");
		tr.setStatus(Result.NEGATIVE);
		
		when(testResultService.getTestResult(eq("123"))).thenReturn(Optional.of(existing));
		when(pushService.executePush(any())).thenReturn(Boolean.TRUE);
		
		controller.addTestResult("123", tr);
		//check existing
		verify(testResultService,times(1)).getTestResult(eq("123"));
		//do push
		verify(pushService, times(1)).executePush(any());
		//save testresult
		verify(testResultService,times(1)).createTestProcess(any());
	}
	
	
	@Test
	public void pushNotifiactionNotTriggeredWhenAlreadyNotified() throws FalseInformedException {
		
		TestResult existing = new TestResult();
		existing.setContact("some@mail.com");
		existing.setNotified(true);
		existing.setStatus(Result.NEGATIVE);
		
		TestResult tr = new TestResult();
		tr.setContact("some@mail.com");
		tr.setNotified(false);
		tr.setStatus(Result.NEGATIVE);
		
		when(testResultService.getTestResult(eq("123"))).thenReturn(Optional.of(existing));
		
		controller.addTestResult("123", tr);
		//check existing
		verify(testResultService,times(1)).getTestResult(eq("123"));
		//do push
		verify(pushService, never()).executePush(any());
		//save testresult
		verify(testResultService,never()).createTestProcess(any());
	}
	
	@Test
	public void notifyWhenSwitchingFromPendingToNegative() throws FalseInformedException {
		
		TestResult existing = new TestResult();
		existing.setContact("some@mail.com");
		existing.setNotified(false);
		existing.setStatus(Result.PENDING);
		
		TestResult tr = new TestResult();
		tr.setContact("some@mail.com");
		tr.setNotified(false);
		tr.setStatus(Result.NEGATIVE);
		
		when(testResultService.getTestResult(eq("123"))).thenReturn(Optional.of(existing));
		
		controller.addTestResult("123", tr);
		//check existing
		verify(testResultService,times(1)).getTestResult(eq("123"));
		//do push
		verify(pushService, times(1)).executePush(any());
		//save testresult
		verify(testResultService,times(1)).createTestProcess(any());
	}
	
}
