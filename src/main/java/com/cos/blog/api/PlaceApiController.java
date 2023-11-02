package com.cos.blog.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Place;
import com.cos.blog.model.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.PlaceService;
import com.cos.blog.test.Member;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@RestController
public class PlaceApiController {

	@Autowired
	private PlaceService placeService;
	
	
	// csv파일을 DB에 등록하는 처리
	@GetMapping("/addplace")
	public String addPlace() {
	String s = "gonggong_hang";
	String starbucks = "starbucks";
	String gonggong_seoul = "gonggong_seoul";
	placeService.addPlace(s);
	placeService.addPlace(starbucks);
	placeService.addPlace(gonggong_seoul);
		return "addplace"; //addplace.jsp 결과 페이지로 이동
	}
	
	// request로 현재 위치를 받아서 주변 장소를 리턴
	
	@GetMapping("/showplace")
	public List<Place> showPlace(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {
		return placeService.list(lat,lng);
	} 
	
	@GetMapping("/deleteplace")
	public String deletePlace() {
		placeService.deletePlace();
		return "delete place";
	}	
}
