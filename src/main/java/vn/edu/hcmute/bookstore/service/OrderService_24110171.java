package vn.edu.hcmute.bookstore.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import vn.edu.hcmute.bookstore.config.JPAUtil_24110171;
import vn.edu.hcmute.bookstore.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/** Xử lý thanh toán COD và lịch sử đơn hàng. */
public class OrderService_24110171 {

    /**
     * Tạo đơn COD trong một transaction: kiểm tra tồn kho -> trừ tồn -> tạo đơn -> xóa giỏ hàng.
     * Nếu có lỗi thì rollback toàn bộ để không bị trừ kho dang dở.
     */
    public Integer checkoutCod(Integer userId, String receiverName, String phone, String address, String note) {
        receiverName = clean(receiverName);
        phone = clean(phone);
        address = clean(address);
        note = clean(note);
        if (receiverName.isBlank()) throw new IllegalArgumentException("Vui lòng nhập tên người nhận.");
        if (phone.isBlank()) throw new IllegalArgumentException("Vui lòng nhập số điện thoại nhận hàng.");
        if (address.isBlank()) throw new IllegalArgumentException("Vui lòng nhập địa chỉ giao hàng.");
        if (receiverName.length() > 100) throw new IllegalArgumentException("Tên người nhận tối đa 100 ký tự.");
        if (phone.length() > 20) throw new IllegalArgumentException("Số điện thoại tối đa 20 ký tự.");
        if (address.length() > 255) throw new IllegalArgumentException("Địa chỉ tối đa 255 ký tự.");
        if (note.length() > 500) throw new IllegalArgumentException("Ghi chú tối đa 500 ký tự.");

        EntityManager em = JPAUtil_24110171.createEntityManager();
        try {
            em.getTransaction().begin();
            User_24110171 user = em.find(User_24110171.class, userId);
            if (user == null) throw new IllegalArgumentException("Không tìm thấy tài khoản.");

            List<CartItem_24110171> cart = em.createQuery(
                            "select c from CartItem_24110171 c where c.user.id=:uid order by c.id",
                            CartItem_24110171.class)
                    .setParameter("uid", userId)
                    .getResultList();
            if (cart.isEmpty()) throw new IllegalArgumentException("Giỏ hàng đang trống.");

            Order_24110171 order = new Order_24110171();
            order.setUser(user);
            order.setCreatedAt(LocalDateTime.now());
            order.setStatus(OrderStatus_24110171.NEW);
            order.setPaymentMethod("COD");
            order.setReceiverName(receiverName);
            order.setReceiverPhone(phone);
            order.setShippingAddress(address);
            order.setNote(note.isBlank() ? null : note);

            BigDecimal total = BigDecimal.ZERO;
            for (CartItem_24110171 cartItem : cart) {
                // Khóa dòng sách trong lúc thanh toán để tránh hai đơn cùng lấy vượt tồn kho.
                Integer bookId = cartItem.getBook().getBookId();
                Book_24110171 book = em.find(Book_24110171.class, bookId, LockModeType.PESSIMISTIC_WRITE);
                int stock = book.getQuantity() == null ? 0 : book.getQuantity();
                int qty = cartItem.getQuantity();
                if (qty < 1 || qty > stock) {
                    throw new IllegalArgumentException("Sách ‘" + book.getTitle() + "’ chỉ còn " + stock + " cuốn. Vui lòng cập nhật lại giỏ hàng.");
                }

                BigDecimal unitPrice = book.getPrice() == null ? BigDecimal.ZERO : book.getPrice();
                BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(qty));
                total = total.add(lineTotal);

                OrderItem_24110171 item = new OrderItem_24110171();
                item.setBook(book);
                item.setBookTitle(book.getTitle());
                item.setUnitPrice(unitPrice);
                item.setQuantity(qty);
                item.setLineTotal(lineTotal);
                order.addItem(item);

                book.setQuantity(stock - qty);
            }

            order.setTotalAmount(total);
            em.persist(order);
            for (CartItem_24110171 cartItem : cart) em.remove(cartItem);
            em.getTransaction().commit();
            return order.getOrderId();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /** Lọc theo trạng thái; status=null nghĩa là xem tất cả. */
    public List<Order_24110171> history(Integer userId, OrderStatus_24110171 status) {
        EntityManager em = JPAUtil_24110171.createEntityManager();
        try {
            String jpql = "select o from Order_24110171 o " +
                    "left join fetch o.items i left join fetch i.book " +
                    "where o.user.id=:uid " +
                    (status == null ? "" : "and o.status=:status ") +
                    "order by o.createdAt desc, o.orderId desc";
            var query = em.createQuery(jpql, Order_24110171.class).setParameter("uid", userId);
            if (status != null) query.setParameter("status", status);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    private String clean(String value) { return value == null ? "" : value.trim(); }
}
