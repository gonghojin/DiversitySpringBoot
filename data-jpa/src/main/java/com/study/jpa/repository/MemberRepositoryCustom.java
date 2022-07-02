package com.study.jpa.repository;

import com.study.jpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
	List<Member> findMemberCustom();
}
