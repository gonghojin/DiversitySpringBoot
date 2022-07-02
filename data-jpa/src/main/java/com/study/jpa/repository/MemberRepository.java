package com.study.jpa.repository;

import com.study.jpa.dto.MemberDto;
import com.study.jpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

	/*
		벌크성 수정, 삭제 쿼리는 @Modifying 어노테이션 사용이 필요
		또한, 벌크성 쿼리를 실행하고 나서 영속성 컨텍스트를 초기화 해주어야 한다.
		: 이 옵션 없이 같은 트랜잭션 안에서 조회를 할 경우, 영속성 컨텍스트에 과거 값이 남아서 문제가 될 수 있음.
		따라서 만약 조회해야 한다면 꼭 영속석 컨텍스트를 초기화

		참고:
		벌크 연산은 영속성 컨텍스트를 무시하고 실행하기 떄문에(jpql이므로, db에 직접 쿼리),  영속성 컨텍스트에 있는 엔티티의 상태와
		DB에 엔티티 상태가 다름

		권장하는 방안
		1. 영속성 컨텍스에 엔티티가 없는 상태에서 벌크연산을 먼저 실행
		2. 부득이하게 영속성 컨텍스트에 엔티티가 있으면 벌크 연산 직후 영속성 컨텍스트를 초기화한다.
	*/
	@Modifying(clearAutomatically = true)
	@Query("update Member m set m.age = m.age + 1 where m.age >= :age")
	int bulkAgePlus(@Param("age") int age);

	// JPQL 패치 조인
	@Query("select m from Member m left join fetch m.team")
	List<Member> findMemberFetchJoin();

	/*
		스프링 데이터 JPA는 엔티티 그래프 기능을 제공하여, JPQL 없이도 폐치 조인을 사용할 수 있도록 지원
		- 단순한 폐치 조인은 EntityGraph를, 복잡한 폐치조인은 JPQL로~
	 */
	// 공통 메서드 오버라이드
	@Override
	@EntityGraph(attributePaths = "team")
	List<Member> findAll();

	// JPQL + 엔티티 그래프
	@EntityGraph(attributePaths = {"team"})
	@Query("select m from Member m")
	List<Member> findMemberEntityGraph();

	// 메서드 이름의 쿼리에서 특히 편리함
	@EntityGraph(attributePaths = {"team"})
	List<Member> findEntityGraphByUsername(String username);
}
