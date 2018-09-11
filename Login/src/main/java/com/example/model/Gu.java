package com.example.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;

@Entity
@IdClass(Gu.class)
@Data
public class Gu implements Serializable{
	@Id
	private int citycode;
	@Id
	private int gucode;
	private String name;
}
