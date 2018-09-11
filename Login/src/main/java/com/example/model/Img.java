package com.example.model;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class Img {
	private int imgid;
	private int gubun;
	private int id;
	private String imgpath;
}
