package com.gongdel.demo.domain.beer;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
//@NoArgsConstructor
@Data
public class Beer {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Beer() {
    }

    public Beer(String name) {
        this.name = name;
    }


}
