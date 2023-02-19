package com.gongdel.algorithm.study;

import java.util.ArrayList;
import java.util.List;

public class Test84512 {

	private String target;
	private List<String> arrayList = new ArrayList<>();

	public int solution(String word) {
		target = word;

		dfs("", 0);
		return arrayList.indexOf(word);
	}

	private void dfs(String text, int depth) {
		if (depth > 5) return;

		arrayList.add(text);
		for (int i = 0; i < 5; i++) {
			dfs(text + "AEIOUT".charAt(i), depth + 1);
		}

	}

	public static void main(String[] args) {
		Test84512 test84512 = new Test84512();
		int answer = test84512.solution("EIO");
		System.out.println(answer);
	}
}
