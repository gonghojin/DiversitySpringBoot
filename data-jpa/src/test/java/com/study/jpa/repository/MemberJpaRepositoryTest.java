package com.study.jpa.repository;

import com.study.jpa.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

	@Autowired
	MemberJpaRepository memberJpaRepository;

	@Test
	void testMember() {
		Member member = new Member("memberA");
		Member savedMember = memberJpaRepository.save(member);

		Member findMember = memberJpaRepository.find(savedMember.getId());

		assertThat(findMember.getId()).isEqualTo(member.getId());
		assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		assertThat(findMember).isEqualTo(member); //JPA 엔티티 동일성 보장
	}

	@Test
	public void findByUsernameAndAgeGreaterThan() {
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("AAA", 20);
		memberJpaRepository.save(m1);
		memberJpaRepository.save(m2);
		List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
		assertThat(result.get(0).getUsername()).isEqualTo("AAA");
		assertThat(result.get(0).getAge()).isEqualTo(20);
		assertThat(result.size()).isEqualTo(1);
	}

	@Test
	public void paging() throws Exception {
		//given
		memberJpaRepository.save(new Member("member1", 10));
		memberJpaRepository.save(new Member("member2", 10));
		memberJpaRepository.save(new Member("member3", 10));
		memberJpaRepository.save(new Member("member4", 10));
		memberJpaRepository.save(new Member("member5", 10));
		int age = 10;
		int offset = 0;
		int limit = 3;

		//when
		List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
		long totalCount = memberJpaRepository.totalCount(age);

		//then
		assertThat(members.size()).isEqualTo(3);
		assertThat(totalCount).isEqualTo(5);
	}
}