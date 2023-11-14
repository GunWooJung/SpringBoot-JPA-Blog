package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.RequestBodyUserDto;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public User userLogin(String username, String password) {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			if (user.getPassword().equals(password)) {
				return user;
			} else {
				return null;
			}
		}
		else {
			User newUser = new User();
			newUser.setUsername(username);
			newUser.setPassword(password);
			userRepository.save(newUser);
			return newUser;
		}
	}
}
