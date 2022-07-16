package com.gongdel.dsl.repository;

import com.gongdel.dsl.dto.MemberSearchCondition;
import com.gongdel.dsl.dto.MemberTeamDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {

	List<MemberTeamDto> search(MemberSearchCondition condition);

	// 전체 카운트를 한번에 조회하는 단순한 방법
	Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable);

	// 데이터 내용과 전체 카운트를 별도로 조회하는 방법
	Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable);
}
