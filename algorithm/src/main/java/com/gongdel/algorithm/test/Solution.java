package com.gongdel.algorithm.test;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

class Solution {

	class Print {
		int idx;
		int priority;

		public Print(int idx, int priority) {
			this.idx = idx;
			this.priority = priority;
		}
	}

	public int solution(int[] priorities, int location) {
		Queue<Print> que = new LinkedList<>();
		Queue<Integer> priorityQue = new PriorityQueue<>(Comparator.reverseOrder());

		for (int i = 0; i < priorities.length; i++) {
			int priority = priorities[i];
			que.offer(new Print(i, priority));
			priorityQue.add(priority);
		}

		int answer = 0;
		while (!que.isEmpty()) {
			Print print = que.poll();
			if (print.priority == priorityQue.peek()) {
				answer++;
				priorityQue.poll();
				if (print.idx == location) {
					break;
				}
			} else {
				que.offer(print);
			}
		}

		return answer;
	}

	public static void main(String[] args) {
		Solution solution = new Solution();
//		String ar1 = "hit";
//		String target = "cog";
		int[] ar1 = new int[]{2, 1, 3, 2};
		int[] ar2 = new int[]{1, 30, 5};


		//		String[] ar2 = new String[]{"eden", "kiki"};


		int solution1 = solution.solution(ar1, 2);
		System.out.println(solution1);
	}
}