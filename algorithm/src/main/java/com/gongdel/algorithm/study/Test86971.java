package com.gongdel.algorithm.study;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// https://school.programmers.co.kr/learn/courses/30/lessons/86971
public class Test86971 {

	private List<Integer>[] lists;

	public int solution(int n, int[][] wires) {
		lists = new ArrayList[n + 1];
		for (int i = 0; i <= n; i++) {
			lists[i] = new ArrayList<>();
		}

		for (int[] wire : wires) {
			lists[wire[0]].add(wire[1]);
			lists[wire[1]].add(wire[0]);
		}

		int answer = 100;
		for (int[] wire : wires) {
			int cntA = bfs(wire[0], wire[1], n);
			int cntB = bfs(wire[1], wire[0], n);

			answer = Math.min(Math.abs(cntA - cntB), answer);
		}
		return answer;
	}

	private int bfs(int v1, int v2, int n) {
		Queue<Integer> q = new LinkedList<>();
		q.add(v1);

		boolean[] checked = new boolean[n + 1];
		int cnt = 0;
		while (!q.isEmpty()) {
			int current = q.poll();
			cnt++;
			checked[current] = true;
			for (Integer next : lists[current]) {
				if (next == v2 || checked[next]) continue;
				q.add(next);
			}
		}
		return cnt;
	}

	public static void main(String[] args) {
		Test86971 main = new Test86971();
		int[][] ints = {{1, 3}, {2, 3}, {3, 4}, {4, 5}, {4, 6}, {4, 7}, {7, 8}, {7, 9}};
		System.out.println(main.solution(9, ints));
	}
}
