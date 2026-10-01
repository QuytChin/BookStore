package vn.edu.hcmute.bookstore.controller;

import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;
import vn.edu.hcmute.bookstore.model.PendingRegistration_24110171;import vn.edu.hcmute.bookstore.service.*;import vn.edu.hcmute.bookstore.util.*;
import java.io.IOException;import java.time.LocalDateTime;
@WebServlet("/register")
public class RegisterController_24110171 extends HttpServlet{
    private final UserService_24110171 users=new UserService_24110171();private final MailService_24110171 mail=new MailService_24110171();
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req,resp);}
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        req.setCharacterEncoding("UTF-8");String email=WebUtil_24110171.trim(req.getParameter("email"));String name=WebUtil_24110171.trim(req.getParameter("fullname"));String pw=req.getParameter("password");
        String p=req.getParameter("phone");Integer phone=null;try{phone=(p==null||p.isBlank())?null:Integer.valueOf(p);}catch(Exception ignored){}
        if(email.isBlank()||name.isBlank()||pw==null||pw.length()<6){req.setAttribute("error","Vui lòng nhập đủ thông tin, mật khẩu từ 6 ký tự.");doGet(req,resp);return;}
        if(users.emailExists(email)){req.setAttribute("error","Email đã tồn tại.");doGet(req,resp);return;}
        String otp=OtpUtil_24110171.generate6Digits();boolean sent=mail.sendOtp(email,otp);
        PendingRegistration_24110171 pending=new PendingRegistration_24110171(email,name,phone,PasswordUtil_24110171.md5(pw),otp,LocalDateTime.now().plusMinutes(5));
        req.getSession().setAttribute("pendingRegistration",pending);req.getSession().setAttribute("mailSent",sent);
        resp.sendRedirect(req.getContextPath()+"/verify-otp");
    }
}
