package vn.edu.hcmute.bookstore.repository;
import vn.edu.hcmute.bookstore.model.Rating_24110171;
import java.util.List;
public interface RatingRepository_24110171 {
    List<Rating_24110171> findByBookId(Integer bookId);
    long countByBookId(Integer bookId);
    boolean exists(Integer userId, Integer bookId);
    Rating_24110171 save(Rating_24110171 rating);
}
