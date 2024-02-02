package com.webproby.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webproby.demo.entity.User;

public interface UserRepo extends JpaRepository<User,String> {

	
	public User findByName(String name);
}
