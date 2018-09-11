package com.example.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;

class ChatreadPK implements Serializable{
	private static final long serialVersionUID = 1L;
	private int msgid;
	private int userid;
}

@Entity
@IdClass(ChatreadPK.class)
//@Data
public class Chatread {
	@Id
	private int msgid;
	@Id
	private int userid;
	private Date writedate;
	public int getMsgid() {
		return msgid;
	}
	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public Date getWritedate() {
		return writedate;
	}
	public void setWritedate(Date writedate) {
		this.writedate = writedate;
	}
	@Override
	public String toString() {
		return "Chatread [msgid=" + msgid + ", userid=" + userid + ", writedate=" + writedate + "]";
	}
	
	
}
