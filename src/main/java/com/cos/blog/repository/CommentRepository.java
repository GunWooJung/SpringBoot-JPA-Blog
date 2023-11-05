package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Comment;
import com.cos.blog.model.Place;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
