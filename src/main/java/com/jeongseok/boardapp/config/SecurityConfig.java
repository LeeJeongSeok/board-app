package com.jeongseok.boardapp.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.jeongseok.boardapp.config.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService;

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers(HttpMethod.POST, "/post").authenticated()
				.requestMatchers(HttpMethod.PATCH, "/post/**").authenticated()
				.requestMatchers(HttpMethod.DELETE, "/post/**").authenticated()
				.requestMatchers("/", "/join", "/post/**").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				.loginPage("/login") // url path 매핑
				.permitAll()
			)
			.logout((logout) -> logout.permitAll()
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
			);
//			.formLogin(form -> form
//				.loginPage("/user/login")
//				.permitAll());
//			.oauth2Login(withDefaults());
		

		return http.build();
	}



	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
