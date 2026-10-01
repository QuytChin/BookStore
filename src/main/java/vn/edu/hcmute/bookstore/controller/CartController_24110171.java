package vn.edu.hcmute.bookstore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmute.bookstore.model.CartItem_24110171;
import vn.edu.hcmute.bookstore.model.User_24110171;
import vn.edu.hcmute.bookstore.service.CartService_24110171;
import vn.edu.hcmute.bookstore.util.AuthUtil_24110171;
import vn.edu.hcmute.bookstore.util.WebUtil_24110171;

import java.io.IOException;
import java.util.List;

@WebServlet("/cart/*")
public class CartController_24110171 extends HttpServlet {
    private final CartService_24110171 carts = new CartService_24110171();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User_24110171 user = AuthUtil_24110171.requireUser(req, resp);
        if (user == null) return;
        List<CartItem_24110171> items = carts.findByUser(user.getId());
        req.setAttribute("cartItems", items);
        req.setAttribute("cartTotal", carts.total(items));
        req.setAttribute("cartQuantity", carts.totalQuantity(items));
        moveFlash(req, "flashCart", "cartMessage");
        moveFlash(req, "flashCartError", "cartError");
        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User_24110171 user = AuthUtil_24110171.requireUser(req, resp);
        if (user == null) return;
        String action = req.getPathInfo();
        int bookId = WebUtil_24110171.intParam(req, "bookId", 0);
        int quantity = WebUtil_24110171.intParam(req, "quantity", 1);
        try {
            if ("/add".equals(action)) {
                carts.add(user.getId(), bookId, quantity);
                req.getSession().setAttribute("flashCart", "Đã thêm sách vào giỏ hàng.");
            } else if ("/update".equals(action)) {
                carts.update(user.getId(), bookId, quantity);
                req.getSession().setAttribute("flashCart", "Đã cập nhật số lượng.");
            } else if ("/remove".equals(action)) {
                carts.remove(user.getId(), bookId);
                req.getSession().setAttribute("flashCart", "Đã xóa sản phẩm khỏi giỏ hàng.");
            } else {
                req.getSession().setAttribute("flashCartError", "Thao tác giỏ hàng không hợp lệ.");
            }
        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("flashCartError", e.getMessage());
        } catch (RuntimeException e) {
            req.getSession().setAttribute("flashCartError", "Không thể cập nhật giỏ hàng. Vui lòng thử lại.");
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    private void moveFlash(HttpServletRequest req, String sessionName, String requestName) {
        Object value = req.getSession().getAttribute(sessionName);
        if (value != null) {
            req.setAttribute(requestName, value);
            req.getSession().removeAttribute(sessionName);
        }
    }
}
