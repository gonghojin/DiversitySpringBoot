package com.gongdel.algorithm.april;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

public class Test0407 {

	public int solution(int n, int[][] computers) {
		boolean[] visited = new boolean[n];
		int answer = 0;

		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				bfs(computers, visited, i);
				answer++;
			}
		}

		return answer;
	}

	private void bfs(int[][] computers, boolean[] visited, int start) {
		Queue<Integer> queue = new LinkedList<>();
		queue.add(start);
		visited[start] = true;

		while (!queue.isEmpty()) {
			int current = queue.poll();

			for (int i = 0; i < computers.length; i++) {
				if (!visited[i] && computers[current][i] == 1) {
					visited[i] = true;
					queue.add(i);
				}
			}
		}
	}


	@Test
	void test() {
		int solution = solution(3, new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}});
		Assertions.assertEquals(2, solution);
	}


}
