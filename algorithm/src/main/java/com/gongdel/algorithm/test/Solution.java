package com.gongdel.algorithm.test;

import java.util.Arrays;
import java.util.stream.Collectors;

class Solution {

	public int solution(String[][] clothes) {
		return Arrays.stream(clothes)
				.collect(Collectors.groupingBy(arr -> arr[0], Collectors.mapping(arr -> arr[1],
						Collectors.counting())))
				.values()
				.stream()
				.collect(Collectors.reducing(1L, (x, y) -> x * (y + 1))).intValue() - 1;
	}

	public static void main(String[] args) {
		Solution solution = new Solution();
//		String ar1 = "hit";
//		String target = "cog";
		String[][] ar1 = new String[][]{{"yellow_hat", "headgear"}, {"blue_sunglasses", "eyewear"}, {"green_turban",
				"headgear"}};
//		String[] ar2 = new String[]{"eden", "kiki"};


		int solution1 = solution.solution(ar1);
		System.out.println(solution1);
	}
}