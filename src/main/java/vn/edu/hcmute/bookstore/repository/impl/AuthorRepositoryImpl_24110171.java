package vn.edu.hcmute.bookstore.repository.impl;

import jakarta.persistence.EntityManager;
import vn.edu.hcmute.bookstore.config.JPAUtil_24110171;
import vn.edu.hcmute.bookstore.model.Author_24110171;
import vn.edu.hcmute.bookstore.repository.AuthorRepository_24110171;
import java.util.List;

public class AuthorRepositoryImpl_24110171 implements AuthorRepository_24110171 {
    @Override public List<Author_24110171> findAll(int page,int size){
        EntityManager em=JPAUtil_24110171.createEntityManager();
        try{return em.createQuery("select a from Author_24110171 a order by a.authorId",Author_24110171.class)
                .setFirstResult((page-1)*size).setMaxResults(size).getResultList();}finally{em.close();}
    }
    @Override public List<Author_24110171> findAll(){
        EntityManager em=JPAUtil_24110171.createEntityManager();
        try{return em.createQuery("select a from Author_24110171 a order by a.authorName",Author_24110171.class).getResultList();}finally{em.close();}
    }
    @Override public long count(){ EntityManager em=JPAUtil_24110171.createEntityManager(); try{return em.createQuery("select count(a) from Author_24110171 a",Long.class).getSingleResult();}finally{em.close();}}
    @Override public Author_24110171 findById(Integer id){ EntityManager em=JPAUtil_24110171.createEntityManager(); try{return em.find(Author_24110171.class,id);}finally{em.close();}}
    @Override public Author_24110171 save(Author_24110171 a){ EntityManager em=JPAUtil_24110171.createEntityManager(); try{em.getTransaction().begin();em.persist(a);em.getTransaction().commit();return a;}catch(RuntimeException e){if(em.getTransaction().isActive())em.getTransaction().rollback();throw e;}finally{em.close();}}
    @Override public Author_24110171 update(Author_24110171 a){ EntityManager em=JPAUtil_24110171.createEntityManager(); try{em.getTransaction().begin();Author_24110171 x=em.merge(a);em.getTransaction().commit();return x;}catch(RuntimeException e){if(em.getTransaction().isActive())em.getTransaction().rollback();throw e;}finally{em.close();}}
    @Override public void delete(Integer id){ EntityManager em=JPAUtil_24110171.createEntityManager(); try{em.getTransaction().begin();Author_24110171 a=em.find(Author_24110171.class,id);if(a!=null)em.remove(a);em.getTransaction().commit();}catch(RuntimeException e){if(em.getTransaction().isActive())em.getTransaction().rollback();throw e;}finally{em.close();}}
}
