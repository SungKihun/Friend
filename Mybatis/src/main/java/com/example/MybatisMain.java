package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;

import com.example.model.UserVO;
import com.example.repository.UserRepository;

@SpringBootApplication(exclude = WebMvcAutoConfiguration.class)
public class MybatisMain implements CommandLineRunner{
	
	public static void main(String[] args) {
		SpringApplication.run(MybatisMain.class, args);
	}
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(userRepository.getUserInfoAll().toString());
		
		UserVO userEntity = new UserVO("test4", "jpub", "qwer1234");
		userRepository.adduserInfo(userEntity);
		
		System.out.println("==입력 후==");
		 
		System.out.println(userRepository.getUserInfoAll().toString());
		
		System.out.println("like 이름 검색");
		System.out.println(userRepository.findByUserNameLike("ki").toString());
		
		System.out.println("단건 조회");
		UserVO userVO = userRepository.findByUserName("jpub");
		System.out.println(userVO.getId() +","+ userVO.getPassWord() +","+ userVO.getUserName());
	}
}
