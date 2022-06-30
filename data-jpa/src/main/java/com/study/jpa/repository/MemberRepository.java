package com.study.jpa.repository;

import com.study.jpa.dto.MemberDto;
import com.study.jpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

	// 아래의 메서드가 없어도
	@Query("select m from Member m where m.username= :username and m.age = :age")
	List<Member> findByUsernameAndAgeGreaterThan(@Param("username") String username, @Param("age") int age);

	// 단순히 값 하나를 조회
	@Query("select m.username from Member m")
	List<String> findUsernameList();

	@Query("select new com.study.jpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
	List<MemberDto> findMemberDto();

	@Query("select m from Member m where m.username in :names")
	List<Member> findByNames(@Param("names") List<String> names);

	/*
		반환 타입
	 */
	List<Member> findByUsername(String name); // 컬렉션: 조회 결과 없을 시 빈 컬렉션 반환

	Member findMemberByUsername(String name); // 단건 : 결과가 없을 시 null 반환, 2건 이상 NonUniqueResultException

	Optional<Member> findOptionalByUsername(String name);// 단건 Optional
}
