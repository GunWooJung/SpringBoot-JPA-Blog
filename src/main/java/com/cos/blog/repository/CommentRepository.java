package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cos.blog.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
