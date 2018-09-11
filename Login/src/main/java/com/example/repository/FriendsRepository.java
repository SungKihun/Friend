package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.model.User;

public interface FriendsRepository extends JpaRepository<User, Integer>{
}
