package com.study.jpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
public class ItemTest {

	@PersistenceContext
	EntityManager em;

	@Test
	@Transactional
	void save() {

		Item item = new Item("1111");
		em.persist(item);

		em.flush();
	}
}
