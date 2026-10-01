package vn.edu.hcmute.bookstore.repository;
import vn.edu.hcmute.bookstore.model.Author_24110171;
import java.util.List;
public interface AuthorRepository_24110171 {
    List<Author_24110171> findAll(int page, int size);
    List<Author_24110171> findAll();
    long count();
    Author_24110171 findById(Integer id);
    Author_24110171 save(Author_24110171 author);
    Author_24110171 update(Author_24110171 author);
    void delete(Integer id);
}
