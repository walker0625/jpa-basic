package com.jpabasic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        // Application 하나에 한개
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa"); // <persistence-unit name="jpa">

        // 트랜젝션 하나당 하나 생성(쓰레드 간 공유X)
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent1 = new Parent();
            parent1.addChild(child1);
            parent1.addChild(child2);

            em.persist(parent1); // child는 영속화하지 않아도 cascade 옵션으로 영속화 됨

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent1.getId());
            //findParent.getChildList().remove(0);
            em.remove(findParent); // 부모를 지우면 자식도 함께 삭제됨
            //List<Member> findMembers = em.createQuery("SELECT m FROM Member AS m", Member.class) // jpql > 객체를 대상으로 쿼리, sql > 테이블을 대상으로 쿼리
            //                             .setFirstResult(5).setMaxResults(8) // paging(mysql, oracle 방언에 맞게 sql을 만듬)
            //                             .getResultList();

            // 양방향 매핑
            // 읽기 전용이라 db에 변경이 적용되지는 않는다 > null
            // 하지만 영속성 컨텍스트에는 적용되므로 양방향의 경우 둘 다 넣어줘야 1차 캐시에서 조회 가능 & Test Code 활용 가능
            // Member의 setter에서 처리
            // toString(lombok 사용시 주의)/json 생성 라이브러리(controller 반환시 entity 지양(json 변환됨)) 생성시 무한루프 위험 주의 > 
            // teamA.getMembers().add(member);

            /* 
            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers();

            for (Member member1 : members) {
                System.out.println("memberName = " + member1.getName());
            }

            Team team = em.find(Team.class, teamA.getId());
            List<Member> members1 = team.getMembers();

            for (Member member1 : members1) {
                System.out.println("member1.getName() = " + member1.getName());
            }

            // 영속
            // (DB 저장 순간이 아님 - 쿼리가 동작하는 시점이 아님)
            // em.persist(member);

            // 준영속
            // em.detach(member);

            // 조회 > 스냅샷 생성
            // Member findMember = em.find(Member.class, 1L);

            // 갱신 > dirty checking(변경 감지)
            // findMember.setName("jeon");

            // 삭제
            // em.remove(1L);

            // em.flush(); // flush(쿼리가 동작하는 시점)

             */


            tx.commit(); // 변경 내용 확정(flush도 동작)
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
