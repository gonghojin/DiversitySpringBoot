package com.study.jpa.controller;

import com.study.jpa.entity.Member;
import com.study.jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;


@RestController
@RequiredArgsConstructor
public class HelloController {

	private final MemberRepository memberRepository;

	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}

	@GetMapping("/members")
	public Page<Member> list(Pageable pageable) {
		Page<Member> page = memberRepository.findAll(pageable);
		return page;
	}


	@PostConstruct
	public void init() {
		for (int i = 0; i < 50; i++) {
			memberRepository.save(new Member("username" + i));
		}
	}
}