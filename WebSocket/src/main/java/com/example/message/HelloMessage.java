package com.example.message;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class HelloMessage {

	private String name;
	private String contents;
	private LocalDateTime sendDate;
}