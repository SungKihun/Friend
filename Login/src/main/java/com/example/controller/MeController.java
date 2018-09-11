package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.City;
import com.example.model.Gu;
import com.example.model.Interest;
import com.example.model.User;
import com.example.repository.CityRepository;
import com.example.repository.GuRepository;
import com.example.repository.InterestRepository;
import com.example.repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://192.168.0.26:3001", maxAge = 3600)
@RequestMapping("/me")
public class MeController {
	@Autowired
	private InterestRepository interestRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private GuRepository guRepository;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/info")
	public Map<String, Object> Welcome(@RequestParam("id") String id, @RequestParam("newbie") boolean newbie){
		// 최초 로그인 시 필요한 정보 가져옴
		if(newbie) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", id);
			Map<Integer, City> cityMap = new HashMap<>();
			for(City city : cityRepository.findAll()) {
				cityMap.put(city.getCode(), city);
			}
			map.put("dataSi", cityMap);
			Map<Integer, Map> ciMap = new HashMap<>();
			for(City city : cityRepository.findAll()) {
				Map<Integer, Gu> guMap = new HashMap<>();
				for(Gu gu : guRepository.findByCitycode(city.getCode())) {
					guMap.put(gu.getGucode(), gu);
				}
				ciMap.put(city.getCode(), guMap);
			}
			map.put("dataGu", ciMap);
			map.put("defaultIntro", null);
			map.put("defaultMsg", null);
			Map<Integer, Interest> interestMap = new HashMap<>();
			for(Interest interest : interestRepository.findAll()) {
				interestMap.put(interest.getCode(), interest);
			}
			map.put("dataInterest", interestMap);
			System.out.println(map);
			return map;
		}
		return null;
	}
	
	// 자신의 상세정보 등록/수정
	@PostMapping("/info")
	public ResponseEntity<Object> SetUserInfo(@RequestBody User user) {
		System.out.println("SetUserInfo");
		System.out.println(userRepository.findById(user.getId()));
		User dbUser = userRepository.findById(user.getId());
		System.out.println("dbUser:"+dbUser);
//		dbUser.update(user);
		System.out.println("dbUser:"+dbUser);
//		System.out.println("interests "+interests);
		userRepository.save(dbUser);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@RequestMapping()
	public void FriendMePage(@RequestParam("id") String id, @RequestParam("seq") String seq) {
		
		System.out.println("MePage");
	}
}