package com.study.jpa.repository;

import com.study.jpa.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Test
	public void testMember() {
		Member member = new Member("memberA");
		Member savedMember = memberRepository.save(member);
		Member findMember = memberRepository.findById(savedMember.getId()).get();

		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
	}

	@Test
	public void testFindByUsernameAndAgeGreaterThan() {
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("AAA", 15);
		memberRepository.save(m1);
		memberRepository.save(m2);
		List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
		assertThat(result.get(0).getUsername()).isEqualTo("AAA");
		assertThat(result.get(0).getAge()).isEqualTo(15);
		assertThat(result.size()).isEqualTo(1);
	}

	@Test
	public void testFindByNames() {
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("BBB", 15);
		memberRepository.save(m1);
		memberRepository.save(m2);
		List<Member> result = memberRepository.findByNames(List.of("AAA", "BBB"));
		assertThat(result.size()).isEqualTo(2);
	}

	@Test
	void testFindByUsername() {
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("AAA", 15);
		memberRepository.save(m1);
		memberRepository.save(m2);

		List<Member> result = memberRepository.findByUsername("AAA");
		assertThat(result.size()).isEqualTo(2);
		assertThat(result.get(0).getAge()).isEqualTo(m1.getAge());
		assertThat(result.get(1).getAge()).isEqualTo(m2.getAge());

		Member m3 = new Member("CCC", 15);
		memberRepository.save(m3);
		Member member = memberRepository.findMemberByUsername("CCC");
		assertThat(member.getAge()).isEqualTo(m3.getAge());

		Optional<Member> ccc = memberRepository.findOptionalByUsername("CCC");
		assertThat(ccc.get().getAge()).isEqualTo(m3.getAge());

	}
}