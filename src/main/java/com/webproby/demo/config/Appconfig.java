package com.webproby.demo.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import lombok.Builder;



@Configuration
public class Appconfig {
//	for INMemory Authentication
//	@Bean
//	public UserDetailsService userDetailsSerivce()
//	{
//	        UserDetails user=org.springframework.security.core.userdetails.User.builder().username("55").password(passwordEncoder().encode("55")).roles("Admin").build();
////	    UserDetails userDetails = org.springframework.security.core.userdetails.User.builder().
////	                username("DURGESH")
////	                .password(passwordEncoder().encode("DURGESH")).roles("ADMIN").
////	                build();
//	        return new InMemoryUserDetailsManager(user);
//
//		
//		
//	}

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationManager authmanager(AuthenticationConfiguration asp) throws Exception
	{
		return asp.getAuthenticationManager();
	}
}


