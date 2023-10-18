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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		
        String csvFile = "C:\\workspacespring\\project\\src\\main\\resources\\gonggongPlace.csv";
        //공공데이터 csv파일
        Charset.forName("UTF-8");
        List<Place> places;
		try {
			places = new CsvToBeanBuilder<Place>(new FileReader(csvFile))
			        .withType(Place.class)
			        .build()
			        .parse();
		      places.forEach(place -> System.out.println(place.getName() + ", " + place.getLocation_x()+ ", "+place.getLocation_y()));
		      //콘솔에 리스트 출력
		      placeService.addPlace(places);
		      //레파지스토리에 place 리스트 넣기
		} catch (IllegalStateException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "addplace"; //addplace.jsp 결과 페이지로 이동
	}
	
	//모든 장소 리스트 json 객체를 반환
	@GetMapping("/showplace")
	public List<Place> showPlace() {
		return placeService.list();
	} 
}
