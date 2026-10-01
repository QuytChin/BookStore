package vn.edu.hcmute.bookstore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;

/**
 * Trả ảnh bìa từ classpath /src/main/resources/covers.
 * Cách này tránh lỗi 404 ảnh khi Eclipse/Tomcat chưa publish thư mục assets tĩnh.
 */
@WebServlet("/cover")
public class CoverController_24110171 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = 1;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (Exception ignored) {
        }
        if (id < 1) id = 1;
        id = ((id - 1) % 14) + 1;

        String resource = String.format("covers/book-%02d.png", id);
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource)) {
            if (in == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            resp.setContentType("image/png");
            resp.setHeader("Cache-Control", "public, max-age=86400");
            in.transferTo(resp.getOutputStream());
        }
    }
}
