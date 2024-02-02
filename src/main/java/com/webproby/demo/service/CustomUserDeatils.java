package com.webproby.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.webproby.demo.entity.User;
import com.webproby.demo.repo.UserRepo;
@Service
public class CustomUserDeatils implements UserDetailsService {

	@Autowired
	private UserRepo ur;
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		// load user from db
		
		User user=ur.findByName(name);
		
		if(user==null)
		{
			throw new RuntimeException("user not found");
		}
		
		
		
		
		return user;
	}

}
