package com.example.querydsl;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.querydsl.UserRepositoryImpl;

@SpringBootApplication(exclude = WebMvcAutoConfiguration.class)
public class QueryDslApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(QueryDslApplication.class, args);
		UserRepository userRepository = context.getBean(UserRepository.class);
		
		userRepository.save(new UserEntity("홍길동", 33, UserRole.USER));
		userRepository.save(new UserEntity("홍연희", 33, UserRole.USER));
		userRepository.save(new UserEntity("이홍련", 33, UserRole.USER));
		userRepository.save(new UserEntity("차미홍", 33, UserRole.USER));
		userRepository.save(new UserEntity("철수", 24, UserRole.USER));
		userRepository.save(new UserEntity("영희", 7, UserRole.USER));
		userRepository.save(new UserEntity("척준경", 41, UserRole.ADMIN));
		userRepository.save(new UserEntity("데니스", 80, UserRole.ADMIN));
		userRepository.save(new UserEntity("비숍", 10, UserRole.ADMIN));
		
		List<UserEntity> resultList = userRepository.findAllLike("%홍%");
		System.out.printf("이름에 홍을 포함한 인원 수:%d\n", resultList.size());
		
		for(UserEntity u : resultList) {
			System.out.println(u.getUsername());
		}
	}
}