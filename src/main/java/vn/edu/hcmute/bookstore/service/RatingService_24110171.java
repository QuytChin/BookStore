package vn.edu.hcmute.bookstore.service;

import vn.edu.hcmute.bookstore.model.Book_24110171;
import vn.edu.hcmute.bookstore.model.RatingId_24110171;
import vn.edu.hcmute.bookstore.model.Rating_24110171;
import vn.edu.hcmute.bookstore.model.User_24110171;
import vn.edu.hcmute.bookstore.repository.RatingRepository_24110171;
import vn.edu.hcmute.bookstore.repository.impl.RatingRepositoryImpl_24110171;

import java.util.List;

public class RatingService_24110171 {
    private final RatingRepository_24110171 repo = new RatingRepositoryImpl_24110171();

    public List<Rating_24110171> byBook(Integer bookId) {
        return repo.findByBookId(bookId);
    }

    public long count(Integer bookId) {
        return repo.countByBookId(bookId);
    }

    public boolean add(User_24110171 user, Book_24110171 book, int stars, String text) {
        if (user == null || book == null || repo.exists(user.getId(), book.getBookId())) {
            return false;
        }
        String cleanText = text == null ? "" : text.trim();
        if (cleanText.length() < 10 || cleanText.length() > 600) {
            return false;
        }

        Rating_24110171 rating = new Rating_24110171();
        rating.setId(new RatingId_24110171(user.getId(), book.getBookId()));
        rating.setUser(user);
        rating.setBook(book);
        rating.setRating((byte) Math.max(1, Math.min(5, stars)));
        rating.setReviewText(cleanText);
        repo.save(rating);
        return true;
    }
}
