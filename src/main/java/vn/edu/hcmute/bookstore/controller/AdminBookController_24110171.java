package vn.edu.hcmute.bookstore.controller;

import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;
import vn.edu.hcmute.bookstore.model.*;import vn.edu.hcmute.bookstore.service.*;import vn.edu.hcmute.bookstore.util.WebUtil_24110171;
import java.io.IOException;import java.math.BigDecimal;import java.time.LocalDate;import java.util.*;
@WebServlet("/admin/books/*")
public class AdminBookController_24110171 extends HttpServlet{
    private final BookService_24110171 books=new BookService_24110171();private final AuthorService_24110171 authors=new AuthorService_24110171();private static final int SIZE=6;
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        String path=req.getPathInfo()==null?"/":req.getPathInfo();
        if("/create".equals(path)){showForm(req,resp,new Book_24110171());return;}
        if("/edit".equals(path)){Book_24110171 b=books.find(WebUtil_24110171.intParam(req,"id",0));if(b==null){resp.sendError(404);return;}showForm(req,resp,b);return;}
        if("/delete".equals(path)){try{books.delete(WebUtil_24110171.intParam(req,"id",0));req.getSession().setAttribute("adminMsg","Đã xóa sách.");}catch(Exception e){req.getSession().setAttribute("adminMsg","Không xóa được sách vì còn dữ liệu liên quan.");}resp.sendRedirect(req.getContextPath()+"/admin/books");return;}
        int page=Math.max(1,WebUtil_24110171.intParam(req,"page",1));req.setAttribute("books",books.page(page,SIZE));req.setAttribute("page",page);req.setAttribute("totalPages",books.totalPages(SIZE));req.getRequestDispatcher("/WEB-INF/views/admin/books/list.jsp").forward(req,resp);
    }
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException{req.setCharacterEncoding("UTF-8");int id=WebUtil_24110171.intParam(req,"id",0);Book_24110171 b=id>0?books.find(id):new Book_24110171();if(b==null)b=new Book_24110171();b.setIsbn(intOrNull(req.getParameter("isbn")));b.setTitle(WebUtil_24110171.trim(req.getParameter("title")));b.setPublisher(WebUtil_24110171.trim(req.getParameter("publisher")));b.setPrice(decimal(req.getParameter("price")));b.setDescription(WebUtil_24110171.trim(req.getParameter("description")));b.setPublishDate(date(req.getParameter("publishDate")));b.setCoverImage(WebUtil_24110171.trim(req.getParameter("coverImage")));b.setQuantity(intOrNull(req.getParameter("quantity")));List<Integer> ids=new ArrayList<>();String[] a=req.getParameterValues("authorIds");if(a!=null)for(String s:a)try{ids.add(Integer.valueOf(s));}catch(Exception ignored){}if(id>0)books.update(b,ids);else books.save(b,ids);req.getSession().setAttribute("adminMsg",id>0?"Đã cập nhật sách.":"Đã thêm sách.");resp.sendRedirect(req.getContextPath()+"/admin/books");}
    private void showForm(HttpServletRequest req,HttpServletResponse resp,Book_24110171 b)throws ServletException,IOException{req.setAttribute("book",b);req.setAttribute("authors",authors.all());req.getRequestDispatcher("/WEB-INF/views/admin/books/form.jsp").forward(req,resp);}
    private Integer intOrNull(String s){try{return(s==null||s.isBlank())?null:Integer.valueOf(s);}catch(Exception e){return null;}}
    private BigDecimal decimal(String s){try{return(s==null||s.isBlank())?null:new BigDecimal(s);}catch(Exception e){return null;}}
    private LocalDate date(String s){try{return(s==null||s.isBlank())?null:LocalDate.parse(s);}catch(Exception e){return null;}}
}
