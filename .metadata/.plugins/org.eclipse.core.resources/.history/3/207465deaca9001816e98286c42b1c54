package com.example.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.*;

@Entity
@Data
public class School {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SCHOOL_ID")
	private Long id;
	private String name;
	private String address;
	private String telnumber;
	
	public School() {
		
	}
	
	public School(String name) {
		this.name = name;
	}
	
	@OneToMany(mappedBy = "school")
	private Set<Student> students;
	
	public void registerStudent(Student s) {
		if(students == null) {
			students = new HashSet<Student>();
		}
		students.add(s);
	}
}
