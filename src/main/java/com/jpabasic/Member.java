package com.jpabasic;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
//@Table(name="USER") > db 테이블명 매핑 가능
public class Member {

    @Id
    private Long id;

    //@Column(name = "name")
    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP) // db date 형태
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    
    private LocalDate localDataTest;

    private LocalDateTime localDateTimeTest;

    @Lob // text형
    private String description;

    public Member() {}

}