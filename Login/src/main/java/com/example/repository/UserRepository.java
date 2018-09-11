package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	//id에 해당하는 모든정보
	User findById(@Param("id") String id);
	
	//seq에 해당하는 모든정보
	User findByUserid(@Param("seq") int seq);
	
	//id, pass check
	@Query(value="select id  from user where id = :id and pw =:pw", nativeQuery=true)
	public Object CheckLogin(@Param("id") String id, @Param("pw") String pw);
	
	// find pw
	@Query(value="select pw from user where id = :id", nativeQuery=true)
	public String passwardById(@Param("id") String id);
	
	// 1-1-4 인기친구
	@Query(value = "select u.* from user u left join"
			+ "(select id, count(*)*2 hitcnt from hit where gubun = 1 and date(hittime) >= date(subdate(now(), Interval 30 day)) group by id) a "
			+ "on u.userid = a.id left join " + " (select userid, count(*) nocnt from noticecom "
			+ "  where date(writedate) >= date(subdate(now(), Interval 30 day)) group by userid) b "
			+ " on u.userid = b.userid order by (ifnull(a.hitcnt,0) + ifnull(b.nocnt,0)) desc limit 6;", nativeQuery = true)
	public List<User> topFriends();
	
	// 3-1 추천친구 첫번째 (1/5) 관심사 중 하나가 일치하고 city와 gu가 일치하는
	@Query(value = "select * from user where name is not null and city=:city and gu=:gu and userid in"
			+ "(select userid from userinterest where code in (:code))", nativeQuery = true)
	public List<User> recommend_1(@Param("city") int city, @Param("gu") int gu, @Param("code") List<Integer> code);

	// 3-1 추천친구 두번째 (2/5) 관심사 중 하나가 일치하고 city가 일치하고 gu가 일치하지 않는
	@Query(value = "select * from user where name is not null and city=:city and gu!=:gu and userid in"
			+ "(select userid from userinterest where code in (:code))", nativeQuery = true)
	public List<User> recommend_2(@Param("city") int city, @Param("gu") int gu, @Param("code") List<Integer> code);

	// 3-1 추천친구 세번째 (3/5) 관심사 중 하나가 일치하고 city가 일치하지않고 gu가 일치하는
	@Query(value = "select * from user where name is not null and city!=:city and gu=:gu and userid in"
			+ "(select userid from userinterest where code in (:code))", nativeQuery = true)
	public List<User> recommend_3(@Param("city") int city, @Param("gu") int gu, @Param("code") List<Integer> code);

	// 3-1 추천친구 네번째 (4/5) 관심사 중 하나가 일치하고 city와 gu가 일치하는 않는
	@Query(value = "select * from user where name is not null and city!=:city and gu!=:gu and userid in"
			+ "(select userid from userinterest where code in (:code))", nativeQuery = true)
	public List<User> recommend_4(@Param("city") int city, @Param("gu") int gu, @Param("code") List<Integer> code);

	// 3-1 추천친구 다섯번째 (5/5) 관심사가 일치하지 않는
	@Query(value = "select * from user where name is not null and userid not in"
			+ "(select userid from userinterest where code in (:code))", nativeQuery = true)
	public List<User> recommend_5(@Param("code") List<Integer> code);
}
