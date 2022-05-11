package com.jpabasic;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
// allocationSize : DB 호출 횟수를 줄이기 위해서 미리 불러와서 Memory 사용
// (서버를 내리면 미리 가져온 메모리 값은 날아가지만 빈 공간이 큰 문제는 x, 여러서버가 동시에 호출해도 각각 다른 범위를 가져가기 때문에 동시성 문제x)
@SequenceGenerator(name="member_seq_generator", sequenceName = "member_seq", initialValue = 1, allocationSize = 50)
//@Table(name="USER") > db 테이블명 매핑 가능
public class Member {

    // Long + 대체키(ai, seq, uuid 등)를 식별자로 추천
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) // DB에 insert를 해야 id를 알 수 있으므로. persist할 때 미리 insert하고 그 값을 영속성 컨텍스트에서 사용(모아서 query가 어려움)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator") // sequence는 query전에 미리 확인 가능하므로(db에서 가져옴) 모아서 query가 가능(성능상 큰 차이는 x)
    private Long id;

    //@Column(name = "name")
    private String name;

    /*private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP) // db date 형태
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    
    private LocalDate localDataTest;

    private LocalDateTime localDateTimeTest;

    @Lob // text형
    private String description;*/

    public Member() {}

}