package com.cos.blog.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Comment;
import com.cos.blog.model.Place;
import com.cos.blog.model.Report;
import com.cos.blog.repository.OpinionRepository;
import com.cos.blog.repository.PlaceRepository;
import com.cos.blog.repository.ReportRepository;

@Service
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private PlaceRepository placeRepository;
	
	@Transactional
	public List<Report> reportShow(int placeId) {
		Optional<Place> place = placeRepository.findById(placeId);
		if (place.isPresent()) {
			List<Report> reports = reportRepository.findByPlace(place.get());
			return reports;
		} else {
			return null;
		}
	}

	@Transactional
	public ResponseEntity<String> reportEnroll(String placeId, String type, String content, String ip) {
		Optional<Place> place = placeRepository.findById(Integer.parseInt(placeId));
		if (place.isPresent()) {
			String contentKor = content;
			if (contentKor.equals("man_bell_yes"))
				contentKor = "남자 화장실 비상벨이 있음";
			else if (contentKor.equals("man_bell_no"))
				contentKor = "남자 화장실 비상벨이 없거나 파손됨";
			else if (contentKor.equals("woman_bell_yes"))
				contentKor = "여자 화장실 비상벨이 있음";
			else if (contentKor.equals("woman_bell_no"))
				contentKor = "여자 화장실 비상벨이 없거나 파손됨";
			else if (contentKor.equals("delete_closed"))
				contentKor = "폐업";
			else if (contentKor.equals("delete_duplicated"))
				contentKor = "중복된 장소";
			else if (contentKor.equals("delete_nowhere"))
				contentKor = "없는 장소";
			else if (contentKor.equals("delete_relocation"))
				contentKor = "다른 곳으로 이전";
			else if (contentKor.equals("man_diaper_yes"))
				contentKor = "남자 화장실 기저귀 교환대가 있음";
			else if (contentKor.equals("man_diaper_no"))
				contentKor = "남자 화장실 기저귀 교환대가 없거나 파손됨";
			else if (contentKor.equals("woman_diaper_yes"))
				contentKor = "여자 화장실 기저귀 교환대가 있음";
			else if (contentKor.equals("woman_diaper_no"))
				contentKor = "여자 화장실 기저귀 교환대가 없거나 파손됨";
			else if (contentKor.equals("man_disabled_yes"))
				contentKor = "남자 장애인 화장실이 있음";
			else if (contentKor.equals("man_disabled_no"))
				contentKor = "남자 장애인 화장실이 없음";
			else if (contentKor.equals("woman_disabled_yes"))
				contentKor = "여자 장애인 화장실이 있음";
			else if (contentKor.equals("woman_disabled_no"))
				contentKor = "여자 장애인 화장실이 없음";
			Report report = new Report();
			report.setType(type);
			report.setContent(contentKor);
			report.setPlace(place.get());
			report.setCount(1);
			report.setIp(ip);
			Boolean isSame = false;
			List<Report> calsame = reportRepository.findByPlace(place.get());
			for (Report r : calsame) {
				if(r.getType().equals(type) && r.getContent().equals(contentKor)) {
				if (r.getIp().equals(ip)){
					 isSame = true;
					 return ResponseEntity.status(400).body("ip");	
				}
				else if (r.getIp2().equals(ip)){
					 isSame = true;
					 return ResponseEntity.status(400).body("ip");	
				}	
				else if (r.getIp3().equals(ip)){
					 isSame = true;
					 return ResponseEntity.status(400).body("ip");	
				}
				else{
					isSame = true;
					r.setCount(r.getCount() + 1);
					if(r.getIp2()==null) r.setIp2(ip);
					else if(r.getIp3()==null) r.setIp3(ip);
					reportRepository.save(r);
					return ResponseEntity.ok("Request successful");
				}
			  }
			}
				reportRepository.save(report);
				return ResponseEntity.ok("Request successful");
		}
		return ResponseEntity.status(400).body("fail");
	}
	@Transactional
	public void reportDelete(int reportId) {
		reportRepository.deleteById(reportId);
	}
	
	@Transactional
	public ResponseEntity<String> reportClickHeart(int reportId, String ip) {
		Optional<Report> reports = reportRepository.findById(reportId);
		System.out.println(reportId);
		Report report = reports.get();
		if(ip.equals(report.getIp())){
				return ResponseEntity.status(400).body("ip");
		}
		else if(ip.equals(report.getIp2())){
			return ResponseEntity.status(400).body("ip");
		}
		else if(ip.equals(report.getIp3())){
			return ResponseEntity.status(400).body("ip");
		}
		System.out.println(ip);
		if(report.getIp2()==null) report.setIp2(ip);
		else if(report.getIp3()==null) report.setIp3(ip);
		report.setCount(report.getCount()+1);
		reportRepository.save(report);
		return ResponseEntity.ok("Request successful");
	}

	@Transactional
	public void DeleteAll() {
		reportRepository.deleteAll();
	}
}
