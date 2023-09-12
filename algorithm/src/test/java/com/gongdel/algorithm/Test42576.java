package com.gongdel.algorithm;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

//https://school.programmers.co.kr/learn/courses/30/lessons/42576
class Test42576 {

	public String solution(String[] participant, String[] completion) {
		Map<String, Long> nameAndCounts = Arrays.stream(participant).collect(Collectors.groupingBy(Function.identity()
				, Collectors.counting()));
		for (String name : completion) {
			nameAndCounts.put(name, nameAndCounts.getOrDefault(name, 1L) - 1);
		}

		for (Map.Entry<String, Long> entry : nameAndCounts.entrySet()) {
			if (entry.getValue() > 0) {
				return entry.getKey();
			}

		}

		return "";
	}

	@Test
	void test() {
		String solution = solution(new String[]{"leo", "kiki", "eden"}, new String[]{"eden", "kiki"});
		Assertions.assertThat(solution).isEqualTo("leo");
	}
}
