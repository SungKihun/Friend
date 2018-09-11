package com.example.demo.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;

@ApiModel
public class User {
	private int userNo;
	private String userId;
	private String email;
	private String uname;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date regDate;

	public User(int userNo, String userId, String email, String uname, Date regDate) {
		super();
		this.userNo = userNo;
		this.userId = userId;
		this.email = email;
		this.uname = uname;
		this.regDate = regDate;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	
}
