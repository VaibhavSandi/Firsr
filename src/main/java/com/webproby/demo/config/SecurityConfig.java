package com.webproby.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.webproby.demo.service.CustomUserDeatils;

@Configuration
public class SecurityConfig {
	@Autowired
	private CustomUserDeatils userDetailsService;
	
	
	@Autowired
	private PasswordEncoder passwordencoder;
	@Autowired
	private JWTAuthenticationEntryPoint point;
	
	@Autowired
	
	private JWTAuthenticationFilter filter;
	
	@Bean
	public SecurityFilterChain secutiryfilterchain(HttpSecurity http) throws Exception
	{
		
		//configuartion

		http.csrf(csrf->csrf.disable())
		.cors(cors->cors.disable())
		.authorizeHttpRequests(auth->auth.requestMatchers("/auth/tokens").permitAll()
		
				
				.requestMatchers("/home/**")
				.permitAll()
				.requestMatchers("/auth/save")
				.permitAll()
				.anyRequest().authenticated())
		.exceptionHandling(ex->ex.authenticationEntryPoint(point))
		.sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		
		http.addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
		
	}

	@Bean
	public DaoAuthenticationProvider sss()
	{
		
		DaoAuthenticationProvider dsa=new DaoAuthenticationProvider();
		
		dsa.setUserDetailsService(userDetailsService);
		dsa.setPasswordEncoder(passwordencoder);
		return dsa;
		
	}
}
