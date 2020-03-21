package de.wirvsvirus.testresult.backend.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.wirvsvirus.testresult.backend.exceptions.SmsSendingException;
import de.wirvsvirus.testresult.backend.model.PushMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Using https://www.sms4.de/cgi-bin/sms_http.pl
 *
 */
@Service
@ConditionalOnMissingBean(type = "SmsServiceProvider")
@Slf4j
public class SmsServiceVonage implements SmsServiceProvider{

	private static final String APPLICATION_TYPE = "application/x-www-form-urlencoded";

	private static final OkHttpClient httpClient = new OkHttpClient();
	
	private static final ObjectMapper mapper = new ObjectMapper();

	private static final String BASE_URL = "https://rest.nexmo.com/sms/json";

	private static final String KEY = "api_key";
	@Value("${vonage.api.key:cb4bc87e}")
	private String apiKey;
	
	private static final String SECRET = "api_secret";
	@Value("${vonage.api.secret:NOT_SET}")
	private String apiSecret;
	
	private static final String TO = "to";
	private static final String FROM = "from";

	private static final String TEXT = "text";

	public void sendNegativeResultSms(PushMessage message) throws SmsSendingException  {
		String url = buildUrl(message);
		log.info("url={}", url);
		
		Request request = new Request.Builder().url(url)
				.post(RequestBody.create(MediaType.parse(APPLICATION_TYPE), "")).build();
		try {
			okhttp3.Response response = httpClient.newCall(request).execute();
			if (!response.isSuccessful()) {
				throw new SmsSendingException("Unexpected code " + response);
			}
			
			ResponseBody responseBody = response.body();
			log.info("Sending successful, response body: {}", response.body().string());
			Response r = mapper.readValue(response.body().string(), Response.class);
			if (!r.messages.get(0).status.equals("0")) {
				throw new SmsSendingException("Not successful sent sms :" + responseBody.string());
			}
		} catch (IOException e) {
			throw new SmsSendingException(e);
		}
	}

	private String buildUrl(PushMessage message) {
		
		log.info(apiKey);
		HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();

		urlBuilder.addQueryParameter(TO, message.getContact());
		urlBuilder.addQueryParameter(KEY, apiKey);
		urlBuilder.addQueryParameter(SECRET, apiSecret);
		urlBuilder.addQueryParameter(FROM, message.getFrom());
		urlBuilder.addQueryParameter(TEXT, message.getText());

		return urlBuilder.build().toString();
	}
	@Data
	class Response {
		@JsonProperty("message-count")
		String messageCount;
		List<Message> messages;
	}
	@Data
	class Message{
		String status;
	}
	
}
