package vn.edu.hcmute.bookstore.service;

import vn.edu.hcmute.bookstore.model.Author_24110171;
import vn.edu.hcmute.bookstore.model.Book_24110171;
import vn.edu.hcmute.bookstore.repository.AuthorRepository_24110171;
import vn.edu.hcmute.bookstore.repository.BookRepository_24110171;
import vn.edu.hcmute.bookstore.repository.impl.AuthorRepositoryImpl_24110171;
import vn.edu.hcmute.bookstore.repository.impl.BookRepositoryImpl_24110171;
import java.util.LinkedHashSet;
import java.util.List;

public class BookService_24110171 {
    private final BookRepository_24110171 repo=new BookRepositoryImpl_24110171();
    private final AuthorRepository_24110171 authorRepo=new AuthorRepositoryImpl_24110171();
    public List<Book_24110171> page(int page,int size){return repo.findAll(Math.max(1,page),size);}
    public long count(){return repo.count();}
    public int totalPages(int size){return (int)Math.max(1,Math.ceil(count()/(double)size));}
    public Book_24110171 find(Integer id){return repo.findById(id);}
    public Book_24110171 save(Book_24110171 b,List<Integer> authorIds){b.setAuthors(resolveAuthors(authorIds));return repo.save(b);}
    public Book_24110171 update(Book_24110171 b,List<Integer> authorIds){b.setAuthors(resolveAuthors(authorIds));return repo.update(b);}
    public void delete(Integer id){repo.delete(id);}
    private LinkedHashSet<Author_24110171> resolveAuthors(List<Integer> ids){
        LinkedHashSet<Author_24110171> set=new LinkedHashSet<>();
        if(ids!=null) for(Integer id:ids){Author_24110171 a=authorRepo.findById(id);if(a!=null)set.add(a);} return set;
    }
}
