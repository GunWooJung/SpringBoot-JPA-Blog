package com.cos.blog.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {
	@GetMapping("/test")
	public String test2() {
		return "index";
	}
	
	@GetMapping("/viewdetails")
	public String viewdetails() {
		return "view_details";
	}

	@GetMapping("/amend_information")
	public String amend_information() {
		return "amend_information";
	}
	
	@GetMapping({"","/ "})
	public String test() {
		return "index";
	}
}
