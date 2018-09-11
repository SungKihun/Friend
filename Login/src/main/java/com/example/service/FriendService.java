package com.example.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.model.Userinterest;
import com.example.repository.FriendsRepository;
import com.example.repository.ImgRepository;
import com.example.repository.UserInterestRepository;
import com.example.repository.UserRepository;

@Service
public class FriendService {
	@Autowired
	private UserInterestRepository userInterestRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ImgRepository imgRepository;
	@Autowired
	EntityManager entitymanager;
	
	// 1-1-4 인기친구
	   public Map<String, Object> popularUsers(){
	      // 인기친구 쿼리
	      List<User> userList = userRepository.topFriends();
	      // 구분 1: 회원
	      final int gubun = 1;
	      
	      Map<Integer, Object> popUserMap = new HashMap<>();
	      for(User u : userList) {
	         Map<String, Object> UserMap = new HashMap<>();
	         // userid 맵
	         UserMap.put("userid", u.getUserid());
	         // name 맵
	         UserMap.put("name", u.getName());
	         List<Map<String, String>> imgsList = new ArrayList<Map<String, String>>();
	         // 구분1, 유저아이디 에 해당하는 imgpath
	         List<String> imgList = imgRepository.imgpathByGubunAndId(gubun, u.getUserid());
	         // 이미지를 이미지리스트에 추가
	         for(String img : imgList) {
	            Map<String, String> imgMap = new HashMap<String, String>();
	            imgMap.put("img", img);
	            imgsList.add(imgMap);
	         }
	         // 이미지리스트
	         UserMap.put("imgs", imgsList);
	         // 유저아이디:유저정보
	         popUserMap.put(u.getUserid(), UserMap);
	            
	      }
	      
	      Map<String, Object> popularUsersMap = new HashMap<String, Object>();
	      popularUsersMap.put("popularUsers", popUserMap);
	      
	      List<Integer> useridList = new ArrayList<>();
	      // 인기유저 순서 리스트
	      for(User user : userRepository.topFriends()) {
	         useridList.add(user.getUserid());
	      }
	      popularUsersMap.put("popularIndex", useridList);
	      
	      return popularUsersMap;
	   }
	   
	// 3-2
	   // 필터 검색
	   public Map<String, Object> SearchFriends(Integer page, Integer si, Integer gu, Integer gender, Integer minAge, Integer maxAge, Integer interest) {
	      // TODO Auto-generated method stub
	      CriteriaBuilder builder=entitymanager.getCriteriaBuilder();
	      CriteriaQuery<User> query=builder.createQuery(User.class);
	      Root<User> root=query.from(User.class);
	      List<Predicate> predicates=new ArrayList<Predicate>();
	      // 시가 있다면
	      if(si != 0) {
	         predicates.add(builder.equal(root.get("city"), si));
	      }
	      // 구가 있다면
	      if(gu != 0) {
	         predicates.add(builder.equal(root.get("gu"), gu));
	      }
	      // 성별이 있다면
	      if(gender != 0) {
	         predicates.add(builder.equal(root.get("gender"), gender));
	      }
	      // 나이가 있다면
	      if(minAge!=0 && maxAge!=100) {
	         //between : 사이 값
	         Date min = new Date();
	         Date max = new Date();
	         min.setYear(min.getYear()-minAge);
	         max.setYear(max.getYear()-maxAge);
	         predicates.add(builder.between(root.get("birth"), max, min));
	      }
	      // 관심사가 있다면
	      if(interest!=0) {
	         Subquery<Userinterest> sub=query.subquery(Userinterest.class);
	         Root<Userinterest> subroot=query.from(Userinterest.class);
	         Predicate pd=builder.equal(subroot.get("code"), interest);
	         sub.from(Userinterest.class);
	         sub.select(subroot.get("userid")).where(pd);
	         predicates.add(builder.and(builder.in(root.get("userid")).value(sub)));
	      }
	      query.select(root).where(predicates.toArray(new Predicate[] {}));
	      
	      TypedQuery<User> typedQuery = entitymanager.createQuery(query);
	      List<User> userList = typedQuery.getResultList();
	      
	      List<User> userListPage = new ArrayList<>();
	      // page당 20명씩 반환
	      if (page * 20 >= userList.size() && userList.size() > page * 20 - 20) {
	            for (int i = page * 20 - 20; i < userList.size(); i++) {
	               userListPage.add(userList.get(i));
	            }
	      }
	        else if (page * 20 < userList.size()) {
	            for (int i = page * 20 - 20; i < page * 20; i++) {
	               userListPage.add(userList.get(i));
	            }
	        }
	        else {
	           return null;
	        }
	      
	      final int gubun = 1;
	      Map<String, Object> popularMap = new HashMap<>();
	      Map<Object, Object> useridMap = new HashMap<>();
	      List<Integer> useridList = new ArrayList<>();
	      // user정보 매핑
	      for(User u : userListPage) {
	         Map<String, Object> userMap = new HashMap<>();
	         userMap.put("userid", u.getUserid());
	         userMap.put("name", u.getName());
	         userMap.put("gender", u.getGender());
	         // age 계산
	         userMap.put("age", new Date().getYear() - u.getBirth().getYear() +1);
	         userMap.put("city", u.getCity());
	         userMap.put("gu", u.getGu());
	         // user정보에 userinterest 추가
	         List<Integer> interestList = userInterestRepository.codeByUserid(u.getUserid());
	         userMap.put("interests", interestList);
	         // user정보에 imgpath 추가
	         List<String> imgList = imgRepository.imgpathByGubunAndId(gubun, u.getUserid());
	         List<Object> imgsList = new ArrayList<>();
	         for(String img : imgList) {
	            Map<String, String> imgMap = new HashMap<>();
	            imgMap.put("img", img);
	            imgsList.add(imgMap);
	         }
	         userMap.put("imgs", imgsList);
	         // userid 매핑
	         useridMap.put(userMap.get("userid"), userMap);
	         popularMap.put("users", useridMap);
	      }
	      
	      return popularMap;
	   }
	   
	// 3-1 필터없는 기본검색
	   public Map<String, Object> DefaultSearchFriends(Integer userid, Integer page){
	      // userid에 해당하는 유저정보
	      User user = userRepository.findByUserid(userid);
	      // userid에 해당하는 userinterest값
	      List<Integer> userCodeList = userInterestRepository.codeByUserid(user.getUserid());
	      
	      List<User> userList = new ArrayList<>();
	      userList.addAll(userRepository.recommend_1(user.getCity(), user.getGu(), userCodeList));
	      userList.addAll(userRepository.recommend_2(user.getCity(), user.getGu(), userCodeList));
	      userList.addAll(userRepository.recommend_3(user.getCity(), user.getGu(), userCodeList));
	      userList.addAll(userRepository.recommend_4(user.getCity(), user.getGu(), userCodeList));
	      userList.addAll(userRepository.recommend_5(userCodeList));
	      List<User> userListPage = new ArrayList<>();
	      
	      // page당 20명씩 반환
	      if (page * 20 >= userList.size() && userList.size() > page * 20 - 20) {
	            for (int i = page * 20 - 20; i < userList.size(); i++) {
	               userListPage.add(userList.get(i));
	            }
	      }
	        else if (page * 20 < userList.size()) {
	            for (int i = page * 20 - 20; i < page * 20; i++) {
	               userListPage.add(userList.get(i));
	            }
	        }
	        else {
	           return null;
	        }
	      
	      final int gubun = 1;
	      
	      Map<String, Object> popularMap = new HashMap<>();
	      Map<Object, Object> useridMap = new HashMap<>();
	      List<Integer> useridList = new ArrayList<>();
	      // user정보 매핑
	      for(User u : userListPage) {
	         Map<String, Object> userMap = new HashMap<>();
	         userMap.put("userid", u.getUserid());
	         userMap.put("name", u.getName());
	         userMap.put("gender", u.getGender());
	         // age 계산
	         userMap.put("age", new Date().getYear() - u.getBirth().getYear() +1);
	         userMap.put("city", u.getCity());
	         userMap.put("gu", u.getGu());
	         // user정보에 userinterest 추가
	         List<Integer> interestList = userInterestRepository.codeByUserid(u.getUserid());
	         userMap.put("interests", interestList);
	         // user정보에 imgpath 추가
	         List<String> imgList = imgRepository.imgpathByGubunAndId(gubun, u.getUserid());
	         List<Object> imgsList = new ArrayList<>();
	         for(String img : imgList) {
	            Map<String, String> imgMap = new HashMap<>();
	            imgMap.put("img", img);
	            imgsList.add(imgMap);
	         }
	         userMap.put("imgs", imgsList);
	         // userid 매핑
	         useridMap.put(userMap.get("userid"), userMap);
	         popularMap.put("users", useridMap);
	         
	         // 추천친구 순서 리스트
	         useridList.add(u.getUserid());
	      }
	      
	      popularMap.put("popularIndex", useridList);
	      return popularMap;
	   }

	   public void test()
	   {
		   return;
	   }
}
