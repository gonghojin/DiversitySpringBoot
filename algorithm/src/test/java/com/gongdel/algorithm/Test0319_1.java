package com.gongdel.algorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Test0319_1 {
	@Test
	void test() {
		int result = solution(9, new int[][]{
				{1, 3}, {2, 3}, {3, 4}, {4, 5}, {4, 6}, {4, 7}, {7, 8}, {7, 9}
		});
		Assertions.assertEquals(1, result); // 원래 1이 정답이어야 함.
	}

	public int solution(int n, int[][] wires) {
		Map<Integer, List<Integer>> graph = new HashMap<>();
		for (int i = 1; i <= n; i++) {
			graph.put(i, new ArrayList<>());
		}

		for (int[] wire : wires) {
			int a = wire[0], b = wire[1];
			graph.get(a).add(b);
			graph.get(b).add(a);
		}

		int min = n;
		for (int[] wire : wires) {
			int a = wire[0], b = wire[1];

			// 간선 제거
			graph.get(a).remove(Integer.valueOf(b));
			graph.get(b).remove(Integer.valueOf(a));

			// BFS를 사용하여 한쪽 서브트리 크기 구하기
			int nodeCount = bfs(graph, a, n);
			int otherNodeCount = n - nodeCount;
			min = Math.min(min, Math.abs(nodeCount - otherNodeCount));

			// 간선 복구
			graph.get(a).add(b);
			graph.get(b).add(a);
		}
		return min;
	}

	private int bfs(Map<Integer, List<Integer>> graph, int start, int n) {
		Queue<Integer> queue = new LinkedList<>();
		Set<Integer> visited = new HashSet<>();
		queue.add(start);
		visited.add(start);
		int nodeCount = 0;

		while (!queue.isEmpty()) {
			int node = queue.poll();
			nodeCount++;

			for (int neighbor : graph.get(node)) {
				if (!visited.contains(neighbor)) {  // 방문 체크 후 추가
					visited.add(neighbor);
					queue.add(neighbor);
				}
			}
		}
		return nodeCount;
	}
}
