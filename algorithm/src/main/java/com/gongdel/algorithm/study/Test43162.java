package com.gongdel.algorithm.study;

public class Test43162 {

	private boolean[] checked;
	private int[][] ref;

	public int solution(int n, int[][] computers) {
		checked = new boolean[n];
		ref = computers;
		int answer = 0;

		for (int i = 0; i < n; i++) {
			if (!checked[i]) {
				answer++;
				dfs(i);
			}

		}

		return answer;
	}

	private void dfs(int i) {
		if (checked[i]) return;
		checked[i] = true;
		for (int j = 0; j < ref[i].length; j++) {
			if (ref[i][j] == 1) {
				dfs(j);
			}
		}
	}

	public static void main(String[] args) {
		Test43162 test43162 = new Test43162();
		int answer = test43162.solution(3, new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}});
		System.out.println(answer);
	}
}
