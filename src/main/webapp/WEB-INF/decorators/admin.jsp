<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin • <sitemesh:write property='title'/></title>

    <!-- Nhúng CSS trực tiếp để giao diện luôn hoạt động kể cả khi Eclipse/Tomcat chưa publish file tĩnh -->
    <style>
        <%@ include file="/assets/css/style.css" %>
    </style>
    <sitemesh:write property='head'/>
</head>
<body class="admin-page">
<div class="admin-app">
    <aside class="admin-sidebar">
        <div class="admin-logo">
            <span class="admin-logo-icon">B</span>
            <div>
                <strong>BookStore</strong>
                <small>Admin Console</small>
            </div>
        </div>

        <div class="admin-user-card">
            <div class="admin-avatar">TC</div>
            <div>
                <b>Trần Quyết Chiến</b>
                <span>MSSV 24110171</span>
            </div>
        </div>

        <nav class="admin-menu">
            <span class="admin-menu-label">QUẢN LÝ</span>
            <a href="${pageContext.request.contextPath}/admin/books">
                <span class="menu-icon">▣</span>
                <span>Quản lý sách</span>
            </a>
            <a href="${pageContext.request.contextPath}/admin/authors">
                <span class="menu-icon">✦</span>
                <span>Quản lý tác giả</span>
            </a>

            <span class="admin-menu-label second">HỆ THỐNG</span>
            <a href="${pageContext.request.contextPath}/home">
                <span class="menu-icon">⌂</span>
                <span>Về trang chủ</span>
            </a>
            <a class="logout-link" href="${pageContext.request.contextPath}/logout">
                <span class="menu-icon">↪</span>
                <span>Đăng xuất</span>
            </a>
        </nav>

        <div class="admin-sidebar-bottom">
            <span>ĐỀ 01</span>
            <b>LT Web • 2026</b>
        </div>
    </aside>

    <section class="admin-workspace">
        <header class="admin-topbar">
            <div>
                <span class="eyebrow">BẢNG ĐIỀU KHIỂN</span>
                <h2>Quản trị BookStore</h2>
            </div>
            <div class="admin-status"><span></span> Hệ thống đang hoạt động</div>
        </header>

        <main class="admin-content">
            <c:if test="${not empty sessionScope.adminMsg}">
                <div class="alert success"><c:out value="${sessionScope.adminMsg}"/></div>
                <c:remove var="adminMsg" scope="session"/>
            </c:if>
            <sitemesh:write property='body'/>
        </main>

        <footer class="admin-footer">
            <span>Trần Quyết Chiến • 24110171 • Mã đề 01</span>
            <span>Vai trò: <b>Admin</b></span>
        </footer>
    </section>
</div>
</body>
</html>
