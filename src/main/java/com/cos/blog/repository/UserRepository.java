package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Comment;
import com.cos.blog.model.Place;
import com.cos.blog.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
	
}
