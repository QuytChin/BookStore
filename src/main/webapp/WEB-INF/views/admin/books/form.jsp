<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html><head><title>${empty book.bookId?'Thêm':'Sửa'} sách</title></head><body>
<div class="form-card admin-form-card">
    <div class="form-heading"><div><span class="badge">BOOK FORM</span><h1>${empty book.bookId?'Thêm sách mới':'Cập nhật sách'}</h1></div><a class="back-link" href="${pageContext.request.contextPath}/admin/books">← Quay lại</a></div>
    <form method="post" action="${pageContext.request.contextPath}/admin/books/save">
        <input type="hidden" name="id" value="${book.bookId}">
        <div class="form-grid">
            <label>ISBN<input name="isbn" type="number" value="${book.isbn}" placeholder="100015"></label>
            <label>Tiêu đề<input name="title" required value="${book.title}" placeholder="Tên sách"></label>
            <label>Publisher<input name="publisher" value="${book.publisher}" placeholder="NXB Văn Học"></label>
            <label>Price<input name="price" type="number" step="0.01" value="${book.price}" placeholder="75.00"></label>
            <label>Publish date<input name="publishDate" type="date" value="${book.publishDate}"></label>
            <label>Quantity<input name="quantity" type="number" min="0" value="${book.quantity}"></label>
            <label class="span2">Cover image<input name="coverImage" value="${book.coverImage}" placeholder="assets/img/book-01.png"><small>Nhập đường dẫn ảnh trong thư mục webapp, ví dụ assets/img/book-01.png</small></label>
            <label class="span2">Description<textarea name="description" rows="5" placeholder="Mô tả ngắn về nội dung cuốn sách"><c:out value="${book.description}"/></textarea></label>
            <label class="span2">Tác giả<select name="authorIds" multiple size="7"><c:forEach var="a" items="${authors}"><option value="${a.authorId}" <c:if test="${book.authors.contains(a)}">selected</c:if>><c:out value="${a.authorName}"/></option></c:forEach></select><small>Giữ Ctrl để chọn nhiều tác giả.</small></label>
        </div>
        <div class="form-actions"><button type="submit">Lưu thông tin</button><a class="button secondary" href="${pageContext.request.contextPath}/admin/books">Hủy</a></div>
    </form>
</div>
</body></html>
