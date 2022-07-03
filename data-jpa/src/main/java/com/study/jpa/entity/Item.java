package com.study.jpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * data jpa 새로운 엔티티를 판단하는 기본 전략
 * 식별자가 객체일 때 : null일 경우
 * 식별자가 기본 타입일 때 : 0일 경우
 *
 * 아래처럼 JPA 식별자에 값을 할당할 경우, 새로운 엔티티로 인식을 하지 않음
 * SimpleJpaRepository#save를 보면 merge()가 호출되는 것을 볼 수 있음
 * merge()는 우선적으로 DB를 호출해서 값을 확인하고, 값이 없으면 새로운 엔티티로 인지하므로 매우 비효율적
 * 따라서 Persistable을 사용해서 새로운 엔티티 확인 여부를 직접 구현해서 새로운 엔티티 조건을 정의
 * 아래처럼  @CreatedDate에 값이 없으면 새로운 엔티티로 판단하는 방식으로 활용
 */
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item  implements Persistable<String> {

	@Id
	private String id;

	@CreatedDate
	private LocalDateTime createdDate;

	public Item(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean isNew() {
		return createdDate == null;
	}
}
