package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Interest;

public interface InterestRepository extends JpaRepository<Interest, Integer>{
	
}
