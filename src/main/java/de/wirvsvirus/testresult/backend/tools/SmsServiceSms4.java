package de.wirvsvirus.testresult.backend.tools;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;

import lombok.experimental.UtilityClass;
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
public class SmsServiceSms4 {

	private final static OkHttpClient httpClient = new OkHttpClient();

	private static final String BASE_URL = "http://www.sms4.de/cgi-bin/sms_out.pl";
	private static final String USER = "user";
	private static final String USER_VALUE = "Riddy";
	private static final String PSW = "pwd";
	@Value("${sms4.password}")
	private static String PSW_VALUE;
	private static final String KDNR = "kdnr";
	private static final String KDNR_VALUE = "WE20161";
	private static final String TO = "handynr";
	private static final String FROM = "absender";
	private static final String FROM_VALUE = "Krankenhaus";

	private static final String TEXT = "text";
	private static final String NEGATIV_TEXT = "Wir freuen uns Ihnen mitteilen zu können, dass ihr COVID-19 Testergebnis negativ ist. Der Virus konnte bei Ihnen nicht festegestellt werden.";

	public static void sendNegativeResultSms(String number) throws IOException {

		String phonenumber = cleanupNumber(number);
		String url = buildUrl(phonenumber);

		Request request = new Request.Builder().url(url).build();

		Call call = httpClient.newCall(request);
		Response response = call.execute();

		if (!response.isSuccessful())
			throw new IOException("Unexpected code " + response);

		// Get response headers
		ResponseBody responseBody = response.body();

		if (!responseBody.string().contains("SMS erfolgreich versendet"))
			throw new IOException("Not successful sent sms " + responseBody.string());
		// Get response body
		System.out.println(response.body().string());
	}

	private static String cleanupNumber(String number) {
		if (number.startsWith("0") || number.startsWith("+"))
			return cleanupNumber(number.substring(1));
		return number;
	}

	private static String buildUrl(String number) {
		HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();

		urlBuilder.addQueryParameter(TO, number);

		urlBuilder.addQueryParameter(USER, USER_VALUE);
		urlBuilder.addQueryParameter(PSW, PSW_VALUE);
		urlBuilder.addQueryParameter(KDNR, KDNR_VALUE);
		urlBuilder.addQueryParameter(FROM, FROM_VALUE);
		urlBuilder.addQueryParameter(TEXT, NEGATIV_TEXT);

		return urlBuilder.build().toString();
	}

}
