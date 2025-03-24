package com.gongdel.algorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class Test0324_01 {

	private static final String[] VOWELS = {"A", "E", "I", "O", "U"};
	private final Map<String, Integer> dictionary = new HashMap<>();
	private int index = 1;

	public int solution(String word) {
		buildDictionary("");
		return dictionary.get(word);
	}

	private void buildDictionary(String current) {
		if (current.length() == 5) return;

		for (String vowel : VOWELS) {
			String next = current + vowel;
			dictionary.put(next, index++);
			buildDictionary(next);
		}
	}

	@Test
	void test() {
		int result = solution("AAAAE");
		Assertions.assertEquals(6, result);
	}
}
