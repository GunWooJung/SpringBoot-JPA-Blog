package com.cos.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Comment;
import com.cos.blog.model.Place;
import com.cos.blog.model.Report;
import com.cos.blog.model.User;
import com.cos.blog.repository.CommentRepository;
import com.cos.blog.repository.PlaceRepository;
import com.cos.blog.repository.ReportRepository;
import com.cos.blog.repository.UserRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PlaceRepository placeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
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

	public void commentEnroll(String userId, String placeId, String score, String content) {
		Optional<Place> place = placeRepository.findById(Integer.parseInt(placeId));
		if (place.isPresent()) {
			Optional<User> user = userRepository.findById(Integer.parseInt(userId));
			if (user.isPresent()) {
			Comment comment = new Comment();
			comment.setPlace(place.get());
			comment.setUser(user.get());
			comment.setContent(content);
			comment.setScore(Integer.parseInt(score));
			commentRepository.save(comment);
			List<Comment> calcomment = commentRepository.findByPlace(place.get());
			double result = 0;
			for (int i = 0; i < calcomment.size(); i++) {
			  result += calcomment.get(i).getScore();
			}
			result /= calcomment.size();
			place.get().setAverage_score(result);
			placeRepository.save(place.get());
			}
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
