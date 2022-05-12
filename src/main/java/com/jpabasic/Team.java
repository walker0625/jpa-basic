package com.jpabasic;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Team {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") // 연관관계의 주인은 Member의 team > mappedBy는 조회만 가능(변경은 불가)
    private List<Member> members = new ArrayList<Member>();
}
