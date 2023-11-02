package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

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
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id; 	//식별자
	
	@Column
	private String name;	//화장실 이름
		
	@Lob // 화장실 추가 내용
	private String content;

	@ColumnDefault("0")
	private String longitude;
	
	@ColumnDefault("0")
	private String latitude;
	
	//@Column
	//private HasToilet disabled; //장애인용
	
	//@Column
	//private HasToilet diaperChange; //기저귀교환대 유무
}
