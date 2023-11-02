package com.cos.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.Board;
import com.cos.blog.model.Place;
import com.cos.blog.model.User;

public interface PlaceRepository extends JpaRepository<Place, Integer> {

}
