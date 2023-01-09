package com.gongdel.algorithm.study;

// https://school.programmers.co.kr/learn/courses/30/lessons/86491
public class Test86491 {

	public int solution(int[][] sizes) {
		int maxWidth = 0;
		int minWidth = 0;
		for (int[] size : sizes) {
			int max = Integer.max(size[0], size[1]);
			if (maxWidth < max) maxWidth = max;

			int min = Integer.min(size[0], size[1]);
			if (minWidth < min) minWidth = min;
		}

		return maxWidth * minWidth;
	}

	public static void main(String[] args) {
		Test86491 main = new Test86491();
		int[][] ints = {{60, 50}, {30, 70}, {60, 30}, {80, 40}};
		main.solution(ints);
	}
}
