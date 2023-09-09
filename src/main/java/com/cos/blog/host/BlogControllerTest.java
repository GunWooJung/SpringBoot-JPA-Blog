package com.cos.blog.host;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogControllerTest {

	@GetMapping("/test/hello")
	public String hello() {
		return "<h1>Hell</h1>";
	}
}
