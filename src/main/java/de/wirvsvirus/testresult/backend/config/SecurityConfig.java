package de.wirvsvirus.testresult.backend.config;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import io.micrometer.core.instrument.util.StringUtils;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Value("${post.user:}")
	private String postUser;

	@Value("${post.password:}")
	private String postPassword;

	@Value("${admin.user:}")
	private String adminUser;

	@Value("${admin.password:}")
	private String adminPassword;
	
	
	private void initUsers() {
		if (StringUtils.isEmpty(postUser)) {
			postUser = UUID.randomUUID().toString();
			System.out.println("postUser not set, using: " + postUser);
		}
		if (StringUtils.isEmpty(postPassword)) {
			postPassword = UUID.randomUUID().toString();
			System.out.println("postPassword not set, using: " + postPassword);
		}
		if (StringUtils.isEmpty(adminUser)) {
			adminUser = UUID.randomUUID().toString();
			System.out.println("adminUser not set, using: " + adminUser);
		}
		if (StringUtils.isEmpty(adminPassword)) {
			adminPassword = UUID.randomUUID().toString();
			System.out.println("adminPassword not set, using: " + adminPassword);
		}
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		initUsers();
		auth.inMemoryAuthentication().withUser(postUser).password("{noop}" + postPassword).roles("POSTUSER").and()
				.withUser(adminUser).password("{noop}" + adminPassword).roles("POSTUSER", "ADMIN");

	}

	// Secure the endpoins with HTTP Basic authentication
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				// HTTP Basic authentication
				.httpBasic()
				.and().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/tests/**").permitAll()
				.antMatchers(HttpMethod.POST, "/tests/**").hasRole("POSTUSER")
				.antMatchers(HttpMethod.DELETE, "/tests/**").hasRole("ADMIN")
				.and()
				.cors().disable()
				.csrf().disable()
				.formLogin().disable();
	}
}
