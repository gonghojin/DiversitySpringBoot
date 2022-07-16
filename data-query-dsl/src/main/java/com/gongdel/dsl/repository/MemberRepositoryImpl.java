package com.gongdel.dsl.repository;

import com.gongdel.dsl.dto.MemberSearchCondition;
import com.gongdel.dsl.dto.MemberTeamDto;
import com.gongdel.dsl.dto.QMemberTeamDto;
import com.gongdel.dsl.entity.Member;
import com.gongdel.dsl.entity.QMember;
import com.gongdel.dsl.entity.QTeam;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.gongdel.dsl.entity.QMember.*;
import static com.gongdel.dsl.entity.QTeam.*;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	//회원명, 팀명, 나이(ageGoe, ageLoe)
	@Override
	public List<MemberTeamDto> search(MemberSearchCondition condition) {
		return queryFactory
				.select(new QMemberTeamDto(
						member.id,
						member.username,
						member.age,
						team.id,
						team.name
				))
				.from(member)
				.leftJoin(member.team, team)
				.where(usernameEq(condition.getUsername()),
						teamNameEq(condition.getTeamName()), ageGoe(condition.getAgeGoe()),
						ageLoe(condition.getAgeLoe()))
				.fetch();
	}

	private BooleanExpression usernameEq(String username) {
		return isEmpty(username) ? null : member.username.eq(username);
	}

	private BooleanExpression teamNameEq(String teamName) {
		return isEmpty(teamName) ? null : team.name.eq(teamName);
	}

	private BooleanExpression ageGoe(Integer ageGoe) {
		return ageGoe == null ? null : member.age.goe(ageGoe);
	}

	private BooleanExpression ageLoe(Integer ageLoe) {
		return ageLoe == null ? null : member.age.loe(ageLoe);
	}

	/*
		단순한 페이징, fetchResult() 사용
	 */
	@Override
	public Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable) {
		QueryResults<MemberTeamDto> results = queryFactory
				.select(new QMemberTeamDto(
						member.id,
						member.username,
						member.age,
						team.id,
						team.name
				))
				.from(member)
				.leftJoin(member.team, team)
				.where(usernameEq(condition.getUsername()),
						teamNameEq(condition.getTeamName()),
						ageGoe(condition.getAgeGoe()),
						ageLoe(condition.getAgeLoe()))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();

		List<MemberTeamDto> content = results.getResults();
		long total = results.getTotal();

		return new PageImpl<>(content, pageable, total);
	}

	/*
		전체 커운트를 조회할 떄, join 쿼리를 줄일 수 있다면 상당한 효과가 있음
	 */
	@Override
	public Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable) {
		List<MemberTeamDto> content = queryFactory
				.select(new QMemberTeamDto(
						member.id,
						member.username,
						member.age,
						team.id,
						team.name
				))
				.from(member)
				.leftJoin(member.team, team)
				.where(usernameEq(condition.getUsername()),
						teamNameEq(condition.getTeamName()),
						ageGoe(condition.getAgeGoe()),
						ageLoe(condition.getAgeLoe()))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();

		JPAQuery<Member> countQuery = queryFactory
				.select(member)
				.from(member)
				.leftJoin(member.team, team)
				.where(usernameEq(condition.getUsername()),
						teamNameEq(condition.getTeamName()),
						ageGoe(condition.getAgeGoe()),
						ageLoe(condition.getAgeLoe()));

//		return new PageImpl<>(content, pageable, total);

		/*
			count 쿼리가 생략 가능한 경우, 생략해서 처리
			: 페이지 시작이면서 컨텐츠 사이즈가 페이즈 사이즈보다 작을 때
			: 마지막 페이지일떄 (offset + 컨텐츠 사이즈를 더해서 전체 사이즈를 구함)
		 */
		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
	}
}


