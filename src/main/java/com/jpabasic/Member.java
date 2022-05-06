package com.jpabasic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//@Table(name="USER") > db 테이블명 매핑 가능
public class Member {

    @Id
    private Long id;
    
    //@Column(name="username") >db 컬럼명 매핑 가능
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
