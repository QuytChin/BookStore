package vn.edu.hcmute.bookstore.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmute.bookstore.model.User_24110171;

import java.io.IOException;

/** Tiện ích dùng chung cho các chức năng chỉ dành cho người đã đăng nhập. */
public final class AuthUtil_24110171 {
    private AuthUtil_24110171() {}

    public static User_24110171 currentUser(HttpServletRequest req) {
        Object value = req.getSession().getAttribute("currentUser");
        return value instanceof User_24110171 ? (User_24110171) value : null;
    }

    public static User_24110171 requireUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User_24110171 user = currentUser(req);
        if (user == null) {
            req.getSession().setAttribute("flash", "Vui lòng đăng nhập để sử dụng giỏ hàng và đặt hàng.");
            resp.sendRedirect(req.getContextPath() + "/login");
        }
        return user;
    }
}
