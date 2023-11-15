package com.cos.blog.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.RequestBodyCommentDto;
import com.cos.blog.model.Comment;
import com.cos.blog.model.StarRating;
import com.cos.blog.service.CommentService;
import com.cos.blog.service.StarRatingService;

@RestController
public class StarRatingApiController {
	/*
	 * @Autowired private StarRatingService starRatingtService;
	 * 
	 * 
	 * @GetMapping("/starrating/show") public List<StarRating>
	 * starRatingShow(@RequestParam("id") int placeId) { List<StarRating> stars =
	 * starRatingtService.starRatingShow(placeId); return stars; }
	 */

	/*
	 * @PostMapping("/starrating/enroll") public void starRatingEnroll(@RequestBody
	 * RequestBodystarRatingDto request) {
	 * 
	 * }
	 */

	// 아직 미개발
	@GetMapping("/starrating/update")
	public void starRatingUpdate(@RequestParam("id") int starRatingId) {

	}

	@GetMapping("/starrating/delete")
	public void starRatingDelete(@RequestParam("id") int starRatingId) {

	}
}
