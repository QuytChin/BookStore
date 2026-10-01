package vn.edu.hcmute.bookstore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmute.bookstore.model.Book_24110171;
import vn.edu.hcmute.bookstore.service.BookService_24110171;
import vn.edu.hcmute.bookstore.service.RatingService_24110171;
import vn.edu.hcmute.bookstore.util.WebUtil_24110171;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns={"/","/home","/products"})
public class HomeController_24110171 extends HttpServlet {
    private final BookService_24110171 books=new BookService_24110171();
    private final RatingService_24110171 ratings=new RatingService_24110171();
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        int size=6,page=Math.max(1,WebUtil_24110171.intParam(req,"page",1));
        int totalPages=books.totalPages(size);if(page>totalPages)page=totalPages;
        List<Book_24110171> data=books.page(page,size);Map<Integer,Long> reviewCounts=new LinkedHashMap<>();
        for(Book_24110171 b:data)reviewCounts.put(b.getBookId(),ratings.count(b.getBookId()));
        req.setAttribute("books",data);req.setAttribute("reviewCounts",reviewCounts);req.setAttribute("page",page);req.setAttribute("totalPages",totalPages);
        req.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(req,resp);
    }
}
