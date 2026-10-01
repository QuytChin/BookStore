USE BookStore_24110171;
GO

/*
  FILE TEST CHỨC NĂNG USER - MSSV 24110171
  Project cũng tự tạo 3 bảng mới khi khởi động nếu database cũ chưa có.
  File này dùng khi muốn chủ động tạo bảng/test trạng thái trực tiếp trong SQL Server.
*/

-- ======================= BỔ SUNG GIỎ HÀNG / COD / LỊCH SỬ ĐƠN =======================
IF OBJECT_ID('cart_items','U') IS NULL
BEGIN
    CREATE TABLE cart_items(
        cart_item_id INT IDENTITY(1,1) PRIMARY KEY,
        user_id INT NOT NULL,
        bookid INT NOT NULL,
        quantity INT NOT NULL,
        updated_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
        CONSTRAINT CK_cart_quantity CHECK(quantity >= 1),
        CONSTRAINT UQ_cart_user_book UNIQUE(user_id, bookid),
        CONSTRAINT FK_cart_user FOREIGN KEY(user_id) REFERENCES users(id),
        CONSTRAINT FK_cart_book FOREIGN KEY(bookid) REFERENCES books(bookid)
    );
END
GO

IF OBJECT_ID('customer_orders','U') IS NULL
BEGIN
    CREATE TABLE customer_orders(
        order_id INT IDENTITY(1,1) PRIMARY KEY,
        user_id INT NOT NULL,
        created_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
        status VARCHAR(30) NOT NULL DEFAULT 'NEW',
        payment_method VARCHAR(20) NOT NULL DEFAULT 'COD',
        receiver_name NVARCHAR(100) NOT NULL,
        receiver_phone VARCHAR(20) NOT NULL,
        shipping_address NVARCHAR(255) NOT NULL,
        note NVARCHAR(500) NULL,
        total_amount DECIMAL(12,2) NOT NULL,
        CONSTRAINT CK_order_status CHECK(status IN
            ('NEW','CONFIRMED','PREPARING','SHIPPING','DELIVERING','DELIVERED','CANCELED','RETURNED')),
        CONSTRAINT FK_order_user FOREIGN KEY(user_id) REFERENCES users(id)
    );
END
GO

IF OBJECT_ID('order_items','U') IS NULL
BEGIN
    CREATE TABLE order_items(
        order_item_id INT IDENTITY(1,1) PRIMARY KEY,
        order_id INT NOT NULL,
        bookid INT NOT NULL,
        book_title NVARCHAR(200) NOT NULL,
        unit_price DECIMAL(12,2) NOT NULL,
        quantity INT NOT NULL,
        line_total DECIMAL(12,2) NOT NULL,
        CONSTRAINT CK_order_item_quantity CHECK(quantity >= 1),
        CONSTRAINT FK_order_item_order FOREIGN KEY(order_id) REFERENCES customer_orders(order_id),
        CONSTRAINT FK_order_item_book FOREIGN KEY(bookid) REFERENCES books(bookid)
    );
END
GO

/*
  Các mã trạng thái dùng để test bộ lọc lịch sử đơn hàng:
  NEW        = Đơn hàng mới
  CONFIRMED  = Đã xác nhận
  PREPARING  = Chuẩn bị hàng
  SHIPPING   = Vận chuyển
  DELIVERING = Giao hàng
  DELIVERED  = Đã giao
  CANCELED   = Đơn hàng hủy
  RETURNED   = Đơn hàng hoàn

  Ví dụ: sau khi User đặt đơn #1, chạy từng lệnh rồi F5 trang /orders để quan sát:
  UPDATE customer_orders SET status='CONFIRMED'  WHERE order_id=1;
  UPDATE customer_orders SET status='PREPARING'  WHERE order_id=1;
  UPDATE customer_orders SET status='SHIPPING'   WHERE order_id=1;
  UPDATE customer_orders SET status='DELIVERING' WHERE order_id=1;
  UPDATE customer_orders SET status='DELIVERED'  WHERE order_id=1;
  UPDATE customer_orders SET status='CANCELED'   WHERE order_id=1;
  UPDATE customer_orders SET status='RETURNED'   WHERE order_id=1;

  Xem nhanh dữ liệu:
  SELECT * FROM customer_orders ORDER BY order_id DESC;
  SELECT * FROM order_items ORDER BY order_item_id DESC;
  SELECT * FROM cart_items ORDER BY cart_item_id DESC;
*/
