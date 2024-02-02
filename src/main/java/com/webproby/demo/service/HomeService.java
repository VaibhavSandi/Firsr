package com.webproby.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webproby.demo.entity.User;
import com.webproby.demo.repo.UserRepo;

@Service
public class HomeService {
	@Autowired
	private UserRepo ur;
	@Autowired
	private PasswordEncoder pas;
	
	public List<User> getAllUser()
	{
		return ur.findAll();
	}
	
	
	public void saveUser(User u)
	{
		 u.setUserId(UUID.randomUUID().toString());
		pas.encode(u.getPassword());
		
		ur.save(u);
	}

	 


}
