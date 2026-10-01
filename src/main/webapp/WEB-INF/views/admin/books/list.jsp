<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html><head><title>Quản lý Books</title></head><body>
<div class="toolbar admin-toolbar">
    <div><span class="badge">CÂU 6 • ADMIN</span><h1>Quản lý Books</h1><p>CRUD sách có phân trang 6 bản ghi / trang.</p></div>
    <a class="button" href="${pageContext.request.contextPath}/admin/books/create">＋ Thêm sách</a>
</div>
<div class="table-wrap">
<table class="admin-table books-table">
    <thead><tr><th>Bìa</th><th>ID</th><th>Tiêu đề</th><th>ISBN</th><th>Publisher</th><th>Quantity</th><th>Tác giả</th><th>Thao tác</th></tr></thead>
    <tbody>
    <c:forEach var="b" items="${books}">
        <fmt:formatNumber value="${b.bookId}" pattern="00" var="coverNo"/>
        <tr>
            <td><img class="table-cover" src="${pageContext.request.contextPath}/cover?id=${b.bookId}" alt="Bìa minh họa cho ${b.title}"></td>
            <td><span class="id-chip">#${b.bookId}</span></td>
            <td><strong><c:out value="${b.title}"/></strong></td>
            <td>${b.isbn}</td>
            <td><c:out value="${b.publisher}"/></td>
            <td><span class="stock-pill">${b.quantity}</span></td>
            <td><c:forEach var="a" items="${b.authors}" varStatus="s"><c:out value="${a.authorName}"/><c:if test="${!s.last}">, </c:if></c:forEach></td>
            <td><div class="table-actions"><a class="action-edit" href="${pageContext.request.contextPath}/admin/books/edit?id=${b.bookId}">Sửa</a><a class="action-delete" onclick="return confirm('Bạn chắc chắn muốn xóa sách này?')" href="${pageContext.request.contextPath}/admin/books/delete?id=${b.bookId}">Xóa</a></div></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>
<nav class="pagination"><c:forEach begin="1" end="${totalPages}" var="i"><a class="${i==page?'active':''}" href="${pageContext.request.contextPath}/admin/books?page=${i}">${i}</a></c:forEach></nav>
</body></html>
