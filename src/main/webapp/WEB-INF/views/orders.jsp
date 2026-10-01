<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head><title>Lịch sử đặt hàng</title></head>
<body>
<div class="breadcrumb"><a href="${pageContext.request.contextPath}/home">Trang chủ</a><span>›</span><span>Lịch sử đặt hàng</span></div>

<section class="commerce-heading order-heading">
    <div>
        <span class="eyebrow">LỊCH SỬ ĐẶT HÀNG</span>
        <h1>Theo dõi đơn hàng</h1>
        <p>Lọc đơn theo từng trạng thái. Khi thay đổi cột <b>status</b> trong database rồi tải lại trang, trạng thái sẽ cập nhật tương ứng.</p>
    </div>
    <a class="secondary-action" href="${pageContext.request.contextPath}/products">+ Mua thêm sách</a>
</section>

<c:if test="${not empty orderMessage}"><div class="alert success"><c:out value="${orderMessage}"/></div></c:if>

<nav class="order-filters" aria-label="Lọc trạng thái đơn hàng">
    <a class="${empty selectedStatus ? 'active' : ''}" href="${pageContext.request.contextPath}/orders">Tất cả</a>
    <c:forEach var="s" items="${statuses}">
        <a class="${not empty selectedStatus and selectedStatus.code == s.code ? 'active' : ''}"
           href="${pageContext.request.contextPath}/orders?status=${s.code}"><c:out value="${s.label}"/></a>
    </c:forEach>
</nav>

<c:choose>
    <c:when test="${empty orders}">
        <div class="empty-commerce">
            <div class="empty-icon">📦</div>
            <h2>Chưa có đơn hàng phù hợp</h2>
            <p>Không có đơn ở trạng thái đang chọn. Bạn có thể chọn trạng thái khác hoặc tiếp tục mua sắm.</p>
            <a class="primary-action" href="${pageContext.request.contextPath}/products">Xem sản phẩm</a>
        </div>
    </c:when>
    <c:otherwise>
        <div class="order-list">
            <c:forEach var="order" items="${orders}">
                <article class="order-card">
                    <header class="order-card-head">
                        <div>
                            <span class="order-id">Đơn #${order.orderId}</span>
                            <span class="order-time"><c:out value="${order.createdAtDisplay}"/></span>
                        </div>
                        <span class="status-badge status-${order.status.cssClass}"><c:out value="${order.status.label}"/></span>
                    </header>

                    <div class="order-progress">
                        <span class="${order.status.code == 'NEW' ? 'current' : ''}">1. Mới</span>
                        <span class="${order.status.code == 'CONFIRMED' ? 'current' : ''}">2. Xác nhận</span>
                        <span class="${order.status.code == 'PREPARING' ? 'current' : ''}">3. Chuẩn bị</span>
                        <span class="${order.status.code == 'SHIPPING' ? 'current' : ''}">4. Vận chuyển</span>
                        <span class="${order.status.code == 'DELIVERING' ? 'current' : ''}">5. Giao hàng</span>
                        <span class="${order.status.code == 'DELIVERED' ? 'current' : ''}">6. Đã giao</span>
                    </div>

                    <div class="order-body">
                        <div class="order-items">
                            <c:forEach var="item" items="${order.items}">
                                <div class="order-item">
                                    <img src="${pageContext.request.contextPath}/cover?id=${item.book.bookId}" alt="${item.bookTitle}">
                                    <div>
                                        <b><c:out value="${item.bookTitle}"/></b>
                                        <span>Số lượng: ${item.quantity} • Đơn giá: <fmt:formatNumber value="${item.unitPrice}" minFractionDigits="0" maxFractionDigits="2"/>k</span>
                                    </div>
                                    <strong><fmt:formatNumber value="${item.lineTotal}" minFractionDigits="0" maxFractionDigits="2"/>k</strong>
                                </div>
                            </c:forEach>
                        </div>
                        <aside class="order-meta">
                            <div><span>Thanh toán</span><b>${order.paymentMethod}</b></div>
                            <div><span>Người nhận</span><b><c:out value="${order.receiverName}"/></b></div>
                            <div><span>Điện thoại</span><b><c:out value="${order.receiverPhone}"/></b></div>
                            <div class="address-row"><span>Địa chỉ</span><b><c:out value="${order.shippingAddress}"/></b></div>
                            <div class="order-grand-total"><span>Tổng đơn</span><strong><fmt:formatNumber value="${order.totalAmount}" minFractionDigits="0" maxFractionDigits="2"/>k</strong></div>
                        </aside>
                    </div>
                </article>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>
