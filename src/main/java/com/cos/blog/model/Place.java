package com.cos.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.ColumnDefault;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Place {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 	//식별자
	
	@Column
	private String name = "정보 없음";	//화장실 이름
		
	@Column
	private String longitude = "0";
	
	@Column
	private String latitude = "0";
	
	@Column
	private String disabled_man = "정보 없음"; //남자장애인용 엑셀정보
	
	@Column
	private String disabled_woman = "정보 없음"; //여자장애인용 엑셀정보
	
	@Column
	private String diaper = "정보 없음"; //기저귀교환대 유무 엑셀정보

	@Column
	private String opentime = "정보 없음"; 
	
	@Column
	private String emergency_bell = "없음"; 
	
	@Column
	private double average_score = 0; 
	// 장소에 존재하지 않아 신고한 횟수 3이상이면 delete
}
