package vn.edu.hcmute.bookstore.controller;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import vn.edu.hcmute.bookstore.model.User_24110171;import vn.edu.hcmute.bookstore.service.UserService_24110171;
import java.io.IOException;
@WebServlet("/login")
public class LoginController_24110171 extends HttpServlet{
    private final UserService_24110171 users=new UserService_24110171();
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{Object f=req.getSession().getAttribute("flash");if(f!=null){req.setAttribute("message",f);req.getSession().removeAttribute("flash");}req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req,resp);}
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{User_24110171 u=users.login(req.getParameter("email"),req.getParameter("password"));if(u==null){req.setAttribute("error","Sai email hoặc mật khẩu.");doGet(req,resp);return;}req.getSession().setAttribute("currentUser",u);if(u.isAdmin())resp.sendRedirect(req.getContextPath()+"/admin/books");else resp.sendRedirect(req.getContextPath()+"/home");}
}
