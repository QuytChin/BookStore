package vn.edu.hcmute.bookstore.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RatingId_24110171 implements Serializable {
    @Column(name = "userid")
    private Integer userId;

    @Column(name = "bookid")
    private Integer bookId;

    public RatingId_24110171() {}
    public RatingId_24110171(Integer userId, Integer bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getBookId() { return bookId; }
    public void setBookId(Integer bookId) { this.bookId = bookId; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RatingId_24110171 that)) return false;
        return Objects.equals(userId, that.userId) && Objects.equals(bookId, that.bookId);
    }
    @Override public int hashCode() { return Objects.hash(userId, bookId); }
}
