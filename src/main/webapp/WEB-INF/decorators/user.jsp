<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><sitemesh:write property='title'/></title>

    <!-- Nhúng CSS trực tiếp để không phụ thuộc việc Tomcat publish file CSS tĩnh -->
    <style>
        <%@ include file="/assets/css/style.css" %>
    </style>
    <sitemesh:write property='head'/>
</head>
<body>
<header class="topbar">
    <div class="container nav">
        <a class="brand" href="${pageContext.request.contextPath}/home">BookStore 24110171</a>
        <nav>
            <a href="${pageContext.request.contextPath}/home">Trang Chủ</a>
            <a href="${pageContext.request.contextPath}/products">Sản phẩm</a>
            <c:choose>
                <c:when test="${empty sessionScope.currentUser}">
                    <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/cart">🛒 Giỏ hàng</a>
                    <a href="${pageContext.request.contextPath}/orders">📦 Đơn hàng</a>
                    <span class="hello">Xin chào, <c:out value="${sessionScope.currentUser.fullname}"/></span>
                    <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                </c:otherwise>
            </c:choose>
            <c:if test="${not empty sessionScope.currentUser and sessionScope.currentUser.admin}">
                <a class="admin-link" href="${pageContext.request.contextPath}/admin/books">Trang quản trị</a>
            </c:if>
        </nav>
    </div>
</header>

<main class="container page">
    <sitemesh:write property='body'/>
</main>

<footer>
    <div class="container footer-inner">
        <span>© 2026 BookStore</span>
        <span>Trần Quyết Chiến • <b>24110171</b> • Mã đề <b>01</b></span>
    </div>
</footer>
</body>
</html>
