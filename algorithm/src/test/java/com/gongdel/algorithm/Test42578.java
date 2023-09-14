package com.gongdel.algorithm;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

// https://school.programmers.co.kr/learn/courses/30/lessons/42578
public class Test42578 {
	public int solution(String[][] clothes) {
		Map<String, Integer> clothAndCounts = new HashMap<>();
		for (int i = 0; i < clothes.length; i++) {
			String clothe = clothes[i][1];
			clothAndCounts.put(clothe, clothAndCounts.getOrDefault(clothe, 1) + 1);
		}

		int answer = 1;
		for (Map.Entry<String, Integer> entry : clothAndCounts.entrySet()) {
			answer *= entry.getValue();

		}
		return answer - 1;
	}

	@Test
	void name() {
		int solution = solution(new String[][]{{"yellow_hat", "headgear"}, {"blue_sunglasses", "eyewear"}, {
				"green_turban", "headgear"}});
		Assertions.assertThat(solution).isEqualTo(5);
	}
}
