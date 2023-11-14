package com.cos.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Place;
import com.cos.blog.model.Report;
import com.cos.blog.model.User;
import com.cos.blog.repository.PlaceRepository;
import com.cos.blog.repository.ReportRepository;
import com.cos.blog.repository.UserRepository;

@Service
public class ReportService {
	
	@Autowired
	private ReportRepository reportRepository;
	
	@Autowired
	private PlaceRepository placeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<Report> reportShow(int placeId) {
		Optional<Place> place = placeRepository.findById(placeId);
		if (place.isPresent()) {
			List<Report> reports = reportRepository.findByPlace(place.get());
			return reports;
		} 
		else{
			return null;
		}
	}
	
	@Transactional
	public void reportEnroll(String userId,String placeId, String content) {
		Optional<Place> place = placeRepository.findById(Integer.parseInt(placeId));
		if (place.isPresent()) {
			Optional<User> user = userRepository.findById(Integer.parseInt(userId));
			if (user.isPresent()) {
			Report report = new Report();
			report.setUser(user.get());
			report.setPlace(place.get());
			report.setContent(content);
			reportRepository.save(report);
			}
		} 
	}



	public void reportUpdate(int reportId) {
		
	}

	public void reportDelete(int reportId) {
		reportRepository.deleteById(reportId);
	}

	public void reportClickHeart(int reportId) {
		// TODO Auto-generated method stub
		
	}


}
