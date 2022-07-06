package com.gongdel.dsl;

import com.gongdel.dsl.entity.Member;
import com.gongdel.dsl.entity.QMember;
import com.gongdel.dsl.entity.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.gongdel.dsl.entity.QMember.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
}
