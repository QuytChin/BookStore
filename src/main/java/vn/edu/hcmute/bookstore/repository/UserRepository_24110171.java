package vn.edu.hcmute.bookstore.repository;
import vn.edu.hcmute.bookstore.model.User_24110171;
import java.util.Optional;
public interface UserRepository_24110171 {
    Optional<User_24110171> findByEmail(String email);
    User_24110171 findById(Integer id);
    User_24110171 save(User_24110171 user);
    User_24110171 update(User_24110171 user);
}
