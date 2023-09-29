package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@NoArgsConstructor
public class Member {
	private int id;
	private String username;
	private int password;
	private String email;
	@Builder
	public Member(int id, String username, int password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
}