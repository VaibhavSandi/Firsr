package com.webproby.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webproby.demo.entity.User;
import com.webproby.demo.service.HomeService;

@RestController
@RequestMapping("/home")
public class ACOntroller {
	
	
	@Autowired
	HomeService hs;

	Logger logger = LoggerFactory.getLogger(ACOntroller.class);
	
	@GetMapping("/hi")
	public List<User>  getHi()
	{
		logger.warn("this is working message");
		return hs.getAllUser();
	}
	
	
	
	

}
