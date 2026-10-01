package vn.edu.hcmute.bookstore.model;

/**
 * Các trạng thái đúng theo yêu cầu bài kiểm tra quá trình.
 * Giá trị code được lưu thẳng trong database để có thể UPDATE và quan sát trên giao diện.
 */
public enum OrderStatus_24110171 {
    NEW("Đơn hàng mới", "new"),
    CONFIRMED("Đã xác nhận", "confirmed"),
    PREPARING("Chuẩn bị hàng", "preparing"),
    SHIPPING("Vận chuyển", "shipping"),
    DELIVERING("Giao hàng", "delivering"),
    DELIVERED("Đã giao", "delivered"),
    CANCELED("Đơn hàng hủy", "canceled"),
    RETURNED("Đơn hàng hoàn", "returned");

    private final String label;
    private final String cssClass;

    OrderStatus_24110171(String label, String cssClass) {
        this.label = label;
        this.cssClass = cssClass;
    }

    public String getCode() { return name(); }
    public String getLabel() { return label; }
    public String getCssClass() { return cssClass; }

    public static OrderStatus_24110171 fromCode(String code) {
        if (code == null || code.isBlank()) return null;
        try { return valueOf(code.trim().toUpperCase()); }
        catch (IllegalArgumentException e) { return null; }
    }
}
