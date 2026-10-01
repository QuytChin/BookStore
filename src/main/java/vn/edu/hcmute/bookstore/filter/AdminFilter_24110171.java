package vn.edu.hcmute.bookstore.filter;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebFilter;import jakarta.servlet.http.*;import vn.edu.hcmute.bookstore.model.User_24110171;import java.io.IOException;
@WebFilter("/admin/*")
public class AdminFilter_24110171 implements Filter{
    public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain)throws IOException,ServletException{HttpServletRequest req=(HttpServletRequest)request;HttpServletResponse resp=(HttpServletResponse)response;User_24110171 u=(User_24110171)req.getSession().getAttribute("currentUser");if(u==null||!u.isAdmin()){req.getSession().setAttribute("flash","Bạn cần đăng nhập bằng tài khoản Admin.");resp.sendRedirect(req.getContextPath()+"/login");return;}chain.doFilter(request,response);}
}
