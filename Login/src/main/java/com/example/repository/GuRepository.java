package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Gu;

public interface GuRepository extends JpaRepository<Gu, Integer>{
	List<Gu> findByCitycode(int code);
}
