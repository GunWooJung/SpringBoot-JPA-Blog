package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	  @Query(value = "SELECT * FROM USER WHERE USERNAME = ?1 AND PASSWORD = ?2",nativeQuery = true)
	  User login(String username, String password);
}
