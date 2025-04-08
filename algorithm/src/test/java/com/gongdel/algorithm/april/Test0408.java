package com.gongdel.algorithm.april;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

public class Test0408 {
	int[] dx = {-1, 1, 0, 0}; // 행 변화: 위, 아래, 같은, 같은
	int[] dy = {0, 0, -1, 1}; // 열 변화: 같은, 같은, 왼쪽, 오른쪽

	public int solution(int[][] maps) {
		int x = maps.length;
		int y = maps[0].length;

		boolean[][] visited = new boolean[x][y];

		int shortestPath = bfs(maps, visited, 0, 0);
		return shortestPath;
	}

	private int bfs(int[][] maps, boolean[][] visited, int x, int y) {
		int xSize = maps.length;
		int ySize = maps[0].length;

		Queue<Position> queue = new LinkedList<>();
		queue.add(new Position(x, y, 1));

		while (!queue.isEmpty()) {
			Position position = queue.poll();

			if (position.x == xSize - 1 && position.y == ySize - 1) {
				return position.distance;
			}


			for (int i = 0; i < 4; i++) {
				int moveX = position.x + dx[i];
				int moveY = position.y + dy[i];
				// 범위 검사
				if (moveX >= 0 && moveY >= 0 && moveX < xSize && moveY < ySize) {
					// 갈 수 있는 곳이고 방문 안 했으면
					if (maps[moveX][moveY] == 1 && !visited[moveX][moveY]) {
						visited[moveX][moveY] = true;
						queue.add(new Position(moveX, moveY, position.distance + 1));
					}
				}
			}
		}

		return -1;
	}

	public class Position {
		int x;
		int y;
		int distance;

		public Position(int x, int y, int distance) {
			this.x = x;
			this.y = y;
			this.distance = distance;
		}
	}


	@Test
	void test() {
		int[][] map = new int[][]{
				{1, 0, 1, 1, 1},
				{1, 0, 1, 0, 1},
				{1, 0, 1, 1, 1},
				{1, 1, 1, 0, 1},
				{0, 0, 0, 0, 1}
		};
		int solution = solution(map);
		Assertions.assertEquals(solution, 11);
	}
}
