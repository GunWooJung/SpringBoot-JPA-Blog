package com.cos.blog.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.Place;
import com.cos.blog.model.Similarity;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.PlaceRepository;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class PlaceService {
	@Autowired
	private PlaceRepository placeRepository;

	@Transactional // 위도 경도
	public void deletePlace() {
		placeRepository.deleteAll();
	}
	
	// 모든 place를 jpa에 등록하기
	@Transactional // 위도 경도
	public void addPlace(String fileName) {
		String csvFile = "C:\\workspacespring\\project\\src\\main\\resources\\" + fileName + ".csv";
		Charset.forName("UTF-8");

		try {

			List<Place> newPlaces = newPlaces = new CsvToBeanBuilder<Place>(new FileReader(csvFile))
					.withType(Place.class).build().parse();
			for (Place newplace : newPlaces) {
				List<Place> places = placeRepository.findAll();
				Boolean isUnique = true;
				for (Place place : places) { // 이미 존재하는 곳
					Boolean lat_In_Min = false;
					Boolean lat_In_Max = false;
					Boolean lng_In_Min = false;
					Boolean lng_In_Max = false;
					if (Double // 10m안에
							.parseDouble(place.getLongitude())
							- Double.parseDouble("0.011319259720414284905767162827551") < Double
									.parseDouble(newplace.getLongitude())) {
						lng_In_Min = true;
					}
					if (Double.parseDouble(place.getLongitude())
							+ Double.parseDouble("0.011319259720414284905767162827551") > Double
									.parseDouble(newplace.getLongitude())) {
						lng_In_Max = true;
					}
					if (Double.parseDouble(place.getLatitude())
							- Double.parseDouble("0.0090100236513120846942223223335961 ") < Double
									.parseDouble(newplace.getLatitude())) {
						lat_In_Min = true;
					}
					if (Double.parseDouble(place.getLatitude())
							+ Double.parseDouble("0.0090100236513120846942223223335961 ") > Double
									.parseDouble(newplace.getLatitude())) {
						lat_In_Max = true;
					}
					if (lat_In_Min && lat_In_Max && lng_In_Min && lng_In_Max) {
						if (newplace.getName().replaceAll("\\s", "")
								.indexOf(place.getName().replaceAll("\\s", "")) != -1) {
							isUnique = false;
						}
					if (Similarity.Similarity(newplace.getName().replaceAll("(개방|화장실)", ""), place.getName().replaceAll("(개방|화장실)", "")) >= 0.7) {
							isUnique = false;
						}
					}
				}
				if (isUnique) {
					placeRepository.save(newplace);
				}
			}
			// placeRepository.saveAll(places);
		} catch (IllegalStateException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// 모든 place를 불러오기

	@Transactional(readOnly = true)
	public List<Place> list(double cur_location_lat, double cur_location_lng) {
		List<Place> places = placeRepository.findAll();
		List<Place> aroundPlaces = new ArrayList<Place>();
		for (Place place : places) {
			Boolean lat_In_Min = false;
			Boolean lat_In_Max = false;
			Boolean lng_In_Min = false;
			Boolean lng_In_Max = false;
			// 1km부근
			if (cur_location_lng - Double.parseDouble("0.011319259720414284905767162827551") < Double
					.parseDouble(place.getLongitude())) {
				lng_In_Min = true;
			}
			if (cur_location_lng + Double.parseDouble("0.011319259720414284905767162827551") > Double
					.parseDouble(place.getLongitude())) {
				lng_In_Max = true;
			}
			if (cur_location_lat - Double.parseDouble("0.0090100236513120846942223223335961 ") < Double
					.parseDouble(place.getLatitude())) {
				lat_In_Min = true;
			}
			if (cur_location_lat + Double.parseDouble("0.0090100236513120846942223223335961 ") > Double
					.parseDouble(place.getLatitude())) {
				lat_In_Max = true;
			}

			if (lat_In_Min && lat_In_Max && lng_In_Min && lng_In_Max) {
				aroundPlaces.add(place);
			}
		}
		return aroundPlaces;
	}

}
