package vn.edu.hcmute.bookstore.repository.impl;

import jakarta.persistence.EntityManager;
import vn.edu.hcmute.bookstore.config.JPAUtil_24110171;
import vn.edu.hcmute.bookstore.model.Book_24110171;
import vn.edu.hcmute.bookstore.repository.BookRepository_24110171;

import java.util.List;

public class BookRepositoryImpl_24110171 implements BookRepository_24110171 {
    @Override
    public List<Book_24110171> findAll(int page, int size) {
        EntityManager em = JPAUtil_24110171.createEntityManager();
        try {
            // Không dùng DISTINCT vì SQL Server không cho DISTINCT trên cột TEXT (description).
            // Quan hệ authors đang EAGER nên vẫn lấy được tác giả sau khi EntityManager đóng.
            return em.createQuery("select b from Book_24110171 b order by b.bookId", Book_24110171.class)
                    .setFirstResult((page - 1) * size)
                    .setMaxResults(size)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = JPAUtil_24110171.createEntityManager();
        try {
            return em.createQuery("select count(b) from Book_24110171 b", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Book_24110171 findById(Integer id) {
        EntityManager em = JPAUtil_24110171.createEntityManager();
        try {
            return em.find(Book_24110171.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public Book_24110171 save(Book_24110171 book) {
        EntityManager em = JPAUtil_24110171.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
            return book;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Book_24110171 update(Book_24110171 book) {
        EntityManager em = JPAUtil_24110171.createEntityManager();
        try {
            em.getTransaction().begin();
            Book_24110171 updated = em.merge(book);
            em.getTransaction().commit();
            return updated;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = JPAUtil_24110171.createEntityManager();
        try {
            em.getTransaction().begin();
            Book_24110171 book = em.find(Book_24110171.class, id);
            if (book != null) {
                book.getAuthors().clear();
                em.remove(book);
            }
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
