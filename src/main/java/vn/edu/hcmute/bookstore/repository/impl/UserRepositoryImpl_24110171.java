package vn.edu.hcmute.bookstore.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import vn.edu.hcmute.bookstore.config.JPAUtil_24110171;
import vn.edu.hcmute.bookstore.model.User_24110171;
import vn.edu.hcmute.bookstore.repository.UserRepository_24110171;
import java.util.Optional;

public class UserRepositoryImpl_24110171 implements UserRepository_24110171 {
    @Override public Optional<User_24110171> findByEmail(String email) {
        EntityManager em = JPAUtil_24110171.createEntityManager();
        try {
            return Optional.of(em.createQuery("select u from User_24110171 u where lower(u.email)=lower(:email)", User_24110171.class)
                    .setParameter("email", email).getSingleResult());
        } catch (NoResultException e) { return Optional.empty(); }
        finally { em.close(); }
    }
    @Override public User_24110171 findById(Integer id) {
        EntityManager em = JPAUtil_24110171.createEntityManager();
        try { return em.find(User_24110171.class, id); } finally { em.close(); }
    }
    @Override public User_24110171 save(User_24110171 user) {
        EntityManager em = JPAUtil_24110171.createEntityManager();
        try { em.getTransaction().begin(); em.persist(user); em.getTransaction().commit(); return user; }
        catch (RuntimeException e) { if (em.getTransaction().isActive()) em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }
    @Override public User_24110171 update(User_24110171 user) {
        EntityManager em = JPAUtil_24110171.createEntityManager();
        try { em.getTransaction().begin(); User_24110171 x=em.merge(user); em.getTransaction().commit(); return x; }
        catch (RuntimeException e) { if (em.getTransaction().isActive()) em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }
}
