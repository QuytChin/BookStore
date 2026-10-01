package vn.edu.hcmute.bookstore.controller;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;
import vn.edu.hcmute.bookstore.model.PendingRegistration_24110171;import vn.edu.hcmute.bookstore.service.UserService_24110171;
import java.io.IOException;import java.time.LocalDateTime;
@WebServlet("/verify-otp")
public class VerifyOtpController_24110171 extends HttpServlet{
    private final UserService_24110171 users=new UserService_24110171();
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{PendingRegistration_24110171 p=(PendingRegistration_24110171)req.getSession().getAttribute("pendingRegistration");if(p==null){resp.sendRedirect(req.getContextPath()+"/register");return;}req.setAttribute("pending",p);req.getRequestDispatcher("/WEB-INF/views/auth/verify-otp.jsp").forward(req,resp);}
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{PendingRegistration_24110171 p=(PendingRegistration_24110171)req.getSession().getAttribute("pendingRegistration");if(p==null){resp.sendRedirect(req.getContextPath()+"/register");return;}String otp=req.getParameter("otp");if(LocalDateTime.now().isAfter(p.getExpiredAt())){req.setAttribute("error","OTP đã hết hạn. Hãy đăng ký lại.");doGet(req,resp);return;}if(!p.getOtp().equals(otp)){req.setAttribute("error","OTP không đúng.");doGet(req,resp);return;}users.createVerifiedUser(p.getEmail(),p.getFullname(),p.getPhone(),p.getPasswordHash());req.getSession().removeAttribute("pendingRegistration");req.getSession().removeAttribute("mailSent");req.getSession().setAttribute("flash","Kích hoạt thành công. Hãy đăng nhập.");resp.sendRedirect(req.getContextPath()+"/login");}
}
