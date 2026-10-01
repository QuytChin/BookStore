package vn.edu.hcmute.bookstore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmute.bookstore.model.OrderStatus_24110171;
import vn.edu.hcmute.bookstore.model.User_24110171;
import vn.edu.hcmute.bookstore.service.OrderService_24110171;
import vn.edu.hcmute.bookstore.util.AuthUtil_24110171;

import java.io.IOException;

@WebServlet("/orders")
public class OrderHistoryController_24110171 extends HttpServlet {
    private final OrderService_24110171 orders = new OrderService_24110171();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User_24110171 user = AuthUtil_24110171.requireUser(req, resp);
        if (user == null) return;
        OrderStatus_24110171 status = OrderStatus_24110171.fromCode(req.getParameter("status"));
        req.setAttribute("orders", orders.history(user.getId(), status));
        req.setAttribute("statuses", OrderStatus_24110171.values());
        req.setAttribute("selectedStatus", status);
        Object flash = req.getSession().getAttribute("flashOrder");
        if (flash != null) {
            req.setAttribute("orderMessage", flash);
            req.getSession().removeAttribute("flashOrder");
        }
        req.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(req, resp);
    }
}
