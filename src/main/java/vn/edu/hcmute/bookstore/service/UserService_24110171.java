package vn.edu.hcmute.bookstore.service;

import vn.edu.hcmute.bookstore.model.User_24110171;
import vn.edu.hcmute.bookstore.repository.UserRepository_24110171;
import vn.edu.hcmute.bookstore.repository.impl.UserRepositoryImpl_24110171;
import vn.edu.hcmute.bookstore.util.PasswordUtil_24110171;
import java.time.LocalDateTime;

public class UserService_24110171 {
    private final UserRepository_24110171 repo = new UserRepositoryImpl_24110171();

    public boolean emailExists(String email){ return repo.findByEmail(email).isPresent(); }
    public User_24110171 createVerifiedUser(String email,String fullname,Integer phone,String passwordHash){
        User_24110171 u=new User_24110171();
        u.setEmail(email);u.setFullname(fullname);u.setPhone(phone);u.setPasswd(passwordHash);
        u.setSignupDate(LocalDateTime.now());u.setAdmin(false);
        return repo.save(u);
    }
    public User_24110171 login(String email,String rawPassword){
        return repo.findByEmail(email).filter(u->u.getPasswd().equalsIgnoreCase(PasswordUtil_24110171.md5(rawPassword)))
                .map(u->{u.setLastLogin(LocalDateTime.now());return repo.update(u);}).orElse(null);
    }
    public User_24110171 findById(Integer id){return repo.findById(id);}
}
