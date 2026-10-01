USE BookStore_24110171;
GO

/*
  Chạy file này nếu bạn đã tạo database từ bản cũ và không muốn DROP toàn bộ bảng.
  Script cập nhật dữ liệu mẫu, tên tác giả có dấu, ảnh bìa và review.
*/

-- Chuyển các cột văn bản sang Unicode để không còn lỗi dấu tiếng Việt thành dấu ?.
ALTER TABLE author ALTER COLUMN author_name NVARCHAR(100) NULL;
ALTER TABLE books ALTER COLUMN title NVARCHAR(200) NULL;
ALTER TABLE books ALTER COLUMN publisher NVARCHAR(100) NULL;
ALTER TABLE books ALTER COLUMN description NVARCHAR(MAX) NULL;
ALTER TABLE rating ALTER COLUMN review_text NVARCHAR(MAX) NULL;
GO

UPDATE author SET author_name=N'Nguyễn Nhật Ánh', date_of_birth='1955-05-07' WHERE author_id=1;
UPDATE author SET author_name=N'Nam Cao', date_of_birth='1915-10-29' WHERE author_id=2;
UPDATE author SET author_name=N'Tô Hoài', date_of_birth='1920-09-27' WHERE author_id=3;
UPDATE author SET author_name=N'Thạch Lam', date_of_birth='1910-07-07' WHERE author_id=4;
UPDATE author SET author_name=N'Ngô Tất Tố', date_of_birth='1893-01-01' WHERE author_id=5;
UPDATE author SET author_name=N'Vũ Trọng Phụng', date_of_birth='1912-10-20' WHERE author_id=6;
UPDATE author SET author_name=N'Xuân Diệu', date_of_birth='1916-02-02' WHERE author_id=7;
UPDATE author SET author_name=N'Huy Cận', date_of_birth='1919-05-31' WHERE author_id=8;

UPDATE books SET title=N'Mắt Biếc', publisher=N'NXB Trẻ', price=55, description=N'Một câu chuyện trong trẻo về tình yêu tuổi học trò, ký ức và những điều đẹp đẽ luôn ở lại trong mỗi người.', publish_date='2019-11-15', cover_image='assets/img/book-01.png', quantity=18 WHERE bookid=1;
UPDATE books SET title=N'Chí Phèo', publisher=N'NXB Văn Học', price=58, description=N'Tác phẩm hiện thực giàu sức nặng về số phận con người, lòng lương thiện và bi kịch bị xã hội cũ đẩy đến đường cùng.', publish_date='2021-06-10', cover_image='assets/img/book-02.png', quantity=12 WHERE bookid=2;
UPDATE books SET title=N'Dế Mèn Phiêu Lưu Ký', publisher=N'NXB Kim Đồng', price=61, description=N'Hành trình trưởng thành sinh động của Dế Mèn, giàu trí tưởng tượng, tinh thần khám phá và những bài học về tình bạn.', publish_date='2020-09-20', cover_image='assets/img/book-03.png', quantity=25 WHERE bookid=3;
UPDATE books SET title=N'Hai Đứa Trẻ', publisher=N'NXB Văn Học', price=64, description=N'Một truyện ngắn giàu chất thơ, tái hiện nhịp sống phố huyện cùng những ước mơ nhỏ bé nhưng bền bỉ về một tương lai sáng hơn.', publish_date='2022-01-08', cover_image='assets/img/book-04.png', quantity=14 WHERE bookid=4;
UPDATE books SET title=N'Tắt Đèn', publisher=N'NXB Văn Học', price=67, description=N'Bức tranh hiện thực sâu sắc về cuộc sống người nông dân trước Cách mạng, nổi bật với sức sống và tình thương của chị Dậu.', publish_date='2021-03-12', cover_image='assets/img/book-05.png', quantity=16 WHERE bookid=5;
UPDATE books SET title=N'Số Đỏ', publisher=N'NXB Văn Học', price=70, description=N'Một tác phẩm trào phúng sắc sảo, phơi bày những nghịch lý của xã hội thành thị nửa Tây nửa ta bằng giọng văn hài hước.', publish_date='2020-08-18', cover_image='assets/img/book-06.png', quantity=20 WHERE bookid=6;
UPDATE books SET title=N'Thơ Tình Xuân Diệu', publisher=N'NXB Hội Nhà Văn', price=73, description=N'Tuyển chọn những bài thơ tình tiêu biểu với cảm xúc nồng nhiệt, say mê cuộc sống và khát vọng yêu hết mình.', publish_date='2022-02-14', cover_image='assets/img/book-07.png', quantity=19 WHERE bookid=7;
UPDATE books SET title=N'Lửa Thiêng', publisher=N'NXB Văn Học', price=76, description=N'Tập thơ giàu chiều sâu suy tưởng, mang vẻ đẹp cổ điển và nỗi buồn nhân thế được thể hiện bằng ngôn ngữ tinh tế.', publish_date='2021-10-05', cover_image='assets/img/book-08.png', quantity=15 WHERE bookid=8;
UPDATE books SET title=N'Cho Tôi Xin Một Vé Đi Tuổi Thơ', publisher=N'NXB Trẻ', price=79, description=N'Cuốn sách gợi lại thế giới tuổi thơ bằng giọng kể hóm hỉnh, ấm áp và nhiều khoảnh khắc khiến người đọc mỉm cười.', publish_date='2020-06-01', cover_image='assets/img/book-09.png', quantity=27 WHERE bookid=9;
UPDATE books SET title=N'Lão Hạc', publisher=N'NXB Văn Học', price=82, description=N'Câu chuyện cảm động về một người cha nghèo giàu lòng tự trọng, qua đó thể hiện tình thương sâu sắc đối với người nông dân.', publish_date='2022-04-09', cover_image='assets/img/book-10.png', quantity=11 WHERE bookid=10;
UPDATE books SET title=N'Vợ Chồng A Phủ', publisher=N'NXB Giáo Dục', price=85, description=N'Tác phẩm viết về cuộc sống miền núi Tây Bắc, nổi bật với sức sống, khát vọng tự do và hành trình tự giải phóng của con người.', publish_date='2021-11-20', cover_image='assets/img/book-11.png', quantity=17 WHERE bookid=11;
UPDATE books SET title=N'Gió Đầu Mùa', publisher=N'NXB Văn Học', price=88, description=N'Những trang văn nhẹ nhàng, trong trẻo, giàu cảm xúc về con người và những rung động rất đỗi đời thường.', publish_date='2020-12-12', cover_image='assets/img/book-12.png', quantity=13 WHERE bookid=12;
UPDATE books SET title=N'Việc Làng', publisher=N'NXB Văn Học', price=91, description=N'Tác phẩm phản ánh nhiều phong tục, hủ tục và mâu thuẫn trong đời sống làng quê bằng ngòi bút hiện thực sắc bén.', publish_date='2022-05-16', cover_image='assets/img/book-13.png', quantity=10 WHERE bookid=13;
UPDATE books SET title=N'Giông Tố', publisher=N'NXB Văn Học', price=94, description=N'Một tiểu thuyết hiện thực mạnh mẽ về xã hội cũ, số phận con người và những xung đột dữ dội giữa tiền bạc, quyền lực và tình cảm.', publish_date='2021-07-07', cover_image='assets/img/book-14.png', quantity=21 WHERE bookid=14;
GO

-- Chuẩn hóa lại quan hệ sách - tác giả để database cũ không còn ghép sai tác giả.
DELETE FROM book_author WHERE bookid BETWEEN 1 AND 14;
INSERT book_author(bookid,author_id) VALUES
(1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(7,7),(8,8),
(9,1),(10,2),(11,3),(12,4),(13,5),(14,6);
GO

IF NOT EXISTS(SELECT 1 FROM users WHERE email='reader1@hcmute.edu.vn')
INSERT users(email,fullname,phone,passwd,signup_date,last_login,is_admin)
VALUES('reader1@hcmute.edu.vn',N'Trần Hoàng Nam',903101171,'e10adc3949ba59abbe56e057f20f883e',GETDATE(),NULL,0);

IF NOT EXISTS(SELECT 1 FROM users WHERE email='reader2@hcmute.edu.vn')
INSERT users(email,fullname,phone,passwd,signup_date,last_login,is_admin)
VALUES('reader2@hcmute.edu.vn',N'Lê Thảo Vy',904101171,'e10adc3949ba59abbe56e057f20f883e',GETDATE(),NULL,0);
GO

DECLARE @u INT=(SELECT id FROM users WHERE email='user@hcmute.edu.vn');
DECLARE @r1 INT=(SELECT id FROM users WHERE email='reader1@hcmute.edu.vn');
DECLARE @r2 INT=(SELECT id FROM users WHERE email='reader2@hcmute.edu.vn');

IF @u IS NOT NULL AND NOT EXISTS(SELECT 1 FROM rating WHERE userid=@u AND bookid=1)
INSERT rating VALUES(@u,1,5,N'Một câu chuyện rất đẹp và giàu cảm xúc. Mạch kể nhẹ nhàng nhưng để lại dư âm lâu, đặc biệt là cách tác giả viết về ký ức tuổi học trò và những điều chưa kịp nói.');
IF @r1 IS NOT NULL AND NOT EXISTS(SELECT 1 FROM rating WHERE userid=@r1 AND bookid=1)
INSERT rating VALUES(@r1,1,5,N'Văn phong trong trẻo, gần gũi và rất dễ đồng cảm. Đọc xong vẫn thấy bâng khuâng vì câu chuyện vừa dịu dàng vừa man mác buồn.');
IF @u IS NOT NULL AND NOT EXISTS(SELECT 1 FROM rating WHERE userid=@u AND bookid=2)
INSERT rating VALUES(@u,2,5,N'Ngôn ngữ sắc lạnh nhưng giàu tình người. Nhân vật được xây dựng có chiều sâu, khiến mình suy nghĩ nhiều về định kiến và khát vọng được sống lương thiện.');
IF @r2 IS NOT NULL AND NOT EXISTS(SELECT 1 FROM rating WHERE userid=@r2 AND bookid=3)
INSERT rating VALUES(@r2,3,5,N'Một cuốn sách rất cuốn hút, giàu trí tưởng tượng và phù hợp với nhiều lứa tuổi. Những bài học về tình bạn và sự trưởng thành được kể rất tự nhiên.');
IF @u IS NOT NULL AND NOT EXISTS(SELECT 1 FROM rating WHERE userid=@u AND bookid=4)
INSERT rating VALUES(@u,4,4,N'Không có nhiều biến cố lớn nhưng không khí truyện rất đẹp. Những chi tiết nhỏ về phố huyện tạo cảm giác yên tĩnh, buồn và đầy chất thơ.');
IF @r1 IS NOT NULL AND NOT EXISTS(SELECT 1 FROM rating WHERE userid=@r1 AND bookid=5)
INSERT rating VALUES(@r1,5,5,N'Tác phẩm có sức nặng hiện thực rõ rệt. Mình ấn tượng nhất với nghị lực, tình thương gia đình và sự phản kháng mạnh mẽ của chị Dậu.');
IF @r2 IS NOT NULL AND NOT EXISTS(SELECT 1 FROM rating WHERE userid=@r2 AND bookid=6)
INSERT rating VALUES(@r2,6,5,N'Châm biếm thông minh, nhiều đoạn vừa buồn cười vừa cay đắng. Càng đọc càng thấy rõ sự lố lăng và nghịch lý của xã hội được tác giả phơi bày.');
IF @r1 IS NOT NULL AND NOT EXISTS(SELECT 1 FROM rating WHERE userid=@r1 AND bookid=9)
INSERT rating VALUES(@r1,9,5,N'Rất dễ đọc, hóm hỉnh và ấm áp. Cuốn sách khiến mình nhớ lại nhiều kỷ niệm tuổi thơ và trân trọng hơn những điều giản dị quanh mình.');
GO

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
