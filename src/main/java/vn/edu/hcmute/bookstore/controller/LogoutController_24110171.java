package vn.edu.hcmute.bookstore.controller;
import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;
@WebServlet("/logout")
public class LogoutController_24110171 extends HttpServlet{protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws IOException{req.getSession().invalidate();resp.sendRedirect(req.getContextPath()+"/login");}}
