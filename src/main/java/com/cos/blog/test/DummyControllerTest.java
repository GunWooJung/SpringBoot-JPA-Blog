package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id , @RequestBody User requestUser) {
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당 유저는 없습니다.id: "+id);
		});
		user.setEmail(requestUser.getEmail());
		user.setPassword(requestUser.getPassword());
		return user;
	}
	
	@DeleteMapping("/dummy/user/{id}")
	public String deleteUser(@PathVariable int id) {
		try{
			userRepository.deleteById(id);
		}
		catch(Exception e) {
			
		}
		return "삭제되었습니다. id : "+id;
	}
	
	
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();

	}
	
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size = 2 , sort = "id" , direction = Sort.Direction.DESC)Pageable pageable){
		
		Page<User> PagingUser = userRepository.findAll(pageable);
		List<User> users = PagingUser.getContent();
		return users;
	}
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {

			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다.id: "+id);
			}
		
		});
		
		return user;
	}
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("--------------------");
		System.out.println(user.getId());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getEmail());
		user.setRole(RoleType.USER);
		System.out.println(user.getRole());
		System.out.println(user.getCreateDate());
		userRepository.save(user);
		return "회원가입이 완료되었습니다."; 
	}
	
}
