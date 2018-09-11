package com.example.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false)
	private Integer userid;  //순번
	private String id; //아이디
	private String pw; //패스워드
	private String name; //이름
	private String img1; //사진1
	private String img2; //사진2
	private String img3; //사진3
	@Column(name = "city", nullable=false)
	private Integer city; //시
	@Column(nullable=false)
	private Integer gu; //구
	@Column(nullable=false)
	private Boolean areayn; //지역공개여부
	private Date birth;//생년월일
	private Boolean birthyn; //생년월일공개여부
	private Boolean gender; //성별
	private String phone; //핸드폰번호
	private Boolean phoneyn; //핸드폰번호공개여부
	private String msg; //원하는친구
	private String intro; //자기소개
	private Boolean auth; //인증코드
	private String authkey; //이메일 인증키
	private Integer jointype;//가입방식
}
