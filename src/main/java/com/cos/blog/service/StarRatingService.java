package com.cos.blog.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Comment;
import com.cos.blog.model.Place;
import com.cos.blog.model.StarRating;
import com.cos.blog.repository.CommentRepository;
import com.cos.blog.repository.PlaceRepository;
import com.cos.blog.repository.StarRatingRepository;

@Service
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
		} else {
			return null;
		}
	}

	@Transactional
	public void starRatingEnroll(String placeId, String rating) {
		if (rating != null) {
			Double score = Double.parseDouble(rating);
			Optional<Place> place = placeRepository.findById(Integer.parseInt(placeId));
			if (place.isPresent()) {
				StarRating starRating = new StarRating();
				starRating.setPlace(place.get());
				starRating.setScore(score);
				starRatingServiceRepository.save(starRating);
				List<StarRating> calstar = starRatingServiceRepository.findByPlace(place.get());
				double result = 0;
				int count = calstar.size();
				for (int i = 0; i < calstar.size(); i++) {
					result += calstar.get(i).getScore();
				}
				result /= calstar.size();
				place.get().setStar_count(count);
				DecimalFormat df = new DecimalFormat("#.#");
				result = Double.parseDouble(df.format(result));
				place.get().setStar_average(result);
				placeRepository.save(place.get());

			}
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
