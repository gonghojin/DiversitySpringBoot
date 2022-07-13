package com.gongdel.dsl;

import com.gongdel.dsl.dto.MemberDto;
import com.gongdel.dsl.dto.UserDto;
import com.gongdel.dsl.entity.Member;
import com.gongdel.dsl.entity.QMember;
import com.gongdel.dsl.entity.Team;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
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

	@Test
	void projectionConstruct() {
		List<MemberDto> result = queryFactory
				.select(Projections.constructor(MemberDto.class,
						member.username,
						member.age
				)).from(member)
				.fetch();
	}

	/*
		 Dto에 어노테이션을 다는 @QueryProjection도 있다.
		 컴파일러로 타입을 체크 할 수 있어 가장 안전하지만,
		 DTO에 QueryDSL 어노테이션을 유지해야 하는 점과
		 DTO까지 Q 파일을 생성해야하는 단점이 있음.
	 */


	// 동적 쿼리를 해결하는 두가지 방식
	// - BooleanBuilder
	// Where 다중 파라미터 사용

	@Test
	void 동적쿼리_BooleanBuilder() {
		String usernameparam = "member1";
		Integer ageParam = 10;

		List<Member> result = searchMember1(usernameparam, ageParam);
		Assertions.assertThat(result.size()).isEqualTo(1);
	}

	private List<Member> searchMember1(String usernameparam, Integer ageParam) {
		BooleanBuilder builder = new BooleanBuilder();
		if (usernameparam != null) {
			builder.and(member.username.eq(usernameparam));
		}
		if (ageParam != null) {
			builder.and(member.age.eq(ageParam));
		}

		return queryFactory
				.selectFrom(member)
				.where(builder)
				.fetch();
	}

	/*
		where 조건에 null 값은 무시
		메서드를 재활용 가능
		쿼리 가독성 높음
	 */
	@Test
	void 동적쿼리_WhereParam() {
		String usernameParam = "member1";
		Integer ageParam = 10;

		searhMember2(usernameParam, ageParam);
	}

	private List<Member> searhMember2(String usernameParam, Integer ageParam) {
		return queryFactory
				.selectFrom(member)
				.where(usernameEq(usernameParam), ageEq(ageParam))
				.fetch();

	}

	private BooleanExpression usernameEq(String usernameParam) {
		return usernameParam != null ? member.username.eq(usernameParam) : null;
	}

	private BooleanExpression ageEq(Integer ageParam) {
		return ageParam != null ? member.age.eq(ageParam) : null;
	}

	/*
		수정 삭제 벌크 연산

		주의: JPQL 배치와 마찬가지로, 영속성 컨텍스트에 있는 엔티티를 무시하고 실행되기 떄문에
		배치 쿼리를 실행하고 나면 영속성 컨텍스트를 초기화 하는 것이 안전
	 */

	@Test
	@DisplayName("쿼리 한번으로 대량 데이터 수정")
	void updateQuery() {
		queryFactory
				.update(member)
				.set(member.username, "비회원")
				.where(member.age.lt(28))
				.execute();
	}

	@Test
	@DisplayName("기존 숫자에 1 더하기")
	void updateAdd() {
		queryFactory
				.update(member)
				.set(member.age, member.age.add(1))
				.execute();
	}

	@Test
	void deleteQuery() {
		queryFactory
				.delete(member)
				.where(member.age.gt(18))
				.execute();
	}
}
