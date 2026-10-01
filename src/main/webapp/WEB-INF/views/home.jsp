<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head><title>Trang chủ BookStore</title></head>
<body>
<section class="hero home-hero">
    <div class="hero-copy">
        <span class="badge">ĐỀ 01 • MSSV 24110171</span>
        <h1>Khám phá những cuốn sách đáng đọc</h1>
        <p>Danh sách được phân trang đúng <b>6 sản phẩm / trang</b>, có ảnh bìa, tác giả, nhà xuất bản, số lượng và review.</p>
        <div class="hero-pills">
            <span>📚 14 sách mẫu</span>
            <span>✍ Tác giả Việt Nam</span>
            <span>★ Review thực tế</span>
        </div>
    </div>
    <div class="hero-art" aria-hidden="true">
        <span class="book-shape one"></span>
        <span class="book-shape two"></span>
        <span class="book-shape three"></span>
    </div>
</section>

<div class="section-heading">
    <div>
        <span class="eyebrow">THƯ VIỆN</span>
        <h2>Sách nổi bật</h2>
    </div>
    <span class="page-indicator">Trang ${page} / ${totalPages}</span>
</div>

<div class="book-grid">
    <c:forEach var="book" items="${books}">
        <fmt:formatNumber value="${book.bookId}" pattern="00" var="coverNo"/>
        <article class="book-card">
            <a class="cover-link" href="${pageContext.request.contextPath}/books/detail?id=${book.bookId}">
                <img src="${pageContext.request.contextPath}/cover?id=${book.bookId}"
                     alt="Bìa minh họa cho ${book.title}">
                <span class="cover-overlay">Xem chi tiết →</span>
            </a>
            <div class="book-info">
                <div class="book-topline">
                    <span class="book-isbn">ISBN ${book.isbn}</span>
                    <c:if test="${not empty book.price}"><span class="book-price">${book.price}k</span></c:if>
                </div>
                <h3><a href="${pageContext.request.contextPath}/books/detail?id=${book.bookId}"><c:out value="${book.title}"/></a></h3>
                <div class="author-line">✍ <c:forEach var="a" items="${book.authors}" varStatus="s"><c:out value="${a.authorName}"/><c:if test="${!s.last}">, </c:if></c:forEach></div>
                <dl>
                    <dt>Publisher</dt><dd><c:out value="${book.publisher}"/></dd>
                    <dt>Ngày XB</dt><dd><c:out value="${book.publishDate}"/></dd>
                    <dt>Quantity</dt><dd><span class="stock-pill"><c:out value="${book.quantity}"/> cuốn</span></dd>
                </dl>
                <div class="card-footer">
                    <a class="review-link" href="${pageContext.request.contextPath}/books/detail?id=${book.bookId}#reviews">★ Review (${reviewCounts[book.bookId]})</a>
                    <form method="post" action="${pageContext.request.contextPath}/cart/add" class="mini-cart-form">
                        <input type="hidden" name="bookId" value="${book.bookId}">
                        <input type="hidden" name="quantity" value="1">
                        <button class="mini-cart-btn" type="submit" ${book.quantity <= 0 ? 'disabled' : ''}>🛒 Thêm</button>
                    </form>
                    <a class="detail-arrow" href="${pageContext.request.contextPath}/books/detail?id=${book.bookId}" aria-label="Xem chi tiết">→</a>
                </div>
            </div>
        </article>
    </c:forEach>
</div>

<nav class="pagination" aria-label="Phân trang sách">
    <c:forEach begin="1" end="${totalPages}" var="i">
        <a class="${i==page?'active':''}" href="${pageContext.request.contextPath}/home?page=${i}">${i}</a>
    </c:forEach>
</nav>
</body>
</html>
