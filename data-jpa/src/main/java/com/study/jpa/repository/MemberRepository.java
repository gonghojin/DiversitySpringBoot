package com.study.jpa.repository;

import com.study.jpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

	// 아래의 메서드가 없어도
	@Query("select m from Member m where m.username= :username and m.age = :age")
	List<Member> findByUsernameAndAgeGreaterThan(@Param("username") String username, @Param("age") int age);
}
