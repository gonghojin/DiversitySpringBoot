package com.gongdel.algorithm.april;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Test0409_1 {

	public int solution(String begin, String target, String[] words) {
		if (!Arrays.asList(words).contains(target)) return 0;

		Queue<WordStep> queue = new LinkedList<>();
		boolean[] visited = new boolean[words.length];

		queue.add(new WordStep(begin, 0));

		while (!queue.isEmpty()) {
			WordStep current = queue.poll();

			if (current.word.equals(target)) {
				return current.steps;
			}

			for (int i = 0; i < words.length; i++) {
				if (!visited[i] && isAdjacent(current.word, words[i])) {
					visited[i] = true;
					queue.add(new WordStep(words[i], current.steps + 1));
				}
			}
		}
		return 0;
	}

	private boolean isAdjacent(String a, String b) {
		int diff = 0;
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) != b.charAt(i)) diff++;
		}
		return diff == 1;
	}

	private static class WordStep {
		String word;
		int steps;

		WordStep(String word, int steps) {
			this.word = word;
			this.steps = steps;
		}
	}

	@Test
	void test() {
		int solution = solution("hit", "cog", new String[]{"hot", "dot", "dog", "lot", "log", "cog"});
		Assertions.assertEquals(4, solution);
	}
}
