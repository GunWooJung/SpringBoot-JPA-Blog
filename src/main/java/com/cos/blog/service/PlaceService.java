package com.cos.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.Place;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.PlaceRepository;

@Service
public class PlaceService {
	@Autowired
	private PlaceRepository placeRepository;
	
	//모든 place를 jpa에 등록하기
	@Transactional 
	public void addPlace(List<Place> places) {
	     placeRepository.saveAll(places);
	}
	//모든 place를 불러오기
	@Transactional(readOnly=true)
	public List<Place> list(){
		return placeRepository.findAll();
	}

}
