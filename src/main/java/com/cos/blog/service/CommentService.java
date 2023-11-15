package com.cos.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Comment;
import com.cos.blog.model.Place;
import com.cos.blog.model.Report;
import com.cos.blog.repository.CommentRepository;
import com.cos.blog.repository.PlaceRepository;
import com.cos.blog.repository.ReportRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PlaceRepository placeRepository;
	
	@Transactional
	public List<Comment> commentShow(int placeId) {
		Optional<Place> place = placeRepository.findById(placeId);
		if (place.isPresent()) {
			List<Comment> comments = commentRepository.findByPlace(place.get());
			return comments;
		} 
		else{
			return null;
		}
	}

	public void commentEnroll(String username,String password, String placeId, String content) {
		Optional<Place> places = placeRepository.findById(Integer.parseInt(placeId));
		if (places.isPresent()) {
			Comment comment = new Comment();
			comment.setPlace(places.get());
			comment.setUsername(username);
			comment.setPassword(password);
			comment.setContent(content);
			commentRepository.save(comment);
			Place place = places.get();
			place.setComment_count(place.getComment_count()+1);
			placeRepository.save(place);
			}
	}


	private void save(Place place) {
		// TODO Auto-generated method stub
		
	}

	@Transactional
	public void commentUpdate(int commentId) {
		// TODO Auto-generated method stub
		
	}
	
	@Transactional
	public void commentDelete(int commentId) {
		commentRepository.deleteById(commentId);
	}

}
