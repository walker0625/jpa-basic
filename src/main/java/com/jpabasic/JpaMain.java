package com.jpabasic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        // Application 하나에 한개
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");

        // 트랜젝션 하나당 하나 생성(쓰레드 간 공유X)
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            List<Member> findMembers = em.createQuery("SELECT m FROM Member AS m", Member.class) // jpql > 객체를 대상으로 쿼리, sql > 테이블을 대상으로 쿼리
                                         .setFirstResult(5).setMaxResults(8) // paging(mysql, oracle 방언에 맞게 sql을 만듬)
                                         .getResultList();
            for (Member findMember : findMembers) {
                System.out.println("findMember.getName() = " + findMember.getName());
            }
            /*
            Member member = new Member();
            member.setId(1L);
            member.setName("minwoo");

            em.persist(member);

            Member findMember = em.find(Member.class, 1L);
            findMember.setName("jeon");
            em.remove(1L);
*/
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
