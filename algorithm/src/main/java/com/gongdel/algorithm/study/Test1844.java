package com.gongdel.algorithm.study;

import java.util.LinkedList;
import java.util.Queue;

// https://school.programmers.co.kr/learn/courses/30/lessons/1844
public class Test1844 {

	int[] nx = {0, 1, 0, -1};
	int[] ny = {1, 0, -1, 0};

	private static class Point {
		private int x;
		private int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public int solution(int[][] maps) {
		int answer = 0;

		int h = maps.length - 1;
		int w = maps[0].length - 1;
		boolean[][] checked = new boolean[h + 1][w + 1];

		Queue<Point> q = new LinkedList<>();
		q.add(new Point(0, 0));

		while (!q.isEmpty()) {
			Point point = q.poll();
			checked[point.x][point.y] = true;

			for (int i = 0; i < 4; i++) {
				int moveX = point.x + nx[i];
				int moveY = point.y + ny[i];

				if (0 <= moveX && moveX <= h && 0 <= moveY && moveY <= w) {
					if (maps[moveX][moveY] == 1 & !checked[moveX][moveY]) {
						maps[moveX][moveY] = maps[point.x][point.y] + 1;
						q.add(new Point(moveX, moveY));
					}
				}
			}
		}
		return maps[h][w] == 1 ? -1 : maps[h][w];
	}

	public static void main(String[] args) {
		Test1844 test1844 = new Test1844();
		int solution = test1844.solution(new int[][]{{1, 0, 1, 1, 1}, {1, 0, 1, 0, 1}, {1, 0, 1, 1, 1}, {1, 1, 1, 0,
				1}, {0, 0, 0, 0,
				1}});

		System.out.println(solution);
	}
}
