package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly=true)
	public User login(User user) {
		return	userRepository.login(user.getUsername(),user.getPassword());
	}
	
	@Transactional
	public void join(User user) {

			user.setPassword(user.getPassword());
			user.setRole(RoleType.USER);
			userRepository.save(user);
	}
	
	
}
