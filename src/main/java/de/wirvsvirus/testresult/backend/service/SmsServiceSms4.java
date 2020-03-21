package de.wirvsvirus.testresult.backend.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import de.wirvsvirus.testresult.backend.exceptions.SmsSendingException;
import de.wirvsvirus.testresult.backend.model.PushMessage;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Using https://www.sms4.de/cgi-bin/sms_http.pl
 *
 */
@Service
@Profile("sms4")
@Slf4j
public class SmsServiceSms4 implements SmsServiceProvider{

	private static final OkHttpClient httpClient = new OkHttpClient();

	private static final String BASE_URL = "http://www.sms4.de/cgi-bin/sms_out.pl";
	private static final String USER = "user";
	private static final String USER_VALUE = "Riddy";
	private static final String PSW = "pwd";
	@Value("${sms4.password}")
	private String password;
	private static final String KDNR = "kdnr";
	private static final String KDNR_VALUE = "WE20161";
	private static final String TO = "handynr";
	private static final String FROM = "absender";

	private static final String TEXT = "text";

	public void sendNegativeResultSms(PushMessage message) throws SmsSendingException {

		message.setContact(cleanupNumber(message.getContact()));
		String url = buildUrl(message);
		Request request = new Request.Builder().url(url).build();
		Call call = httpClient.newCall(request);
		Response response;
		try {
			response = call.execute();
			if (!response.isSuccessful()) {
				throw new SmsSendingException("Unexpected code " + response);
			}
			// Get response headers
			ResponseBody responseBody = response.body();
			if (!responseBody.string().contains("SMS erfolgreich versendet")) {
				throw new SmsSendingException("Not successful sent sms " + responseBody.string());
			}
			log.debug(response.body().string());
		} catch (IOException e) {
			throw new SmsSendingException(e);
		}
	}

	private static String cleanupNumber(String number) {
		if (number.startsWith("0") || number.startsWith("+"))
			return cleanupNumber(number.substring(1));
		return number;
	}

	private String buildUrl(PushMessage message) {
		HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();

		urlBuilder.addQueryParameter(TO, message.getContact());
		urlBuilder.addQueryParameter(USER, USER_VALUE);
		urlBuilder.addQueryParameter(PSW, password);
		urlBuilder.addQueryParameter(KDNR, KDNR_VALUE);
		urlBuilder.addQueryParameter(FROM, message.getFrom());
		urlBuilder.addQueryParameter(TEXT, message.getText());
		return urlBuilder.build().toString();
	}

}
