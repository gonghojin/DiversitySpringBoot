package com.gongdel.redis;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class RedisBasicTest {
	@Autowired
	RedisTemplate<String, String> redisTemplate;

	@Test
	void redisConnectionTest() {
		final String key = "3";
		final String data = "21";

		final ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(key, data);

		final String s = valueOperations.get(key);
		Assertions.assertThat(s).isEqualTo(data);
	}
}
