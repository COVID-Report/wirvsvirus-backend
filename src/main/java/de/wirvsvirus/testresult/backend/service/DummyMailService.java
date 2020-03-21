package de.wirvsvirus.testresult.backend.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import de.wirvsvirus.testresult.backend.model.PushMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@ConditionalOnMissingBean(type = "EmailService")
@Slf4j
public class DummyMailService implements EmailService{

	@Override
	public void sendMail(PushMessage message) {
		log.warn("email requests, but no real implementation here! message was: {}", message);
	}

}
