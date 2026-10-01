package vn.edu.hcmute.bookstore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmute.bookstore.model.CartItem_24110171;
import vn.edu.hcmute.bookstore.model.User_24110171;
import vn.edu.hcmute.bookstore.service.CartService_24110171;
import vn.edu.hcmute.bookstore.service.OrderService_24110171;
import vn.edu.hcmute.bookstore.util.AuthUtil_24110171;

import java.io.IOException;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutController_24110171 extends HttpServlet {
    private final CartService_24110171 carts = new CartService_24110171();
    private final OrderService_24110171 orders = new OrderService_24110171();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User_24110171 user = AuthUtil_24110171.requireUser(req, resp);
        if (user == null) return;
        List<CartItem_24110171> items = carts.findByUser(user.getId());
        if (items.isEmpty()) {
            req.getSession().setAttribute("flashCartError", "Giỏ hàng đang trống, chưa thể thanh toán.");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }
        req.setAttribute("cartItems", items);
        req.setAttribute("cartTotal", carts.total(items));
        req.setAttribute("checkoutUser", user);
        req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User_24110171 user = AuthUtil_24110171.requireUser(req, resp);
        if (user == null) return;
        String receiverName = req.getParameter("receiverName");
        String receiverPhone = req.getParameter("receiverPhone");
        String shippingAddress = req.getParameter("shippingAddress");
        String note = req.getParameter("note");
        try {
            Integer orderId = orders.checkoutCod(user.getId(), receiverName, receiverPhone, shippingAddress, note);
            req.getSession().setAttribute("flashOrder", "Đặt hàng COD thành công. Mã đơn của bạn là #" + orderId + ".");
            resp.sendRedirect(req.getContextPath() + "/orders");
        } catch (IllegalArgumentException e) {
            req.setAttribute("checkoutError", e.getMessage());
            req.setAttribute("receiverName", receiverName);
            req.setAttribute("receiverPhone", receiverPhone);
            req.setAttribute("shippingAddress", shippingAddress);
            req.setAttribute("note", note);
            doGet(req, resp);
        } catch (RuntimeException e) {
            req.setAttribute("checkoutError", "Thanh toán chưa thành công. Vui lòng kiểm tra lại giỏ hàng và thử lại.");
            doGet(req, resp);
        }
    }
}
