package com.cos.blog.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ControllerTest {
	@GetMapping("/test")
	public String saveForm() {
		return "index";
	}
	
	@GetMapping({"","/ "})
	public String test() {
		return "index";
	}
}
