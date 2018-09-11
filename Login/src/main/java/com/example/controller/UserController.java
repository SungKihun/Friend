package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Info;
import com.example.model.User;
import com.example.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService service;
	
	//사이트 정보
	@GetMapping("/info")
	public List<Info> Info() {
		
		return service.GetInfo();
	}
	
	// 로그인
	@PostMapping("/login")
	public Map<String, Integer> Login(@RequestBody User user) {
		System.out.println("id:"+user);
		Map<String, Integer> map = new HashMap<>();
		map.put("success", service.LoginUser(user));
		return map;
	}
	
	//회원가입
	@PostMapping("/join")
	public ResponseEntity<Object> Join(@RequestBody User user) {
		if(service.GetCheckEmail(user.getId())) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		user.setAuth(false);
		user.setAuthkey(user.getId());
		service.SaveUser(user);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	//이메일 체크
	@GetMapping("/check_email")
	public Map<String, Boolean> CheckEmail(@RequestParam("id") String id) {
		Map<String, Boolean> map = new HashMap<>();
		Boolean b = service.GetCheckEmail(id);
		System.out.println(b);
		map.put("available", b);
		return map;
	}
	
	// 비밀번호 찾기
	@GetMapping("/find_password")
	public Map<String, Boolean> FindPassword(@RequestParam("id") String id){
		Map<String, Boolean> map = new HashMap<>();
		Boolean b = service.GetCheckEmail(id);
		System.out.println(b);
		map.put("success", b);
		return map;
	}
}