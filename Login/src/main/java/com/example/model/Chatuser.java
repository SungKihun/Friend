package com.example.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;

class ChatuserPK implements Serializable{
	private static final long serialVersionUID = 1L;
	private int roomid;
	private int userid;
}

@Entity
@IdClass(ChatuserPK.class)
//@Data
public class Chatuser {
	@Id
	private int roomid;
	@Id
	private int userid;
	public int getRoomid() {
		return roomid;
	}
	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	@Override
	public String toString() {
		return "Chatuser [roomid=" + roomid + ", userid=" + userid + "]";
	}
	
	
}