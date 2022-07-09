package com.gongdel.dsl;

import com.gongdel.dsl.entity.Member;
import com.gongdel.dsl.entity.QMember;
import com.gongdel.dsl.entity.QTeam;
import com.gongdel.dsl.entity.Team;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.gongdel.dsl.entity.QMember.member;
import static com.gongdel.dsl.entity.QTeam.team;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

	@PersistenceContext
	EntityManager em;

	/*
		필드로 제공 시 동시성 문제 발생?
		NO!!
		스프링 프레임워크는 여러 쓰게드에서 동시에 같은 EntityManager에 접근해도,
		트랜잭션마다 별도의 영속성 컨텍스트를 제공하기 떄문에, 동시성 문제 발생 안함
	 */
	JPAQueryFactory queryFactory;

	@BeforeEach
	void setUp() {
		queryFactory = new JPAQueryFactory(em);

		Team teamA = new Team("teamA");
		Team teamB = new Team("teamB");
		em.persist(teamA);
		em.persist(teamB);

		Member member1 = new Member("member1", 10, teamA);
		Member member2 = new Member("member2", 20, teamA);
		Member member3 = new Member("member3", 30, teamB);
		Member member4 = new Member("member4", 40, teamB);
		em.persist(member1);
		em.persist(member2);
		em.persist(member3);
		em.persist(member4);
	}

	@Test
	void startJPQL() {

		String qlString = "select m from Member m " + "where m.username = :username";
		Member findMember =
				em.createQuery(qlString, Member.class).setParameter("username", "member1").getSingleResult();

		assertThat(findMember.getUsername()).isEqualTo("member1");
	}

	@Test
	void startQuerydsl() {

		Member findMember = queryFactory
				.select(member)
				.from(member)
				.where(member.username.eq("member1")) // 파라미터 바인딩 처리
				.fetchOne();

		assertThat(findMember.getUsername()).isEqualTo("member1");
	}

	@Test
	void search() {
		Member findMember = queryFactory
				.selectFrom(member)
				.where(member.username.eq("member1")
						.and(member.age.eq(10))
				).fetchOne();

		assertThat(findMember.getUsername()).isEqualTo("member1");

	}

	@Test
	@DisplayName("AND 조건을 파라미터로 처리")
	public void searchAndParam() {
		List<Member> member1 = queryFactory
				.selectFrom(member)
				.where(member.username.eq("member1"),
						member.age.eq(10)
				)
				.fetch();
		assertThat(member1.size()).isEqualTo(1);

	}

	@Test
	void 결과조회() {
		// List
		List<Member> fetch = queryFactory.selectFrom(member)
				.fetch();

		// 단건
	/*	Member findMember1 = queryFactory.selectFrom(member)
				.fetchOne();
*/

		// 처음 한 건 조회
		Member findMember2 = queryFactory.selectFrom(member)
				.fetchFirst();

		// 페이징에서 사용
		QueryResults<Member> memberQueryResults = queryFactory.selectFrom(member)
				.fetchResults();

		long l = queryFactory.selectFrom(member)
				.fetchCount();
	}

	/**
	 * 회원 정렬 순서
	 * 1. 회원 나이 내림차순(desc)
	 * 2. 회원 이름 올림차순(asc)
	 * 단 2에서 회원 이름이 없으면 마지막에 출력(nulls last)
	 */
	@Test
	void 정렬() {
		em.persist(new Member(null, 100));
		em.persist(new Member("member5", 100));
		em.persist(new Member("member6", 100));

		List<Member> result = queryFactory.selectFrom(member)
				.where(member.age.eq(100))
				.orderBy(member.age.desc(), member.username.asc().nullsLast())
				.fetch();

		Member member5 = result.get(0);
		Member member6 = result.get(1);
		Member memberNull = result.get(2);
		assertThat(member5.getUsername()).isEqualTo("member5");
		assertThat(member6.getUsername()).isEqualTo("member6");
		assertThat(memberNull.getUsername()).isNull();
	}

	@Test
	@DisplayName("페이징_조회건수제한")
	public void paging1() {
		List<Member> result = queryFactory.selectFrom(member)
				.orderBy(member.username.desc())
				.offset(1) //0부터 시작(zero index)
				.limit(2) //최대 2건 조회
				.fetch();

		assertThat(result.size()).isEqualTo(2);
	}

	/**
	 * count 쿼리가 실행되니 성능상 주의
	 * - 실무에서 페이징 쿼리를 작성할 때는, 다에터를 조회하는 쿼리는
	 * 여러 테이블을 조인해야 하지만, count는 조인이 필요 없는 경우도 있다.
	 * 하지만 이렇게 자동화된 count 쿼리는 원본 쿼리와 같이 모두 조인을 해버리기 떄문에
	 * 성능이 안 나올 수 있다.
	 * count 쿼리에 조인이 필요없는 성능 최적화가 필요하다면, count 전용 쿼리를 별도로 작성하자
	 */
	@Test
	@DisplayName("전체 조회수가 필요할 떄")
	void paging2() {
		QueryResults<Member> queryResults = queryFactory.selectFrom(member)
				.orderBy(member.username.desc())
				.offset(1)
				.limit(2)
				.fetchResults();

		assertThat(queryResults.getTotal()).isEqualTo(4);
		assertThat(queryResults.getLimit()).isEqualTo(2);
		assertThat(queryResults.getOffset()).isEqualTo(1);
		assertThat(queryResults.getResults().size()).isEqualTo(2);
	}

	/**
	 * 팀의 이름과 각 팀의 평균 연령을 구해라.
	 */
	@Test
	public void group() {
		List<Tuple> result = queryFactory.select(team.name, member.age.avg())
				.from(member)
				.join(member.team, team)
				.groupBy(team.name)
				.fetch();

		Tuple teamA = result.get(0);
		Tuple teamB = result.get(1);
		assertThat(teamA.get(team.name)).isEqualTo("teamA");
		assertThat(teamA.get(member.age.avg())).isEqualTo(15);
		assertThat(teamB.get(team.name)).isEqualTo("teamB");
		assertThat(teamB.get(member.age.avg())).isEqualTo(35);
	}

	/**
	 * 팀A에 소속된 모든 회원
	 */
	@Test
	public void join() {
		QMember member = QMember.member;
		QTeam team = QTeam.team;

		List<Member> result = queryFactory
				.selectFrom(member)
				.join(member.team, team)
				.where(team.name.eq("teamA"))
				.fetch();

		assertThat(result)
				.extracting("username")
				.containsExactly("member1", "member2");

	}

	/*
	 * 세타 조인(연관관계가 없는 필드로 조인) * 회원의 이름이 팀 이름과 같은 회원 조회
	 */

	@Test
	void theta_join() {
		em.persist(new Member("teamA"));
		em.persist(new Member("teamB"));

		List<Member> result = queryFactory
				.select(member)
				.from(member, team)
				.where(member.username.eq(team.name))
				.fetch();

		assertThat(result).extracting("username")
				.containsExactly("teamA", "teamB");
	}


}
