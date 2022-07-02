package com.study.jpa.repository;

import com.study.jpa.entity.Member;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * 사용자 정의 인터페이스 구현
 * 규칙: 리포지토리 인터페이스(or 사용자 정의 인터페이스[권장] )+ Impl
 * 스프링 데이터 JPA가 인식해서 스프링 빈으로 등록
 *
 * 실무에서는 주로 QueryDSL이나 SpringJdbcTemplate을 함께 사용할 때 사용자 정의 리포지토리 기능 자주 사용
 *
 * 참고:
 * 항상 사용자 정의 리포지토리가 필요한 것은 아니다.
 * 그냥 임의의 리포지토리를 만들어도 된다. 예를들어 MemberQueryRepository를 인터페이스가 아닌 클래스로 만들고
 * 스프링 빈으로 등록해서 그냥 직접 사용해도 된다. 물론 이 경우 스프링 데이터 JPA와는 아무런 관계 없이 별도로 동작한다.
 */
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

	private final EntityManager em;

	@Override
	public List<Member> findMemberCustom() {
		return em.createQuery("select m from Member m") .getResultList();
	}
}
