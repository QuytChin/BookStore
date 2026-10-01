<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head><title>Thanh toán COD</title></head>
<body>
<div class="breadcrumb"><a href="${pageContext.request.contextPath}/cart">Giỏ hàng</a><span>›</span><span>Thanh toán COD</span></div>

<section class="commerce-heading">
    <div>
        <span class="eyebrow">THANH TOÁN ĐƠN HÀNG</span>
        <h1>Thanh toán COD</h1>
        <p>Kiểm tra thông tin nhận hàng. Bạn sẽ thanh toán tiền mặt khi nhận hàng.</p>
    </div>
    <span class="cod-badge">COD • Thanh toán khi nhận</span>
</section>

<c:if test="${not empty checkoutError}"><div class="alert error"><c:out value="${checkoutError}"/></div></c:if>

<div class="checkout-layout">
    <form class="checkout-form-card" method="post" action="${pageContext.request.contextPath}/checkout">
        <h2>Thông tin giao hàng</h2>
        <div class="form-grid-2">
            <label>Người nhận
                <input type="text" name="receiverName" maxlength="100" required
                       value="<c:out value='${not empty receiverName ? receiverName : checkoutUser.fullname}'/>"
                       placeholder="Họ và tên người nhận">
            </label>
            <label>Số điện thoại
                <input type="text" name="receiverPhone" maxlength="20" required
                       value="<c:out value='${not empty receiverPhone ? receiverPhone : checkoutUser.phone}'/>"
                       placeholder="Ví dụ: 0901234567">
            </label>
        </div>
        <label>Địa chỉ giao hàng
            <textarea name="shippingAddress" rows="3" maxlength="255" required placeholder="Số nhà, đường, phường/xã, quận/huyện, tỉnh/thành phố"><c:out value="${shippingAddress}"/></textarea>
        </label>
        <label>Ghi chú (không bắt buộc)
            <textarea name="note" rows="3" maxlength="500" placeholder="Ghi chú cho người giao hàng"><c:out value="${note}"/></textarea>
        </label>

        <div class="payment-option selected">
            <div class="payment-icon">💵</div>
            <div><b>Thanh toán khi nhận hàng (COD)</b><span>Trả tiền mặt cho nhân viên giao hàng khi nhận sách.</span></div>
            <span class="payment-check">✓</span>
        </div>

        <button class="primary-action full checkout-submit" type="submit">Đặt hàng COD</button>
        <a class="continue-link" href="${pageContext.request.contextPath}/cart">← Quay lại giỏ hàng</a>
    </form>

    <aside class="checkout-order-card">
        <span class="eyebrow">ĐƠN HÀNG CỦA BẠN</span>
        <h2>${cartItems.size()} loại sách</h2>
        <div class="checkout-items">
            <c:forEach var="item" items="${cartItems}">
                <div class="checkout-item">
                    <img src="${pageContext.request.contextPath}/cover?id=${item.book.bookId}" alt="${item.book.title}">
                    <div><b><c:out value="${item.book.title}"/></b><span>${item.quantity} × <fmt:formatNumber value="${item.book.price}" minFractionDigits="0" maxFractionDigits="2"/>k</span></div>
                    <strong><fmt:formatNumber value="${item.lineTotal}" minFractionDigits="0" maxFractionDigits="2"/>k</strong>
                </div>
            </c:forEach>
        </div>
        <div class="summary-total"><span>Tổng thanh toán</span><strong><fmt:formatNumber value="${cartTotal}" minFractionDigits="0" maxFractionDigits="2"/>k</strong></div>
        <small>Đơn mới tạo sẽ có trạng thái <b>Đơn hàng mới</b>.</small>
    </aside>
</div>
</body>
</html>
