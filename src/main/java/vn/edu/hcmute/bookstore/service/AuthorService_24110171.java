package vn.edu.hcmute.bookstore.service;
import vn.edu.hcmute.bookstore.model.Author_24110171;
import vn.edu.hcmute.bookstore.repository.AuthorRepository_24110171;
import vn.edu.hcmute.bookstore.repository.impl.AuthorRepositoryImpl_24110171;
import java.util.List;
public class AuthorService_24110171 {
    private final AuthorRepository_24110171 repo=new AuthorRepositoryImpl_24110171();
    public List<Author_24110171> page(int page,int size){return repo.findAll(Math.max(1,page),size);}
    public List<Author_24110171> all(){return repo.findAll();}
    public long count(){return repo.count();}
    public int totalPages(int size){return (int)Math.max(1,Math.ceil(count()/(double)size));}
    public Author_24110171 find(Integer id){return repo.findById(id);}
    public Author_24110171 save(Author_24110171 a){return repo.save(a);}
    public Author_24110171 update(Author_24110171 a){return repo.update(a);}
    public void delete(Integer id){repo.delete(id);}
}
