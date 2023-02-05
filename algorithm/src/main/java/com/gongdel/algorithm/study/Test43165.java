package com.gongdel.algorithm.study;

// https://school.programmers.co.kr/learn/courses/30/lessons/43165
public class Test43165 {

	private int[] numArr;
	private int newTarget;

	public int solution(int[] numbers, int target) {
		numArr = numbers;
		newTarget = target;

		return dfs(0, 0);
	}

	public int dfs(int current, int idx) {
		if (idx == numArr.length) {
			if (current == newTarget) {
				return 1;
			} else {
				return 0;
			}
		}

		return dfs(current + numArr[idx], idx + 1) + dfs(current - numArr[idx], idx + 1);
	}

	public static void main(String[] args) {
		Test43165 target = new Test43165();
		System.out.println(target.solution(new int[]{1, 1, 1, 1, 1}, 3));
	}
}
