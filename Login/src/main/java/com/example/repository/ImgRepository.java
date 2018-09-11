package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Img;

public interface ImgRepository extends JpaRepository<Img, Integer>{
	// img테이블에서 구분과 아이디에 해당하는 이미지클래스
	List<Img> findByGubunAndId(int gubun, int id);

	// 1-1-4 img테이블에서 구분과 아이디에 해당하는 이미지경로
	@Query(value = "select imgpath from img where gubun = :gubun and id = :id", nativeQuery = true)
	public List<String> imgpathByGubunAndId(@Param("gubun") int gubun, @Param("id") int id);
}
