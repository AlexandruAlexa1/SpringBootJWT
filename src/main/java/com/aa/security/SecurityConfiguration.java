package com.aa.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aa.constant.SecurityConstant;
import com.aa.filter.JWTAccessDeniedHandler;
import com.aa.filter.JWTAuthorizationFilter;
import com.aa.filter.JWTForbiddenEntryPoint;
import com.aa.service.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired private UserDetailsService userDetailsService;
	@Autowired private JWTAuthorizationFilter jwtAuthorizationFilter;
	@Autowired private JWTForbiddenEntryPoint jwtForbiddenEntryPoint;
	@Autowired private JWTAccessDeniedHandler jwtAccessDeniedHandler;
	@Autowired private PasswordEncoder passwordEncoder;
	
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		
		return authenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(List.of(authenticationProvider()));
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf((csrf) -> csrf.disable())
			.cors((cors) -> cors.disable())
			.sessionManagement((sessionManagement) ->
				sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests((authorizeHttpRequests) -> 
				authorizeHttpRequests
				.requestMatchers(SecurityConstant.PUBLIC_URLS).permitAll()
				.requestMatchers(HttpMethod.POST, "/api/v1/users").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/api/v1/users").hasAnyAuthority("ADMIN", "EDITOR")
				.anyRequest().authenticated())
			.exceptionHandling((exceptionHandling) -> 
				exceptionHandling
				.authenticationEntryPoint(jwtForbiddenEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler))
			.authenticationProvider(authenticationProvider())
			.authenticationManager(authenticationManager())
			.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
}
