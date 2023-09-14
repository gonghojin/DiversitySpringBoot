package com.gongdel.algorithm;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// https://school.programmers.co.kr/learn/courses/30/lessons/42577
public class Test42577 {
	private boolean solution(String[] phone_book) {
		boolean result = true;
		List<String> collect = Arrays.stream(phone_book).sorted().collect(Collectors.toList());
		for (int i = 0; i < collect.size() - 1; i++) {
			String current = collect.get(i);
			String next = collect.get(i + 1);
			if (next.startsWith(current)) {
				result = false;
				break;
			}
		}
		return result;
	}


	@Test
	void name() {
		boolean solution = solution(new String[]{"119", "97674223", "1195524421"});
		Assertions.assertThat(solution).isFalse();
	}
}
