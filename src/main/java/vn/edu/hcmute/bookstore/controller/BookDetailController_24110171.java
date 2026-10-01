package vn.edu.hcmute.bookstore.controller;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;
import vn.edu.hcmute.bookstore.model.Book_24110171;import vn.edu.hcmute.bookstore.service.*;import vn.edu.hcmute.bookstore.util.WebUtil_24110171;
import java.io.IOException;
@WebServlet("/books/detail")
public class BookDetailController_24110171 extends HttpServlet{
    private final BookService_24110171 books=new BookService_24110171();private final RatingService_24110171 ratings=new RatingService_24110171();
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        int id=WebUtil_24110171.intParam(req,"id",0);Book_24110171 b=books.find(id);if(b==null){resp.sendError(404);return;}
        req.setAttribute("book",b);req.setAttribute("reviews",ratings.byBook(id));req.setAttribute("reviewCount",ratings.count(id));
        req.getRequestDispatcher("/WEB-INF/views/book-detail.jsp").forward(req,resp);
    }
}
