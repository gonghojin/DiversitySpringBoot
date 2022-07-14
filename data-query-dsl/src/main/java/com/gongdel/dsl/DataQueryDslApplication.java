package com.gongdel.dsl;

import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;

@SpringBootApplication
public class DataQueryDslApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataQueryDslApplication.class, args);
	}

	/*
		다음과 같이 스프링 빈으로 등록해서 주입받아 사용해도 된다.
		참고: 동시성 문제 X
		why? 이 시점에 스프링이 주입해주는 entityManager는 실제 동작 시점에 진짜 엔티티 매니저를
		찾아주는 프록시용 가짜 엔티티 매니저이다.
		가짜 엔티티 매니저는 실제 사용 시점에 트랜잭션 단위로 엔티티 매니저(영속성 컨텍스트)를 할당해준다.
	 */
	@Bean
	public JPQLQueryFactory jpqlQueryFactory(EntityManager em) {
		return new JPAQueryFactory(em);
	}
}
