package com.cos.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cos.blog.model.Place;
import com.cos.blog.model.Report;
import com.cos.blog.repository.PlaceRepository;
import com.cos.blog.repository.ReportRepository;

@Service
public class ReportService {
	
	@Autowired
	private ReportRepository reportRepository;
	
	@Autowired
	private PlaceRepository placeRepository;
	
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
	public void reportEnroll(String placeId, String type, String content) {
		Optional<Place> place = placeRepository.findById(Integer.parseInt(placeId));
		if (place.isPresent()) {
			Report report = new Report();
			report.setPlace(place.get());
			report.setContent(content);
			report.setCount(0);
			reportRepository.save(report);
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
