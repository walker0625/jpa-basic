package com.jpabasic;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Parent {
    @Id @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, // 부모만 저장해도 자식들도 같이 영속화(연관관계와는 영향이 없음) - 게시판(댓글, 첨부파일)에서 활용(단일 소유자 일때) // 부모를 지우면 자식도 함께 삭제됨
                                    orphanRemoval = true) // collection에서 삭제하면 Delete 쿼리가 나감 // 부모를 지우면 자식도 함께 삭제됨
    private List<Child> childList = new ArrayList<>();

    public void addChild(Child child) {
        childList.add(child);
        child.setParent(this);
    }

}
