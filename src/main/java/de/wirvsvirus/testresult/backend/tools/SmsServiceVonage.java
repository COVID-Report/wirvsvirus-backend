package de.wirvsvirus.testresult.backend.tools;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;

import lombok.experimental.UtilityClass;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Using https://www.sms4.de/cgi-bin/sms_http.pl
 *
 */
@UtilityClass
public class SmsServiceVonage {

	private static final String APPLICATION_TYPE = "application/x-www-form-urlencoded";

	private final OkHttpClient httpClient = new OkHttpClient();

	private static final String BASE_URL = "https://rest.nexmo.com/sms/json";

	private static final String KEY = "api_key";
	@Value("${vonage.api.key:cb4bc87e}")
	private static String KEY_VALUE;
	private static final String SECRET = "api_secret";
	@Value("${vonage.api.secret}")
	private static String SECRET_VALUE;
	private static final String TO = "to";
	private static final String FROM = "from";
	private static final String FROM_VALUE = "Krankenhaus";

	private static final String TEXT = "text";
	private static final String NEGATIV_TEXT = "Wir freuen uns Ihnen mitteilen zu können, dass ihr COVID-19 Testergebnis negativ ist. Der Virus konnte bei Ihnen nicht festegestellt werden.";

	public static void sendNegativeResultSms(String number) throws IOException {
		Request request = new Request.Builder().url(buildUrl(number))
				.post(RequestBody.create(MediaType.parse(APPLICATION_TYPE), "")).build();

		Response response = httpClient.newCall(request).execute();

		if (!response.isSuccessful())
			throw new IOException("Unexpected code " + response);

		// Get response headers
		ResponseBody responseBody = response.body();

		if (!responseBody.string().contains("\"status\": \"0\""))
			throw new IOException("Not successful sent sms " + responseBody.string());
		// Get response body
		System.out.println(response.body().string());
	}

	private static String buildUrl(String number) {
		HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();

		urlBuilder.addQueryParameter(TO, number);

		urlBuilder.addQueryParameter(KEY, KEY_VALUE);
		urlBuilder.addQueryParameter(SECRET, SECRET_VALUE);
		urlBuilder.addQueryParameter(FROM, FROM_VALUE);
		urlBuilder.addQueryParameter(TEXT, NEGATIV_TEXT);

		return urlBuilder.build().toString();
	}
}
