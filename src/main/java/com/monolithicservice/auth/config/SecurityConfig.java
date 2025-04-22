package com.monolithicservice.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests().requestMatchers("/api/auth/**", // Allow backend endpoints
				"/register.html", // Allow register page
				"/login.html", // Allow login page
				"/js/**", // Allow JS files
				"/css/**", // (Optional) Allow CSS if used
				"/images/**").permitAll().anyRequest().authenticated();
		return http.build();
	}
}
