package de.wirvsvirus.testresult.backend.config;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalDateConfig {
	private DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
	@Bean
	public  DateTimeFormatter getDateTimeFormatter() {
		return formatter;
	}

}
