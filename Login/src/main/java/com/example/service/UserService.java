package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Info;
import com.example.model.User;
import com.example.repository.InfoRepository;
import com.example.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired 
	private InfoRepository infoRepository;
	
	//사이트 정보
	public List<Info> GetInfo() {
		List<Info> info = infoRepository.findAll();
		return info;
	}
	
	//로그인
	public int LoginUser(User user) {
		Object returnUser =  userRepository.CheckLogin(user.getId(), user.getPw());
		
		int findYn=0;
		// 로그인 성공
		if (returnUser != null) {
			findYn = 0;
		}
		// 존재하지 않는 아이디
		else if(userRepository.findById(user.getId()) == null) {
			findYn = 1;
		}
		// 잘못된 비밀번호
		else if(!userRepository.passwardById(user.getId()).equals(user.getPw())) {
			findYn = 2;
		}
		// 네트워크 에러
//		else if(false) {
//			findYn = 3;
//		}
		// 미인증 아이디
		else if(userRepository.findById(user.getId()).getAuth() == false){
			findYn = 4;
		}
		System.out.println("findYn:"+findYn);
		return findYn;
				
	}
	
	//회원저장
	public void SaveUser(User user) {
		userRepository.save(user);
	}

	//이메일 존재여부 체크
	public Boolean GetCheckEmail(String id) {
		System.out.println("getcheckemail : " + id);
		User returnUser = userRepository.findById(id);
		
		// 존재하면 true 존재하지않으면 false
		Boolean useYn;
		if (returnUser != null) {
			useYn = true;
		}else {
			useYn = false;
		}
			
		return useYn;
	}
	
	// 유저 정보 불러옴
	public User GetUserInfo(String id) {
		User user = new User();
		user = userRepository.findById(id);
		return user;
	}
}
