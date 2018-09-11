package com.example.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.*;

@Entity
@Data
@Table(name="tbl_user")
public class UserEntity implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String userName;
	private Integer age;
	private Date createdAt;
	
	@Column(name="role")
	@Enumerated(EnumType.ORDINAL)
	private UserRole role;
	
	@PrePersist
	public void beforeCreate() {
		createdAt = new Date();
	}
	
	public UserEntity() {
		
	}
	
	public UserEntity(String userName) {
		this.userName = userName;
	}
	
	public UserEntity(String userName, Integer age, UserRole role) {
		this.userName = userName;
		this.age = age;
		this.role = role;
	}
}