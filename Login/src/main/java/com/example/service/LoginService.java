package com.example.service;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

@Service
public class LoginService {
	
	@Autowired
	private SnsServiceFactory snsService;

	// authorization url 
	public String getAuthorization(String name) {
		System.out.println("getAuthorization : " + name);
		if (name.equals("naver")) {
			String str = snsService.naver().getAuthorizationUrl();
			System.out.println(str);
			return str;
		} else if(name.equals("facebook")) {
			return snsService.facebook().getAuthorizationUrl();
		} else if(name.equals("google")) {
			return snsService.google().getAuthorizationUrl();
		}
		return null;
	}

	public void getQuickSave(HttpSession session, String code, String state, String name) throws IOException {
		//access token 발급
		OAuth2AccessToken accessToken = getAccessToken(session, code, state, name);

		//profile api를 호출해서 json형식으로 읽어옴
		String apiResult = getUserProfile(accessToken, name);
		
		//DB저장
		
		System.out.println(apiResult);		
	}
	
	//	access token 발급
	public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state, String name) throws IOException{
		//state : 난수130글자 일치 여부 확인
		String sessionState = (String) session.getAttribute("oauth_state");
		if (sessionState.equals(state)) {
			// AccessToken ȹ획득후 리턴
			if (name.equals("naver")) {
				return snsService.naver().getAccessToken(code);
			} else if (name.equals("facebook")) {
				return snsService.facebook().getAccessToken(code);
			} else {
				return snsService.google().getAccessToken(code);
			}
		}
			return null;
	}

	//api profile
	public String getUserProfile(OAuth2AccessToken accessToken, String name) throws IOException {
		OAuth20Service oauthService = null;
		String return_url = "";
		if (name.equals("naver")) {
			oauthService = snsService.naver();
			return_url = "https://openapi.naver.com/v1/nid/me";
		} else if(name.equals("facebook")) {
			oauthService = snsService.facebook();
			return_url = "https://graph.facebook.com/me?fields=id";
		} else {
			oauthService = snsService.google();
			return_url = "https://www.googleapis.com/plus/v1/people/me";
		}
		OAuthRequest request = new OAuthRequest(Verb.GET, return_url, oauthService);
		oauthService.signRequest(accessToken, request);
		Response response = request.send();

		return response.getBody();
	}
}
