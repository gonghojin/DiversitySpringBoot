package com.gongdel.algorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Test42579 {

	class Music {
		int idx;
		int cnt;

		public Music(int idx, int cnt) {
			this.idx = idx;
			this.cnt = cnt;
		}
	}

	public int[] solution(String[] genres, int[] plays) {
		Map<String, List<Music>> maps = new LinkedHashMap<>();
		for (int i = 0; i < genres.length; i++) {
			String genre = genres[i];
			int play = plays[i];
			List<Music> orDefault = maps.getOrDefault(genre, new ArrayList<>());
			orDefault.add(new Music(i, play));
			maps.put(genre, orDefault);
		}

		List<Map.Entry<String, List<Music>>> list
				= new ArrayList<Map.Entry<String, List<Music>>>(
				maps.entrySet());

		Collections.sort(
				list,
				new Comparator<Map.Entry<String, List<Music>>>() {
					public int compare(
							Map.Entry<String, List<Music>> entry1,
							Map.Entry<String, List<Music>> entry2) {

						return -Integer.compare(sum(entry1),
								sum(entry2));
					}
				});


		ArrayList<Integer> resultList = new ArrayList<>();
		for (Map.Entry<String, List<Music>> stringListEntry : list) {
			List<Music> value = stringListEntry.getValue();
			Collections.sort(value, (o1, o2) -> Integer.compare(o2.cnt, o1.cnt));
			for (int i = 0; i < value.size(); i++) {
				Music music = value.get(i);
				resultList.add(music.idx);
				if (i == 1) break;


			}
		}
		return resultList.stream().mapToInt(Integer::intValue).toArray();
	}
	private Integer sum(Map.Entry<String, List<Music>> entry1) {
		return entry1.getValue().stream().mapToInt(value -> value.cnt).sum();
	}

	@Test
	void name() {
		int[] solution = solution(new String[]{"classic", "pop", "classic", "classic", "pop"}, new int[]{500, 600, 150
				, 800, 2500});
		Assertions.assertArrayEquals(new int[]{4, 1, 3, 0}, solution);
	}
}
