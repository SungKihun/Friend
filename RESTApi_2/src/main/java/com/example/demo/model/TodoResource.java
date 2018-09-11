package com.example.demo.model;

import org.springframework.hateoas.ResourceSupport;

// ResourceSupport: hateoas 에 포함된 클래스, 리소스에 링크 정보를 포함
public class TodoResource extends ResourceSupport{
	private String title;
	
	public TodoResource() {
	}
	
	public TodoResource(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
}
