package com.gongdel.demo.domain.beer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource // Spring Data REST dependency가 추가되면 없어도 기본 설정으로 REST API가 만들어진다.
public interface BeerRepository extends JpaRepository<Beer,  Long> {
}
