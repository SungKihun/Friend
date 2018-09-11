package com.example.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.repository.FriendsRepository;
import com.example.repository.UserInterestRepository;
import com.example.repository.UserRepository;
import com.example.service.FriendService;

@RestController
public class FriendsController {
   @Autowired
   private FriendService friendService;
   
   // 3-2
   @RequestMapping("/friends")
   public Map<String, Object> SearchFriends(
         Integer userid,
         Integer page,
         @RequestParam(defaultValue="0") Boolean filter,
         @RequestParam(defaultValue="0") Integer si,
         @RequestParam(defaultValue="0") Integer gu,
         @RequestParam(defaultValue="0") Integer gender,
         @RequestParam(defaultValue="0") Integer minAge,
         @RequestParam(defaultValue="100") Integer maxAge,
         @RequestParam(defaultValue="0") Integer interest) {
      // 3-2 설정필터로 유저 검색
      if(filter) {
         Map<String, Object> resultMap = friendService.SearchFriends(page, si, gu, gender, minAge, maxAge, interest);
         return resultMap;
      }
      // 3-1 추천친구
      else {
         Map<String, Object> resultMap = friendService.DefaultSearchFriends(userid, page);
         return resultMap;
      }
   }
   
   // 1-1-4 인기친구
   @RequestMapping(value="/login", method=RequestMethod.POST)
   public Map<String, Object> popularUsers(){
      return friendService.popularUsers();
   }
}