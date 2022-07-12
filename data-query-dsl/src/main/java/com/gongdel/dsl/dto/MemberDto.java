package com.gongdel.dsl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MemberDto {

	private String username;
	private int age;

	public MemberDto() {
	}

	public MemberDto(String username, int age) {
		this.username = username;
		this.age = age;
	}
}
