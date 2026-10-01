package vn.edu.hcmute.bookstore.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import vn.edu.hcmute.bookstore.config.JPAUtil_24110171;
import vn.edu.hcmute.bookstore.model.Book_24110171;
import vn.edu.hcmute.bookstore.model.CartItem_24110171;
import vn.edu.hcmute.bookstore.model.User_24110171;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Xử lý giỏ hàng: thêm, sửa số lượng, xóa và tính tổng.
 * Giới hạn số lượng luôn là 1..số lượng tồn kho của sách.
 */
public class CartService_24110171 {

    public List<CartItem_24110171> findByUser(Integer userId) {
        EntityManager em = JPAUtil_24110171.createEntityManager();
        try {
            return em.createQuery(
                            "select c from CartItem_24110171 c join fetch c.book where c.user.id=:uid order by c.id",
                            CartItem_24110171.class)
                    .setParameter("uid", userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void add(Integer userId, Integer bookId, int quantity) {
        if (quantity < 1) throw new IllegalArgumentException("Số lượng thêm phải từ 1 trở lên.");
        EntityManager em = JPAUtil_24110171.createEntityManager();
        try {
            em.getTransaction().begin();
            User_24110171 user = em.find(User_24110171.class, userId);
            Book_24110171 book = em.find(Book_24110171.class, bookId);
            if (user == null) throw new IllegalArgumentException("Không tìm thấy tài khoản.");
            if (book == null) throw new IllegalArgumentException("Không tìm thấy sách.");
            int stock = safeStock(book);
            if (stock <= 0) throw new IllegalArgumentException("Sách này hiện đã hết hàng.");

            CartItem_24110171 item = findOne(em, userId, bookId);
            int current = item == null ? 0 : item.getQuantity();
            int wanted = current + quantity;
            if (wanted > stock) {
                throw new IllegalArgumentException("Số lượng vượt tồn kho. Hiện còn " + stock + " cuốn.");
            }

            if (item == null) {
                item = new CartItem_24110171();
                item.setUser(user);
                item.setBook(book);
                item.setQuantity(wanted);
                item.setUpdatedAt(LocalDateTime.now());
                em.persist(item);
            } else {
                item.setQuantity(wanted);
                item.setUpdatedAt(LocalDateTime.now());
            }
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void update(Integer userId, Integer bookId, int quantity) {
        EntityManager em = JPAUtil_24110171.createEntityManager();
        try {
            em.getTransaction().begin();
            CartItem_24110171 item = findOne(em, userId, bookId);
            if (item == null) throw new IllegalArgumentException("Sản phẩm không còn trong giỏ hàng.");
            int stock = safeStock(item.getBook());
            if (quantity < 1 || quantity > stock) {
                throw new IllegalArgumentException("Số lượng hợp lệ từ 1 đến " + stock + ".");
            }
            item.setQuantity(quantity);
            item.setUpdatedAt(LocalDateTime.now());
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void remove(Integer userId, Integer bookId) {
        EntityManager em = JPAUtil_24110171.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("delete from CartItem_24110171 c where c.user.id=:uid and c.book.bookId=:bid")
                    .setParameter("uid", userId)
                    .setParameter("bid", bookId)
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public BigDecimal total(List<CartItem_24110171> items) {
        BigDecimal total = BigDecimal.ZERO;
        if (items == null) return total;
        for (CartItem_24110171 item : items) {
            BigDecimal price = item.getBook().getPrice() == null ? BigDecimal.ZERO : item.getBook().getPrice();
            total = total.add(price.multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return total;
    }

    public int totalQuantity(List<CartItem_24110171> items) {
        return items == null ? 0 : items.stream().mapToInt(i -> i.getQuantity() == null ? 0 : i.getQuantity()).sum();
    }

    private CartItem_24110171 findOne(EntityManager em, Integer userId, Integer bookId) {
        try {
            return em.createQuery(
                            "select c from CartItem_24110171 c join fetch c.book where c.user.id=:uid and c.book.bookId=:bid",
                            CartItem_24110171.class)
                    .setParameter("uid", userId)
                    .setParameter("bid", bookId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private int safeStock(Book_24110171 book) {
        return book.getQuantity() == null ? 0 : Math.max(0, book.getQuantity());
    }
}
