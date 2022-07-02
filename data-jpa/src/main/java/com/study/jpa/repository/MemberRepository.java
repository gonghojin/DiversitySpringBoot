package com.study.jpa.repository;

import com.study.jpa.dto.MemberDto;
import com.study.jpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
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

	/*
		페이징과 정렬

	Page<Member> findByUsername(String name, Pageable pageable); // count 쿼리 사용

	Slice<Member> findSliceByUsername(String name, Pageable pageable); // count 쿼리 사용 안함

	List<Member> findMemberByUsername(String name, Pageable pageable); // count 쿼리 사용 안함

	List<Member> findByUsername(String name, Sort sort);
 	*/

	Page<Member> findByAge(int age, Pageable pageable);

	/*
		count 쿼리를 다음과 같이 분리할 수 있음
		: 카운트 쿼리 분리(이건 복잡한 sql에서 사용, 데이터는 left join, 카운트는 left join 안해도 됨)
		실무에서 매우 중요!!! (전체 count 쿼리는 매우 무겁다.)
	 */
	@Query(value = "select m from Member m", countQuery = "select count(m.username)from Member m")
	Page<Member> findMemberAllCountBy(Pageable pageable);
}
