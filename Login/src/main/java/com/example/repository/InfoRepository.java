package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Info;

public interface InfoRepository extends JpaRepository<Info, Integer>{

}
