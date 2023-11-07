package com.cos.blog.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
