package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Chatmsg;

public interface ChatMsgRepository extends JpaRepository<Chatmsg, Integer>{

}
