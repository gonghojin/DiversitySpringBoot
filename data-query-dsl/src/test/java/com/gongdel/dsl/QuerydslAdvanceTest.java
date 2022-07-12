package com.gongdel.dsl;

import com.gongdel.dsl.dto.MemberDto;
import com.gongdel.dsl.dto.UserDto;
import com.gongdel.dsl.entity.Member;
import com.gongdel.dsl.entity.QMember;
import com.gongdel.dsl.entity.Team;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
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

@SpringBootTest
@Transactional
public class QuerydslAdvanceTest {

	@PersistenceContext
	EntityManager em;

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
	void projectionOne() {
		List<String> result = queryFactory
				.select(member.username)
				.from(member)
				.fetch();
	}

	@Test
	public void projectionTuple() {
		List<Tuple> result = queryFactory
				.select(member.username, member.age)
				.from(member)
				.fetch();

		for (Tuple tuple : result) {
			String username = tuple.get(member.username);
			Integer age = tuple.get(member.age);
			System.out.println("username=" + username);
			System.out.println("age=" + age);
		}
	}

	/*
		JPA에서 DTO를 조회할 떄는 new 명령어를 사용해야함.
		생성자 방식만 지원하고, package 경로를 다 적어줘야함
	 */
	@Test
	void projectionJPQLDto() {
		List<MemberDto> result = em.createQuery(
				"select new com.gongdel.dsl.dto.MemberDto(m.username, m.age) " +
						"from Member m", MemberDto.class).getResultList();
	}

	/*
		결과를 DTO를 반환할 때 사용하는 3가지 방법
	 */
	@Test
	void projectionDtoSetter() {
		List<MemberDto> result = queryFactory
				.select(Projections.bean(MemberDto.class, member.username, member.age))
				.from(member)
				.fetch();
	}

	@Test
	void projectionDtoField() {
		List<MemberDto> result = queryFactory
				.select(Projections.fields(MemberDto.class, member.username, member.age))
				.from(member)
				.fetch();
	}

	/**
	 * 프로퍼티나, 필드 접근 생성 방식에서 이름이 다를 떄 사용
	 * ExpressionUtils.as(source, alias) : 필드나 서브 쿼리에 별칭 적용
	 * username.as("~") : 필드에 별칭 적용
	 */
	@Test
	@DisplayName("필드 별칭이 다를 떄")
	void projectionDtoDifferentAlias() {
		QMember memberSub = new QMember("memberSub");
		queryFactory
				.select(Projections.fields(UserDto.class, member.username.as("name"),
						ExpressionUtils.as(
								JPAExpressions.
										select(memberSub.age.max())
										.from(memberSub), "age")
				))
				.from(member)
				.fetch();
	}
}
