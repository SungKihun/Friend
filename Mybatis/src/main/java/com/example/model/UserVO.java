package com.example.model;

import java.io.Serializable;

import lombok.*;

@Data @AllArgsConstructor
public class UserVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String userName;
	private String passWord;
}
