package com.gongdel.dsl.repository;

import com.gongdel.dsl.dto.MemberSearchCondition;
import com.gongdel.dsl.dto.MemberTeamDto;
import com.gongdel.dsl.dto.QMemberTeamDto;
import com.gongdel.dsl.entity.QMember;
import com.gongdel.dsl.entity.QTeam;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

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
}
