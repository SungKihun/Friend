package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Userinterest;

public interface UserInterestRepository extends JpaRepository<Userinterest, Integer>{
	List<Userinterest> findByCode(int code);
	
	@Query(value="select seq from userinterest where code = :code", nativeQuery=true)
	public List<Integer> seqByCode(@Param("code") int code);
	
	// 3-1 userid에 해당하는 관심코드
	@Query(value = "select code from userinterest where userid = :userid", nativeQuery = true)
	public List<Integer> codeByUserid(@Param("userid") int userid);
}
