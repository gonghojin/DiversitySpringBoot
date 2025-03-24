package com.gongdel.algorithm.study;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// https://school.programmers.co.kr/learn/courses/30/lessons/87694
public class Test87694 {

	public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
		boolean[][] maps = new boolean[102][102];
		for (int[] ints : rectangle) {
			for (int i = ints[0]; i <= ints[2] * 2; i++) {
				for (int j = ints[1]; j <=
						ints[3] * 2; j++) {
					maps[j][i] = true;
				}
			}
		}

		int answer = 0;
		return answer;
	}

	public static void main(String[] args) {
		Test87694 test87694 = new Test87694();
		String[] xxx = new String[]{"20", "30", "10", "50", "40"};
		test87694.solution(xxx);
	}

	private void solution(String[] args) {
		List<String> sorted = Arrays.stream(args).sorted().collect(Collectors.toList());
		int cnt = 0;
		for (int i = 0; i < sorted.size() / 2; i++) {
			Integer a = Integer.valueOf(sorted.get(i));
			Integer b = Integer.valueOf(sorted.get(sorted.size() - 1 - i));

			if (a < b) {
				cnt++;
			}
		}
		System.out.println(cnt);
	}
}
