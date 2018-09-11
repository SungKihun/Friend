package com.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;

@Entity
@Data
@IdClass(Userinterest.class)
public class Userinterest implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="userid")
	private int userid;
	@Id
	private int code;
}