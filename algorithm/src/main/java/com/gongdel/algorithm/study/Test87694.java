package com.gongdel.algorithm.study;

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
		test87694.solution(new int[][]{{1, 1, 7, 4}, {3, 2, 5, 5}, {4, 3, 6, 9}, {2, 6, 8, 8}}, 1, 3, 7, 8);
	}
}
