<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head><title>Chi tiết sách - ${book.title}</title></head>
<body>
<fmt:formatNumber value="${book.bookId}" pattern="00" var="coverNo"/>
<div class="breadcrumb"><a href="${pageContext.request.contextPath}/home">Trang chủ</a><span>›</span><span>Chi tiết sách</span></div>

<section class="detail">
    <div class="detail-cover-wrap">
        <img class="detail-cover" src="${pageContext.request.contextPath}/cover?id=${book.bookId}" alt="Bìa minh họa cho ${book.title}">
        <span class="cover-caption">BOOKSTORE • 24110171</span>
    </div>
    <div class="detail-main">
        <span class="badge">CHI TIẾT SÁCH</span>
        <h1><c:out value="${book.title}"/></h1>
        <div class="detail-author">Tác giả: <strong><c:forEach var="a" items="${book.authors}" varStatus="s"><c:out value="${a.authorName}"/><c:if test="${!s.last}">, </c:if></c:forEach></strong></div>

        <div class="detail-list">
            <div><span>Mã ISBN</span><strong><c:out value="${book.isbn}"/></strong></div>
            <div><span>Publisher</span><strong><c:out value="${book.publisher}"/></strong></div>
            <div><span>Publisher date</span><strong><c:out value="${book.publishDate}"/></strong></div>
            <div><span>Quantity</span><strong><c:out value="${book.quantity}"/> cuốn</strong></div>
            <div><span>Reviews</span><strong>★ ${reviewCount} đánh giá</strong></div>
            <div><span>Giá tham khảo</span><strong><c:out value="${book.price}"/>k</strong></div>
        </div>

        <div class="book-description">
            <h3>Giới thiệu</h3>
            <p><c:out value="${book.description}"/></p>
        </div>

        <div class="detail-cart-box">
            <div>
                <span class="eyebrow">MUA SÁCH</span>
                <b><c:out value="${book.price}"/>k / cuốn</b>
                <small>Số lượng tối đa theo tồn kho hiện tại: ${book.quantity}</small>
            </div>
            <c:choose>
                <c:when test="${book.quantity > 0}">
                    <form method="post" action="${pageContext.request.contextPath}/cart/add">
                        <input type="hidden" name="bookId" value="${book.bookId}">
                        <input type="number" name="quantity" value="1" min="1" max="${book.quantity}" required>
                        <button type="submit">🛒 Thêm vào giỏ hàng</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <button type="button" disabled>Hết hàng</button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</section>

<section class="reviews" id="reviews">
    <div class="section-heading compact">
        <div><span class="eyebrow">CẢM NHẬN BẠN ĐỌC</span><h2>Reviews (${reviewCount})</h2></div>
    </div>

    <c:if test="${not empty sessionScope.flashReview}">
        <div class="alert success"><c:out value="${sessionScope.flashReview}"/></div>
        <c:remove var="flashReview" scope="session"/>
    </c:if>

    <c:choose>
        <c:when test="${empty reviews}">
            <div class="empty-state">Chưa có review. Hãy là người đầu tiên chia sẻ cảm nhận về cuốn sách này.</div>
        </c:when>
        <c:otherwise>
            <div class="review-list">
                <c:forEach var="r" items="${reviews}">
                    <article class="review">
                        <div class="review-avatar">${r.user.fullname.substring(0,1)}</div>
                        <div class="review-content">
                            <div class="review-head">
                                <b><c:out value="${r.user.fullname}"/></b>
                                <span class="stars">${r.rating}/5 ★</span>
                            </div>
                            <p><c:out value="${r.reviewText}"/></p>
                        </div>
                    </article>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</section>

<section class="review-form">
    <div class="review-form-copy">
        <span class="eyebrow">CHIA SẺ CẢM NHẬN</span>
        <h2>Viết review của bạn</h2>
        <p>Đánh giá ngắn gọn, lịch sự và tập trung vào trải nghiệm đọc sách.</p>
    </div>
    <c:choose>
        <c:when test="${empty sessionScope.currentUser}">
            <div class="login-review-box">Bạn cần <a href="${pageContext.request.contextPath}/login">đăng nhập</a> để gửi review.</div>
        </c:when>
        <c:otherwise>
            <form method="post" action="${pageContext.request.contextPath}/review/add" class="review-form-fields">
                <input type="hidden" name="bookId" value="${book.bookId}">
                <label>Đánh giá
                    <select name="rating">
                        <option value="5">★★★★★ - Rất hay</option>
                        <option value="4">★★★★☆ - Hay</option>
                        <option value="3">★★★☆☆ - Ổn</option>
                        <option value="2">★★☆☆☆ - Chưa hợp</option>
                        <option value="1">★☆☆☆☆ - Không thích</option>
                    </select>
                </label>
                <label>Nội dung review
                    <textarea name="reviewText" rows="5" minlength="10" maxlength="600" required placeholder="Ví dụ: Mình thích cách tác giả xây dựng nhân vật và không khí của câu chuyện..."></textarea>
                </label>
                <button type="submit">Gửi review</button>
            </form>
        </c:otherwise>
    </c:choose>
</section>
</body>
</html>
