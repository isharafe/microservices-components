package com.ms.discoveryservice.config;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig {
//
//	@Value("${app.eureka.username}")
//	private String eurekaUsername;
//
//	@Value("${app.eureka.password}")
//	private String eurekaPassword;
//
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//		httpSecurity
//			.csrf().disable()
//			.authorizeHttpRequests().anyRequest().authenticated()
//			.and()
//			.httpBasic();
//
//		return httpSecurity.build();
//	}
//
//	@Bean
//	public UserDetailsService userDetailsService() {
//		PasswordEncoder pswEncoder = passwordEncoder();
//		List<UserDetails> users = new ArrayList<>();
//		/**
//		 * Need to enter authorities. o/w you will get an exception on startup complaining
//		 * authorities are null
//		 */
//		users.add(
//				User.builder().passwordEncoder(pswEncoder::encode)
//					.username(eurekaUsername).password(eurekaPassword).authorities("USER")
//					.build());
//		return new InMemoryUserDetailsManager(users);
//	}
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

}
