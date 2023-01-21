package com.gongdel.algorithm.study;

// https://school.programmers.co.kr/learn/courses/30/lessons/87946
public class Test87946 {

	private boolean[] checked;
	private int result;

	public int solution(int k, int[][] dungeons) {
		checked = new boolean[dungeons.length];

		dfs(k, dungeons, 0);
		return result;
	}

	private void dfs(int k, int[][] dungeons, int cnt) {
		for (int i = 0; i < dungeons.length; i++) {
			if (!checked[i] && dungeons[i][0] <= k) {
				checked[i] = true;
				dfs(k - dungeons[i][1], dungeons, cnt + 1);
				checked[i] = false;
			}
		}

		result = Math.max(result, cnt);
	}


	public static void main(String[] args) {
		Test87946 main = new Test87946();
		int[][] ints = {{80, 20}, {50, 40}, {30, 10}};
		main.solution(80, ints);
	}
}
