package vn.edu.hcmute.bookstore.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * Một dòng trong giỏ hàng của User.
 * Mỗi user chỉ có tối đa 1 dòng cho cùng một quyển sách.
 */
@Entity
@Table(name = "cart_items", uniqueConstraints =
        @UniqueConstraint(name = "UQ_cart_user_book", columnNames = {"user_id", "bookid"}))
public class CartItem_24110171 implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User_24110171 user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "bookid", nullable = false)
    private Book_24110171 book;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public CartItem_24110171() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public User_24110171 getUser() { return user; }
    public void setUser(User_24110171 user) { this.user = user; }
    public Book_24110171 getBook() { return book; }
    public void setBook(Book_24110171 book) { this.book = book; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public BigDecimal getLineTotal() {
        BigDecimal price = book == null || book.getPrice() == null ? BigDecimal.ZERO : book.getPrice();
        int qty = quantity == null ? 0 : quantity;
        return price.multiply(BigDecimal.valueOf(qty));
    }
}
