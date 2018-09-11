package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.model.UserEntity;
import com.example.model.UserRole;
import com.example.repository.UserRepository;

@SpringBootApplication
public class JPAMain {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(JPAMain.class, args);
		UserRepository userRepository = context.getBean(UserRepository.class);
		
		userRepository.save(new UserEntity("윤사장", 60, UserRole.ADMIN));
		UserEntity user = userRepository.findByUserName("윤사장");
		System.out.println("나이:"+user.getAge()+","+"이름:"+user.getUserName()+","+"생성일:"+user.getCreatedAt());
	}
}
