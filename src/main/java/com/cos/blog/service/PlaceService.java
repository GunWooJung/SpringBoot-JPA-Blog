package com.cos.blog.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cos.blog.model.Place;
import com.cos.blog.model.Similarity;
import com.cos.blog.repository.PlaceRepository;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class PlaceService {

	@Autowired
	private PlaceRepository placeRepository;

	@Transactional(readOnly = true)
	public List<Place> placeShow(double cur_location_lat, double cur_location_lng, String changing_table_man,
			String changing_table_woman, String disabled_person, String emergency_bell_disabled,
			String emergency_bell_man, String emergency_bell_woman) {
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
			// 필터링 조건에 부합하는 결과 찾기
			if (lat_In_Min && lat_In_Max && lng_In_Min && lng_In_Max) {
				Boolean check = true;
				if (disabled_person.equals("true")) {
					if (!(place.getDisabled_man().equals("있음") || place.getDisabled_woman().equals("있음")))
						check = false;
				}
				if (changing_table_man.equals("true") && changing_table_woman.equals("true")) {
					if (!(place.getDiaper().equals("남자") || place.getDiaper().equals("여자")
							|| place.getDisabled_woman().equals("남여")))
						check = false;
				}
				if (!changing_table_man.equals("true") && changing_table_woman.equals("true")) {
					if (!(place.getDiaper().equals("여자") || place.getDisabled_woman().equals("남여")))
						check = false;
				}
				if (changing_table_man.equals("true") && !changing_table_woman.equals("true")) {
					if (!(place.getDiaper().equals("남자") || place.getDisabled_woman().equals("남여")))
						check = false;
				}
				if (emergency_bell_disabled.equals("true") && emergency_bell_man.equals("true")
						&& emergency_bell_woman.equals("true")) {
					if (!(place.getEmergency_bell().indexOf("장애") != -1 || place.getEmergency_bell().indexOf("남자") != -1
							|| place.getEmergency_bell().indexOf("여자") != -1))
						check = false;
				}
				if (!emergency_bell_disabled.equals("true") && emergency_bell_man.equals("true")
						&& emergency_bell_woman.equals("true")) {
					if (!(place.getEmergency_bell().indexOf("남자") != -1
							|| place.getEmergency_bell().indexOf("여자") != -1))
						check = false;
				}
				if (emergency_bell_disabled.equals("true") && !emergency_bell_man.equals("true")
						&& emergency_bell_woman.equals("true")) {
					if (!(place.getEmergency_bell().indexOf("장애") != -1
							|| place.getEmergency_bell().indexOf("여자") != -1))
						check = false;
				}
				if (emergency_bell_disabled.equals("true") && emergency_bell_man.equals("true")
						&& !emergency_bell_woman.equals("true")) {
					if (!(place.getEmergency_bell().indexOf("장애") != -1
							|| place.getEmergency_bell().indexOf("남자") != -1))
						check = false;
				}
				if (!emergency_bell_disabled.equals("true") && !emergency_bell_man.equals("true")
						&& emergency_bell_woman.equals("true")) {
					if (!(place.getEmergency_bell().indexOf("여자") != -1))
						check = false;
				}
				if (emergency_bell_disabled.equals("true") && !emergency_bell_man.equals("true")
						&& !emergency_bell_woman.equals("true")) {
					if (!(place.getEmergency_bell().indexOf("장애") != -1))
						check = false;
				}
				if (!emergency_bell_disabled.equals("true") && emergency_bell_man.equals("true")
						&& !emergency_bell_woman.equals("true")) {
					if (!(place.getEmergency_bell().indexOf("남자") != -1))
						check = false;
				}
				if (check)
					aroundPlaces.add(place);
			}
		}
		return aroundPlaces;
	}

	// 모든 place를 jpa에 등록하기
	@Transactional
	public void placeAdd(String fileName) {
		String csvFile = "C:\\workspacespring\\project_local\\src\\main\\resources\\" + fileName + ".csv";
		Charset.forName("UTF-8");

		try {

			List<Place> newPlaces = new CsvToBeanBuilder<Place>(new FileReader(csvFile)).withType(Place.class).build()
					.parse();
			for (Place newplace : newPlaces) { // 엑셀에 장소
				List<Place> places = placeRepository.findAll(); // DB의 장소
				Boolean isUnique = true;
				for (Place place : places) { //
					Boolean lat_In_Min = false;
					Boolean lat_In_Max = false;
					Boolean lng_In_Min = false;
					Boolean lng_In_Max = false;
					if (Double // 1km안에 유사도가 0.5이상
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
						if (Similarity.CalculateSimilarity(
								newplace.getName().replaceAll("\\s", "").replaceAll("(개방|화장실)", ""),
								place.getName().replaceAll("\\s", "").replaceAll("(개방|화장실)", "")) >= 0.5) {
							isUnique = false;
						}
						if (newplace.getLatitude().equals(place.getLatitude())
								&& newplace.getLongitude().equals(place.getLongitude())) {
							isUnique = false;
						}

						Boolean lat_In_Min2 = false;
						Boolean lat_In_Max2 = false;
						Boolean lng_In_Min2 = false;
						Boolean lng_In_Max2 = false;
						if (Double // 10m안에 장소가 있으면 제외
								.parseDouble(place.getLongitude())
								- Double.parseDouble("0.00011319259720414284905767162827551") < Double
										.parseDouble(newplace.getLongitude())) {
							lng_In_Min2 = true;
						}
						if (Double.parseDouble(place.getLongitude())
								+ Double.parseDouble("0.00011319259720414284905767162827551") > Double
										.parseDouble(newplace.getLongitude())) {
							lng_In_Max2 = true;
						}
						if (Double.parseDouble(place.getLatitude())
								- Double.parseDouble("0.000090100236513120846942223223335961 ") < Double
										.parseDouble(newplace.getLatitude())) {
							lat_In_Min2 = true;
						}
						if (Double.parseDouble(place.getLatitude())
								+ Double.parseDouble("0.000090100236513120846942223223335961 ") > Double
										.parseDouble(newplace.getLatitude())) {
							lat_In_Max2 = true;
						}
						if (lat_In_Min2 && lat_In_Max2 && lng_In_Min2 && lng_In_Max2) {
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
	public List<Place> placeSearch(String keyword, String latitude, String longitude) {
		List<Place> places = placeRepository.findAll();
		List<Place> placeResult = new ArrayList<Place>();
		for (Place place : places) {
			if (place.getName().replaceAll("\\s", "").indexOf(keyword.replaceAll("\\s", "")) != -1) {
				placeResult.add(place); // 키워드가 이름에 포함되면 add
			}
		}
		double lat = Double.parseDouble(latitude);
		double lng = Double.parseDouble(longitude);
		if (!placeResult.isEmpty()) {
			double min = (lat - Double.parseDouble(placeResult.get(0).getLatitude())) * (lat - Double.parseDouble(placeResult.get(0).getLatitude())) + 0.0090100236513120846942223223335961
					* 0.0090100236513120846942223223335961 * (lng - Double.parseDouble(placeResult.get(0).getLongitude())) * (lng - Double.parseDouble(placeResult.get(0).getLongitude())) ;
			int min_i = 0;
			for (Place p : placeResult) {
				double p_lat = Double.parseDouble(p.getLatitude());
				double p_lng = Double.parseDouble(p.getLongitude());
				double distance = (lat - p_lat) * (lat - p_lat) + 0.0090100236513120846942223223335961
						* 0.0090100236513120846942223223335961 * (lng - p_lng) * (lng - p_lng);
				if (min > distance) {
					min = distance;
					min_i = placeResult.indexOf(p);
				}
			}
			 Collections.swap(placeResult, 0, min_i);
		}
		return placeResult;
	}

	@Transactional // 모든 DB 삭제
	public void placeDelete(int id) {
		placeRepository.deleteById(id);
	}

	@Transactional
	public Place placeDetail(int id) {
		Optional<Place> places = placeRepository.findById(id);
		Place place = places.get();
		return place;
	}

}
