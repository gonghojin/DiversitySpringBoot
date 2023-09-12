package com.gongdel.algorithm;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

//https://school.programmers.co.kr/learn/courses/30/lessons/1845
class Test1845 {

	private int solution(int[] nums) {
		int possibleSize = nums.length / 2;

		int currentSize = Arrays.stream(nums).boxed().collect(Collectors.groupingBy(Function.identity(),
				Collectors.counting())).keySet().size();

		return Math.min(possibleSize, currentSize);
	}

	@Test
	void test() {
		int solution = solution(new int[]{3, 1, 2, 3});
		Assertions.assertThat(solution).isEqualTo(2);
	}
}
