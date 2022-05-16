package com.jpabasic;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Child {

    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name="parent_id")
    private Parent parent;

}
