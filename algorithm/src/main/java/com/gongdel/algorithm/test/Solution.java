package com.gongdel.algorithm.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Solution {

	class Process {

		int progress;
		int speed;

		public Process(int progress, int speed) {
			this.progress = progress;
			this.speed = speed;
		}
	}

	public int[] solution(int[] progresses, int[] speeds) {
		Queue<Process> que = new LinkedList<>();
		for (int i = 0; i < progresses.length; i++) {
			int progress = progresses[i];
			int speed = speeds[i];
			que.add(new Process(progress, speed));
		}

		ArrayList<Integer> list = new ArrayList<>();
		int day = 0;
		while (!que.isEmpty()) {
			day++;
			Process peek = que.peek();
			if (peek.progress + (day * peek.speed) >= 100) {
				int temp = 1;
				que.poll();
				while (!que.isEmpty()) {
					Process peek1 = que.peek();
					if (peek1.progress + (day * peek1.speed) >= 100) {
						temp++;
						que.poll();
					} else {
						break;
					}
				}
				list.add(temp);
			}
		}

		int[] answer = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			answer[i] = list.get(i);
		}
		return answer;
	}

	public static void main(String[] args) {
		Solution solution = new Solution();
//		String ar1 = "hit";
//		String target = "cog";
		int[] ar1 = new int[]{93, 30, 55};
		int[] ar2 = new int[]{1, 30, 5};


		//		String[] ar2 = new String[]{"eden", "kiki"};


		int[] solution1 = solution.solution(ar1, ar2);
		System.out.println(solution1);
	}
}