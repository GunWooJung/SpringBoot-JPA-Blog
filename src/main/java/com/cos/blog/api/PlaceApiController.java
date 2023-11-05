package com.cos.blog.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cos.blog.model.Place;
import com.cos.blog.service.PlaceService;


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
	//placeService.addPlace(s);
	//placeService.addPlace(starbucks);
	placeService.addPlace(gonggong_seoul);
		return "addplace"; //addplace.jsp 결과 페이지로 이동
	}
	
	// request로 현재 위치를 받아서 주변 장소를 리턴
	
	@GetMapping("/showplace")
	public List<Place> showPlace(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {
		return placeService.list(lat,lng);
	} 
	
	@GetMapping("/showplace/diaperman")
	public List<Place> showPlaceDiaperMan(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {
		return placeService.listDiaperMan(lat,lng);
	} 
	
	@GetMapping("/showplace/diaperwoman")
	public List<Place> showPlaceDiaperWoman(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {
		return placeService.listDiaperWoman(lat,lng);
	} 
	
	@GetMapping("/showplace/disabled")
	public List<Place> showPlaceDisabled(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {
		return placeService.listDisabled(lat,lng);
	} 
	
	@GetMapping("/deleteplace")
	public String deletePlace() {
		placeService.deletePlace();
		return "delete place";
	}	
}
