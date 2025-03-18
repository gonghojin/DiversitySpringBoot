package com.gongdel.algorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Test0318_1 {
	@Test
	void test() {
		int result = solution(80, new int[][]{{80, 20}, {50, 40}, {30, 10}});
		Assertions.assertEquals(3, result);
	}

	private boolean[] checked;
	private int max = 0;

	public int solution(int k, int[][] dungeons) {
		checked = new boolean[dungeons.length];
		backtrack(k, dungeons, 0);

		return max;
	}

	/**
	 * @param k        남은 피로도
	 * @param dungeons 던전 리스트 (최소 필요 피로도, 소모 피로도)
	 * @param count    탐험한 던전 개수
	 */
	void backtrack(int k, int[][] dungeons, int count) {
		for (int i = 0; i < dungeons.length; i++) {
			if (checked[i] || k < dungeons[i][0]) {
				continue;
			}
			checked[i] = true;
			backtrack(k - dungeons[i][1], dungeons, count + 1);
			checked[i] = false;
		}
		max = Math.max(max, count);
	}
}
