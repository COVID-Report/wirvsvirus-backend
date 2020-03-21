package de.wirvsvirus.testresult.backend.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import de.wirvsvirus.testresult.backend.exceptions.MailSendingException;
import de.wirvsvirus.testresult.backend.model.PushMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@ConditionalOnMissingBean(type = "EmailService")
@Slf4j
public class SendGridMailService implements EmailService {
	
	@Value("${sendgrid.apikey:NOT_SET}")
	private String sendGridApiKey;
	
	@Override
	public void sendMail(PushMessage message) throws  MailSendingException {
		if(sendGridApiKey.equals("NOT_SET")) {
			log.warn("no sendgrid apikey configured, try setting sendgrid.apikey");
			throw new  MailSendingException("mailservice not available");
		}
		
		Email from = new Email("do-not-reply@testergebnis.de");
		String subject = "Testergebnis";
		Email to = new Email(message.getContact());
		Content content = new Content("text/plain", message.getText());
		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid(sendGridApiKey);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			log.debug("statuscode {}, body {}, headers {}",response.getStatusCode(),response.getBody(),response.getHeaders());
		} catch (IOException ex) {
			throw new  MailSendingException(ex);
		}
	}

}
