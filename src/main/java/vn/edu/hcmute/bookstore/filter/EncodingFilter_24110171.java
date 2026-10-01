package vn.edu.hcmute.bookstore.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

/** Bắt buộc UTF-8 cho toàn bộ request/response để form và JSP không mất dấu tiếng Việt. */
@WebFilter("/*")
public class EncodingFilter_24110171 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }
}
