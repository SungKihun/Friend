package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Chatroom;
import com.example.service.ChatService;

@Controller
@CrossOrigin(origins = "http://192.168.0.201:3000", maxAge = 3600)
public class ChatController {
	@Autowired
	private ChatService chatservice;
	
	//채팅방 만들기
	@GetMapping("/makeChatRoom")
	public String  makeChatRoom(Chatroom chatroom, int touserid) {
		chatservice.CreateChatRoom(chatroom, touserid);
		return "redirect:/listChatRoomTest?loginid=" + chatroom.getUserid();
	}

	//채팅방 리스트 보이기
	@GetMapping("/listChatRoom")
	public String listChatRoom(int loginid, Model model) {
		model.addAttribute("rooms", chatservice.ListChatRoom(loginid));
		model.addAttribute("loginid", loginid);
		System.out.println(chatservice.ListChatRoom(loginid));
		return "listChatRoom";
	}
	
	//채팅방 리스트 보이기 테스트
	@GetMapping("/listChatRoomTest")
	public String listChatRoomTest(int loginid, Model model) {
		model.addAttribute("roomsGroup", chatservice.ListChatRoom_group(loginid));
		model.addAttribute("roomsPersonal", chatservice.ListChatRoom_personal(loginid));
		model.addAttribute("loginid", loginid);
		System.out.println(chatservice.ListChatRoom(loginid));
		return "listChatRoom";
	}

	
	//멤버 방 입장
	@GetMapping("/ChatRoom")
	public String ChatRoomComeIn(int roomid, int clubid, int userid, Model model) {
		chatservice.ChatComeIn(roomid, clubid, userid);		
		model.addAttribute("roomid" , roomid);
		model.addAttribute("clubid" , clubid);
		model.addAttribute("userid" , userid);
		return "room";
	}
}