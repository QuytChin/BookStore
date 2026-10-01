package vn.edu.hcmute.bookstore.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem_24110171 implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order_24110171 order;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "bookid", nullable = false)
    private Book_24110171 book;

    // Lưu snapshot tên và giá tại thời điểm đặt hàng để lịch sử không bị đổi theo sản phẩm sau này.
    @Column(name = "book_title", nullable = false, columnDefinition = "nvarchar(200)")
    private String bookTitle;

    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "line_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal lineTotal;

    public OrderItem_24110171() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Order_24110171 getOrder() { return order; }
    public void setOrder(Order_24110171 order) { this.order = order; }
    public Book_24110171 getBook() { return book; }
    public void setBook(Book_24110171 book) { this.book = book; }
    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getLineTotal() { return lineTotal; }
    public void setLineTotal(BigDecimal lineTotal) { this.lineTotal = lineTotal; }
}
