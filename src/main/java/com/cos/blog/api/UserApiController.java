package com.cos.blog.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.RequestBodyUserDto;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Place;
import com.cos.blog.model.User;
import com.cos.blog.service.PlaceService;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	// API 요청 방식 : POST,  주소 : /user/login , 설명 : requestbody에
	// String으로 username , password 로 요청
	// username이 db에 존재하면 비밀번호 검사, 존재하지않으면 db에 등록
	@PostMapping("/user/login")
	public User userLogin(@RequestBody RequestBodyUserDto request, HttpSession session) {
		System.out.println("User Application : login 이 호출됨");
		User user =  userService.userLogin(request.getUsername(), request.getPassword());
		if(user!=null) {
			session.setAttribute("user", user);
		}
		//비밀번호가 맞지않는경우 추가
		return user;
	}
	
}
