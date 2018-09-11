package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.example.model.Chatmsg;
import com.example.model.Chatroom;
import com.example.service.ChatService;

@Controller
public class ChatMessageController {

    @Autowired
    private ChatService chatservice ;
    
    @MessageMapping("/alarmMessage")
    public void alarmSave(Chatmsg msg, Chatroom room) throws InterruptedException {
        //Thread.sleep(1000); // simulated delay
    	//System.out.println(SessionBindingListener.loginList);
    	System.out.println(room.getClubid());
    	
    	chatservice.SendChatMsg(msg, room.getClubid());


//    	if (SessionBindingListener.loginList.get(receiveid) != null) {
//    		
//    	}
    	

    }
    
}