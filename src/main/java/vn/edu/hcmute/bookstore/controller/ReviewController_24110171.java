package vn.edu.hcmute.bookstore.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmute.bookstore.model.Book_24110171;
import vn.edu.hcmute.bookstore.model.User_24110171;
import vn.edu.hcmute.bookstore.service.BookService_24110171;
import vn.edu.hcmute.bookstore.service.RatingService_24110171;
import vn.edu.hcmute.bookstore.util.WebUtil_24110171;

import java.io.IOException;

@WebServlet("/review/add")
public class ReviewController_24110171 extends HttpServlet {
    private final BookService_24110171 books = new BookService_24110171();
    private final RatingService_24110171 ratings = new RatingService_24110171();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        User_24110171 user = (User_24110171) req.getSession().getAttribute("currentUser");
        int bookId = WebUtil_24110171.intParam(req, "bookId", 0);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Book_24110171 book = books.find(bookId);
        int stars = WebUtil_24110171.intParam(req, "rating", 5);
        String text = WebUtil_24110171.trim(req.getParameter("reviewText"));
        boolean ok = ratings.add(user, book, stars, text);

        req.getSession().setAttribute("flashReview",
                ok ? "Cảm ơn bạn! Review đã được ghi nhận."
                   : "Không thể thêm review. Nội dung cần từ 10-600 ký tự hoặc bạn đã review sách này.");
        resp.sendRedirect(req.getContextPath() + "/books/detail?id=" + bookId + "#reviews");
    }
}
