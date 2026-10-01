package vn.edu.hcmute.bookstore.repository;
import vn.edu.hcmute.bookstore.model.Book_24110171;
import java.util.List;
public interface BookRepository_24110171 {
    List<Book_24110171> findAll(int page, int size);
    long count();
    Book_24110171 findById(Integer id);
    Book_24110171 save(Book_24110171 book);
    Book_24110171 update(Book_24110171 book);
    void delete(Integer id);
}
