<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head><title>Giỏ hàng</title></head>
<body>
<div class="breadcrumb"><a href="${pageContext.request.contextPath}/home">Trang chủ</a><span>›</span><span>Giỏ hàng</span></div>

<section class="commerce-heading">
    <div>
        <span class="eyebrow">GIỎ HÀNG USER</span>
        <h1>Giỏ hàng của bạn</h1>
        <p>Thêm, xóa và thay đổi số lượng trong giới hạn tồn kho của từng sản phẩm.</p>
    </div>
    <span class="cart-summary-pill">${cartQuantity} sản phẩm</span>
</section>

<c:if test="${not empty cartMessage}"><div class="alert success"><c:out value="${cartMessage}"/></div></c:if>
<c:if test="${not empty cartError}"><div class="alert error"><c:out value="${cartError}"/></div></c:if>

<c:choose>
    <c:when test="${empty cartItems}">
        <div class="empty-commerce">
            <div class="empty-icon">🛒</div>
            <h2>Giỏ hàng đang trống</h2>
            <p>Chọn một cuốn sách bạn thích rồi thêm vào giỏ để tiếp tục.</p>
            <a class="primary-action" href="${pageContext.request.contextPath}/products">Xem sản phẩm</a>
        </div>
    </c:when>
    <c:otherwise>
        <div class="cart-layout">
            <section class="cart-list">
                <c:forEach var="item" items="${cartItems}">
                    <article class="cart-row">
                        <a class="cart-cover" href="${pageContext.request.contextPath}/books/detail?id=${item.book.bookId}">
                            <img src="${pageContext.request.contextPath}/cover?id=${item.book.bookId}" alt="${item.book.title}">
                        </a>
                        <div class="cart-product">
                            <span class="book-isbn">ISBN ${item.book.isbn}</span>
                            <h3><a href="${pageContext.request.contextPath}/books/detail?id=${item.book.bookId}"><c:out value="${item.book.title}"/></a></h3>
                            <p><c:out value="${item.book.publisher}"/></p>
                            <span class="stock-pill">Còn ${item.book.quantity} cuốn</span>
                        </div>
                        <div class="cart-price">
                            <b><fmt:formatNumber value="${item.book.price}" minFractionDigits="0" maxFractionDigits="2"/>k</b>
                            <span>/ cuốn</span>
                        </div>
                        <form class="qty-form" method="post" action="${pageContext.request.contextPath}/cart/update">
                            <input type="hidden" name="bookId" value="${item.book.bookId}">
                            <label>Số lượng</label>
                            <div class="qty-control">
                                <button type="button" onclick="this.nextElementSibling.stepDown();this.nextElementSibling.dispatchEvent(new Event('change'))">−</button>
                                <input type="number" name="quantity" value="${item.quantity}" min="1" max="${item.book.quantity}" required>
                                <button type="button" onclick="this.previousElementSibling.stepUp();this.previousElementSibling.dispatchEvent(new Event('change'))">+</button>
                            </div>
                            <button class="secondary-action" type="submit">Cập nhật</button>
                        </form>
                        <div class="cart-line-total">
                            <span>Thành tiền</span>
                            <strong><fmt:formatNumber value="${item.lineTotal}" minFractionDigits="0" maxFractionDigits="2"/>k</strong>
                            <form method="post" action="${pageContext.request.contextPath}/cart/remove" onsubmit="return confirm('Xóa sản phẩm này khỏi giỏ hàng?')">
                                <input type="hidden" name="bookId" value="${item.book.bookId}">
                                <button class="remove-link" type="submit">Xóa</button>
                            </form>
                        </div>
                    </article>
                </c:forEach>
            </section>

            <aside class="cart-summary-card">
                <span class="eyebrow">TÓM TẮT</span>
                <h2>Thông tin đơn</h2>
                <div class="summary-line"><span>Tổng số lượng</span><b>${cartQuantity}</b></div>
                <div class="summary-line"><span>Phương thức</span><b>COD</b></div>
                <div class="summary-total"><span>Tạm tính</span><strong><fmt:formatNumber value="${cartTotal}" minFractionDigits="0" maxFractionDigits="2"/>k</strong></div>
                <p>Thanh toán bằng tiền mặt khi nhận hàng.</p>
                <a class="primary-action full" href="${pageContext.request.contextPath}/checkout">Tiến hành thanh toán COD</a>
                <a class="continue-link" href="${pageContext.request.contextPath}/products">← Tiếp tục mua hàng</a>
            </aside>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>
