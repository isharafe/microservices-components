package com.ms.discoveryservice.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${app.eureka.username}")
	private String eurekaUsername;

	@Value("${app.eureka.password}")
	private String eurekaPassword;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.csrf().disable()
			.authorizeHttpRequests().anyRequest().authenticated()
			.and()
			.httpBasic();

		return httpSecurity.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		PasswordEncoder pswEncoder = passwordEncoder();
		List<UserDetails> users = new ArrayList<>();
		/**
		 * Need to enter authorities. o/w you will get an exception on startup complaining
		 * authorities are null
		 */
		users.add(
				User.builder().passwordEncoder(pswEncoder::encode)
					.username(eurekaUsername).password(eurekaPassword).authorities("USER")
					.build());
		return new InMemoryUserDetailsManager(users);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
