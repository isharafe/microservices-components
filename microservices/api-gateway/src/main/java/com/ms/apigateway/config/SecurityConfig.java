package com.ms.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
/*
 * spring cloud api-gateway is based on WebFlux.
 * so for security, need to use EnableWebFluxSecurity
 * */
@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
		serverHttpSecurity
			.csrf().disable()
			.authorizeExchange(exchange -> exchange
					.pathMatchers(
							/* exclude eureka js and css content as these won't need to be authenticated*/
							"/eureka/**",
							/* exclude keycloak forward urls*/
							"/realms/**"
							).permitAll()
					/* authenticate any other urls */
					.anyExchange().authenticated()
					)
			.oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);

		return serverHttpSecurity.build();
	}
}
