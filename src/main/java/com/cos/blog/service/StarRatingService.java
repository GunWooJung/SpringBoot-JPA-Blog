package com.cos.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Comment;
import com.cos.blog.model.Place;
import com.cos.blog.model.StarRating;
import com.cos.blog.repository.CommentRepository;
import com.cos.blog.repository.PlaceRepository;
import com.cos.blog.repository.StarRatingRepository;

public class StarRatingService {
	@Autowired
	private PlaceRepository placeRepository;
	
	@Autowired
	private StarRatingRepository starRatingServiceRepository;
	
	@Transactional
	public List<StarRating> starRatingtShow(int placeId) {
		Optional<Place> place = placeRepository.findById(placeId);
		if (place.isPresent()) {
			List<StarRating> starRatings = starRatingServiceRepository.findByPlace(place.get());
			return starRatings;
		} 
		else{
			return null;
		}
	}

	public void starRatingEnroll(String userId, String placeId, String score) {
		Optional<Place> place = placeRepository.findById(Integer.parseInt(placeId));
		if (place.isPresent()) {
			StarRating starRating = new StarRating();
			starRating.setPlace(place.get());
			starRating.setScore(Integer.parseInt(score));
			starRatingServiceRepository.save(starRating);
			List<StarRating> calstar = starRatingServiceRepository.findByPlace(place.get());
			double result = 0;
			int count = calstar.size();
			for (int i = 0; i < calstar.size(); i++) {
			  result += calstar.get(i).getScore();
			}
			result /= calstar.size();
			place.get().setStar_count(count);
			place.get().setStar_average(result);
			placeRepository.save(place.get());
			
		} 
	}

	@Transactional
	public void starRatingUpdate(int starRatingId) {
		// TODO Auto-generated method stub
		
	}
	
	@Transactional
	public void starRatingDelete(int starRatingId) {
		starRatingServiceRepository.deleteById(starRatingId);
	}
}

