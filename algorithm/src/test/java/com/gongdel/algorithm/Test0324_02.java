package com.gongdel.algorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Test0324_02 {
	private int answer;

	public int solution(int[] numbers, int target) {
		answer = 0;
		dfs(numbers, 0, 0, target);
		return answer;
	}

	private void dfs(int[] numbers, int index, int sum, int target) {
		if (index == numbers.length) {
			if (sum == target) {
				answer++;
			}
			return;
		}

		dfs(numbers, index + 1, sum + numbers[index], target);
		dfs(numbers, index + 1, sum - numbers[index], target);
	}

	@Test
	void test() {

		int result1 = solution(new int[]{4, 1, 2, 1}, 4);
		Assertions.assertEquals(2, result1);
	}
}
