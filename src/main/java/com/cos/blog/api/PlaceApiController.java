package com.cos.blog.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.RequestBodyDto;
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
	placeService.addPlace(s);
	placeService.addPlace(starbucks);
	placeService.addPlace(gonggong_seoul);
		return "addplace"; //addplace.jsp 결과 페이지로 이동
	}
	
	@GetMapping("/updateplace")
	public String updatePlace() {
	String gonggong_seoul = "gonggong_seoul";
	placeService.updatePlace(gonggong_seoul);
		return "updateplace"; //addplace.jsp 결과 페이지로 이동
	}
	// request로 현재 위치를 받아서 주변 장소를 리턴
	
	@PostMapping("/showplace")
	public List<Place> showPlace(@RequestBody RequestBodyDto request) {
	    double lat = Double.parseDouble(request.getLat());
	    double lng = Double.parseDouble(request.getLng());
	    String changing_table_man = request.getChanging_table_man();
		String changing_table_woman = request.getChanging_table_woman();
		String disabled_person  = request.getDisabled_person();
		String emergency_bell_disabled = request.getEmergency_bell_disabled();
		String emergency_bell_man  = request.getEmergency_bell_man();
		String emergency_bell_woman  = request.getEmergency_bell_woman();
	    return placeService.list(lat, lng,changing_table_man,changing_table_woman,disabled_person
	    		,emergency_bell_disabled,emergency_bell_man,emergency_bell_woman);
	}

	
	/*
	 * @GetMapping("/showplace/diaperman") public List<Place>
	 * showPlaceDiaperMan(@RequestParam("lat") double lat, @RequestParam("lng")
	 * double lng) { return placeService.listDiaperMan(lat,lng); }
	 * 
	 * @GetMapping("/showplace/diaperwoman") public List<Place>
	 * showPlaceDiaperWoman(@RequestParam("lat") double lat, @RequestParam("lng")
	 * double lng) { return placeService.listDiaperWoman(lat,lng); }
	 * 
	 * @GetMapping("/showplace/disabled") public List<Place>
	 * showPlaceDisabled(@RequestParam("lat") double lat, @RequestParam("lng")
	 * double lng) { return placeService.listDisabled(lat,lng); }
	 */
	
	@GetMapping("/deleteplace")
	public String deletePlace() {
		placeService.deletePlace();
		return "delete place";
	}	
}
