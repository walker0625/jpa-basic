package com.jpabasic;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
// allocationSize : DB 호출 횟수를 줄이기 위해서 미리 불러와서 Memory 사용
// (서버를 내리면 미리 가져온 메모리 값은 날아가지만 빈 공간이 큰 문제는 x, 여러서버가 동시에 호출해도 각각 다른 범위를 가져가기 때문에 동시성 문제x)
// @SequenceGenerator(name="member_seq_generator", sequenceName = "member_seq", initialValue = 1, allocationSize = 50)
// @Table(name="USER") > db 테이블명 매핑 가능
public class Member {

    // Long + 대체키(ai, seq, uuid 등)를 식별자로 추천
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에 insert를 해야 id를 알 수 있으므로. persist할 때 미리 insert하고 그 값을 영속성 컨텍스트에서 사용(모아서 query가 어려움)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator") // sequence는 query전에 미리 확인 가능하므로(db에서 가져옴) 모아서 query가 가능(성능상 큰 차이는 x)
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)  // MemberToTeam,
                                        // 실무에서는 LAZY가 강력하게 권장됨! > 일단 다 LAZY로 설정(ManoToOne, @OneToOne)하고 필요하면 fetch join
                                        // 1. EAGER로 하면 불필요한 조인(10개면?) 부하가 심할 수 있음 2. JPQL 시 N+1 문제 발생
    @JoinColumn(name = "TEAM_ID") // 외래키가 생성됨
    private Team team; // 연관관계의 주인은 Member(외래키가 있는 있는 객체(N : 다)를 주인으로 삼는 것이 권장됨! > 1. 결국 바뀌는 것은 외래키의 값이므로 2. 성능 이슈)

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this); // 양방향 처리
    }

    /*
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
    private String description;*/
}