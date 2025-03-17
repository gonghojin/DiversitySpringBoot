package com.gongdel.algorithm;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class Test0317_1 {
	@Test
	void test() {
		int[] solution = solution(10, 2);
		Assertions.assertThat(solution).isEqualTo(new int[]{4, 3});
	}

	public int[] solution(int brown, int yellow) {
		int[] answer = {};
		int total = brown + yellow;
		for (int height = 3; height <= total / height; height++) {
			if (total % height == 0) {
				int width = total / height;
				if (yellow == ((width - 2) * (height - 2))) {
					return new int[]{width, height};
				}
			}
		}
		return answer;
	}
}
