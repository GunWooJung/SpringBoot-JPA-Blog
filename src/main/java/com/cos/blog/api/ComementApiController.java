package com.cos.blog.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.RequestBodyCommentDto;
import com.cos.blog.dto.RequestBodyReportDto;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Comment;
import com.cos.blog.model.Report;
import com.cos.blog.service.CommentService;
import com.cos.blog.service.PlaceService;

@RestController
public class ComementApiController {

	@Autowired
	private CommentService commentService;
	
	// API 요청 방식 : GET,  주소 : /comment/show?id=${id} , 설명 : place id 값으로 해당 장소 댓글 불러오기
	@GetMapping("/comment/show")
	public List<Comment> commentShow(@RequestParam("id") int placeId) {
		List<Comment> comments = commentService.commentShow(placeId);
		return comments;
	}
	
	// API 요청 방식 : POST,  주소 : /comment/enroll , 설명 : requestbody에 
	// String으로 userId , placeId, score, content 받아오기
	// 댓글 등록시 place의 평균 score값이 자동으로 갱신됨
	@PostMapping("/comment/enroll")
	public void commentEnroll(@RequestBody RequestBodyCommentDto request) {
		commentService.commentEnroll(request.getUsername(),request.getPassword(),request.getPlaceId(),request.getContent());
	}
	
	//아직 미개발
	@GetMapping("/comment/update")
	public void commentUpdate(@RequestParam("id") int commentId) {
		
	}
	// API 요청 방식 : GET,  주소 : /comment/delete?id=${id} , 설명 :comment id 값으로 해당 댓글 삭제
	@GetMapping("/comment/delete")
	public void commentDelete(@RequestParam("id") int commentId) {
		commentService.commentDelete(commentId);
	}
}
