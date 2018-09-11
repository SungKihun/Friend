package com.example.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.LoginService;
import com.example.service.SnsServiceFactory;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	String link = null;
	
	@RequestMapping("/loginForm")
	public void loginForm() {}
	
	@RequestMapping("/quicklogin")
	public String login(String link) {
		//name 은 naver, facebook, google
		System.out.println(link);
		this.link = link;
		
		String url = "redirect:" + loginService.getAuthorization(link);
		System.out.println("url : " + url);

		return url;
	}

	//authorization code 를 각 사이트에서 발급 받아옴(code)
	@RequestMapping("callback")
	public void callback(HttpSession session, String code, String state, String error) throws IOException {
		System.out.println("callback");
		session.setAttribute("oauth_state", SnsServiceFactory.STATE);
		if(error != null) { //에러발생시 오류페이지로 이동
			System.out.println("error: " + error);
		}
		loginService.getQuickSave(session, code, state, link);


	}
}
