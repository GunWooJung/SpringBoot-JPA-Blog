package com.cos.blog.api;

import java.util.List;
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
	
	//지도 중심기준 주변 화장실 불러오기
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
	
	//키워드만 추출
	@GetMapping("/searchbykeyword")
	public List<Place> searchByKeyword(@RequestParam("keyword") String keyword) {
		return placeService.listSearchByKeyword(keyword);
	}
	
	//전체 목록 삭제
	@GetMapping("/deleteplace")
	public String deletePlace() {
		placeService.deletePlace();
		return "delete place";
	}	
}
