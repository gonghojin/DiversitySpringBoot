package com.study.jpa.repository;

import com.study.jpa.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	EntityManager em;

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

	/*
		검색 조건: 나이가 10살
		정렬 조건: 이름으로 내림차순
		페이징 조건: 첫 번째 페이지, 페이지당 보여줄 데이터는 3건
	 */
	@Test
	void page() {
		// given
		memberRepository.save(new Member("member1", 10));
		memberRepository.save(new Member("member2", 10));
		memberRepository.save(new Member("member3", 10));
		memberRepository.save(new Member("member4", 10));
		memberRepository.save(new Member("member5", 10));

		// when
		PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
		Page<Member> page = memberRepository.findByAge(10, pageRequest);

		// then
		List<Member> content = page.getContent(); // 조회된 데이터
		assertThat(content.size()).isEqualTo(3); // 조회된 데이터 수
		assertThat(page.getTotalElements()).isEqualTo(5); // 전체 데이터 수
		assertThat(page.getNumber()).isEqualTo(0); // 페이지 번호
		assertThat(page.getTotalPages()).isEqualTo(2); //전체 페이지 번호
		assertThat(page.isFirst()).isTrue(); //첫번째 항목인가?
		assertThat(page.hasNext()).isTrue(); //다음 페이지가 있는가?
	}

	@Test
	void bulkAgePlus() {
		//given
		memberRepository.save(new Member("member1", 10));
		memberRepository.save(new Member("member2", 19));
		memberRepository.save(new Member("member3", 20));
		memberRepository.save(new Member("member4", 21));
		memberRepository.save(new Member("member5", 40));

		//when
		int resultCount = memberRepository.bulkAgePlus(20);

		//then
		assertThat(resultCount).isEqualTo(3);
	}

	@Test
	public void queryHint() throws Exception {
		//given
		memberRepository.save(new Member("member1", 10));
		em.flush();
		em.clear();
		//when
		Member member = memberRepository.findReadOnlyByUsername("member1");
		member.setUsername("member2");
		em.flush(); //Update Query 실행X
	}
}