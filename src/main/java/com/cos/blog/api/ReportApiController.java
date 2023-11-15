package com.cos.blog.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.cos.blog.service.ReportService;

@RestController
public class ReportApiController {

	@Autowired
	private ReportService reportService;
	
	// API 요청 방식 : GET,  주소 : /report/show?id=${id} , 설명 : place id값으로 
	// 해당 장소 신고 목록 조회
	@GetMapping("/report/show")
	public List<Report> reportShow(@RequestParam("id") int placeId) {
		List<Report> reports = reportService.reportShow(placeId);
		return reports;
	}
	
	// API 요청 방식 : POST,  주소 : /report/enroll , 설명 : requestbody에
	// String으로 userId, placeId , content 요청으로 신고 등록하기

	@PostMapping("/report/enroll")
	public void reportEnroll(@RequestBody RequestBodyReportDto request) {
		reportService.reportEnroll(request.getPlaceId(),request.getContent());
	}
	
	//미개발
	@GetMapping("/report/update")
	public void reportUpdate(@RequestParam("id") int reportId) {
		
	} // post로 수정
	
	// API 요청 방식 : GET,  주소 : /report/delete?id=${id} , 설명 : report id값으로 
	// 해당 신고 내용 삭제
	@GetMapping("/report/delete")
	public void reportDelete(@RequestParam("id") int reportId) {
		reportService.reportDelete(reportId);
	}

	// API 요청 방식 : GET,  주소 : /report/clickheart?id=${id} , 설명 : report id값으로 
	// 하트를 누르면 신고 count값이 1 증가
	@GetMapping("/report/clickheart")
	public void reportClickHeart(@RequestParam("id") int reportId) {
		reportService.reportClickHeart(reportId);
	}
}
