package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Info {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int no;
	private String content;
	private String name;
}
