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

            List<Member> findMembers = em.createQuery("SELECT m FROM Member AS m", Member.class) // jpql > 객체를 대상으로 쿼리, sql > 테이블을 대상으로 쿼리
                                         .setFirstResult(5).setMaxResults(8) // paging(mysql, oracle 방언에 맞게 sql을 만듬)
                                         .getResultList();
            
            // 비영속
            // Member member = new Member();
            // member.setId(1);
            // member.setName("minwoo");

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
            em.flush(); // flush(쿼리가 동작하는 시점)
            tx.commit(); // 변경 내용 확정
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
