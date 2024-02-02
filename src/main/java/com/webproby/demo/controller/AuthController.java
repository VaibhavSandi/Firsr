package com.webproby.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webproby.demo.config.JWTHelper;
import com.webproby.demo.config.JWTRequest;
import com.webproby.demo.config.JWTResponse;
import com.webproby.demo.entity.User;
import com.webproby.demo.service.HomeService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	
	@Autowired
	private UserDetailsService usd;
	
	@Autowired
	private HomeService hs;
	@Autowired
	
	private AuthenticationManager manager;
	
	 @Autowired
	    private JWTHelper helper;

	
	
	Logger logger = LoggerFactory.getLogger(ACOntroller.class);
	
	
	
	@PostMapping("/tokens")
	private ResponseEntity<JWTResponse> login(@RequestBody JWTRequest request)
	{
		
	this.doAuthenticate(request.getName(), request.getPassword());
	
	UserDetails us=usd.loadUserByUsername(request.getName());
		
		String token=this.helper.generateToken(us);
		
		
		JWTResponse response=JWTResponse.builder().jwtToken(token).username(us.getUsername()).build();
		
		
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	
	
	private void doAuthenticate(String name,String password)
	{
		UsernamePasswordAuthenticationToken authentication =new UsernamePasswordAuthenticationToken(name, password);
		try
		{
		manager.authenticate(authentication);
		}
		catch(BadCredentialsException e)
		{
			throw new BadCredentialsException(" Invalid Username or Password  !!");
			
		}
	}
	
	@ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
	@PostMapping("/save")
	public void createUser(@RequestBody User user)
	{
		
		hs.saveUser(user);
		
	}
	
	



}
