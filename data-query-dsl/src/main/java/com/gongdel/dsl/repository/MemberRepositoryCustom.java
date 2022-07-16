package com.gongdel.dsl.repository;

import com.gongdel.dsl.dto.MemberSearchCondition;
import com.gongdel.dsl.dto.MemberTeamDto;

import java.util.List;

public interface MemberRepositoryCustom {

	List<MemberTeamDto> search(MemberSearchCondition condition);

}
