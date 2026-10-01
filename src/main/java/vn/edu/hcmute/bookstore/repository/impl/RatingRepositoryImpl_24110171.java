package vn.edu.hcmute.bookstore.repository.impl;

import jakarta.persistence.EntityManager;
import vn.edu.hcmute.bookstore.config.JPAUtil_24110171;
import vn.edu.hcmute.bookstore.model.Rating_24110171;
import vn.edu.hcmute.bookstore.model.RatingId_24110171;
import vn.edu.hcmute.bookstore.repository.RatingRepository_24110171;
import java.util.List;

public class RatingRepositoryImpl_24110171 implements RatingRepository_24110171 {
    @Override public List<Rating_24110171> findByBookId(Integer bookId){
        EntityManager em=JPAUtil_24110171.createEntityManager();
        try{return em.createQuery("select r from Rating_24110171 r join fetch r.user where r.id.bookId=:id order by r.id.userId",Rating_24110171.class).setParameter("id",bookId).getResultList();}
        finally{em.close();}
    }
    @Override public long countByBookId(Integer bookId){ EntityManager em=JPAUtil_24110171.createEntityManager(); try{return em.createQuery("select count(r) from Rating_24110171 r where r.id.bookId=:id",Long.class).setParameter("id",bookId).getSingleResult();}finally{em.close();}}
    @Override public boolean exists(Integer userId,Integer bookId){ EntityManager em=JPAUtil_24110171.createEntityManager(); try{return em.find(Rating_24110171.class,new RatingId_24110171(userId,bookId))!=null;}finally{em.close();}}
    @Override public Rating_24110171 save(Rating_24110171 r){ EntityManager em=JPAUtil_24110171.createEntityManager(); try{em.getTransaction().begin();em.persist(r);em.getTransaction().commit();return r;}catch(RuntimeException e){if(em.getTransaction().isActive())em.getTransaction().rollback();throw e;}finally{em.close();}}
}
