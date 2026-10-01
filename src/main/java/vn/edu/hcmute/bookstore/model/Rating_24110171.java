package vn.edu.hcmute.bookstore.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rating")
public class Rating_24110171 implements Serializable {
    @EmbeddedId
    private RatingId_24110171 id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    @JoinColumn(name = "userid")
    private User_24110171 user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "bookid")
    private Book_24110171 book;

    @Column(name = "rating")
    private Byte rating;

    @Column(name = "review_text", columnDefinition = "text")
    private String reviewText;

    public Rating_24110171() {}

    public RatingId_24110171 getId() { return id; }
    public void setId(RatingId_24110171 id) { this.id = id; }
    public User_24110171 getUser() { return user; }
    public void setUser(User_24110171 user) { this.user = user; }
    public Book_24110171 getBook() { return book; }
    public void setBook(Book_24110171 book) { this.book = book; }
    public Byte getRating() { return rating; }
    public void setRating(Byte rating) { this.rating = rating; }
    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }
}
