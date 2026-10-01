USE master;
GO
IF DB_ID('BookStore_24110171') IS NULL
    CREATE DATABASE BookStore_24110171 COLLATE Vietnamese_CI_AS;
GO
USE BookStore_24110171;
GO

IF OBJECT_ID('order_items','U') IS NOT NULL DROP TABLE order_items;
IF OBJECT_ID('customer_orders','U') IS NOT NULL DROP TABLE customer_orders;
IF OBJECT_ID('cart_items','U') IS NOT NULL DROP TABLE cart_items;
IF OBJECT_ID('rating','U') IS NOT NULL DROP TABLE rating;
IF OBJECT_ID('book_author','U') IS NOT NULL DROP TABLE book_author;
IF OBJECT_ID('books','U') IS NOT NULL DROP TABLE books;
IF OBJECT_ID('author','U') IS NOT NULL DROP TABLE author;
IF OBJECT_ID('users','U') IS NOT NULL DROP TABLE users;
GO

CREATE TABLE users(
    id INT IDENTITY(1,1) PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    fullname NVARCHAR(50) NULL,
    phone INT NULL,
    passwd VARCHAR(32) NOT NULL,
    signup_date DATETIME NULL,
    last_login DATETIME NULL,
    is_admin BIT NULL DEFAULT 0
);

CREATE TABLE author(
    author_id INT IDENTITY(1,1) PRIMARY KEY,
    author_name NVARCHAR(100) NULL,
    date_of_birth DATE NULL
);

CREATE TABLE books(
    bookid INT IDENTITY(1,1) PRIMARY KEY,
    isbn INT NULL,
    title NVARCHAR(200) NULL,
    publisher NVARCHAR(100) NULL,
    price DECIMAL(6,2) NULL,
    description NVARCHAR(MAX) NULL,
    publish_date DATE NULL,
    cover_image VARCHAR(100) NULL,
    quantity INT NULL
);

CREATE TABLE book_author(
    bookid INT NOT NULL,
    author_id INT NOT NULL,
    CONSTRAINT PK_book_author PRIMARY KEY(bookid,author_id),
    CONSTRAINT FK_book_author_book FOREIGN KEY(bookid) REFERENCES books(bookid),
    CONSTRAINT FK_book_author_author FOREIGN KEY(author_id) REFERENCES author(author_id)
);

CREATE TABLE rating(
    userid INT NOT NULL,
    bookid INT NOT NULL,
    rating TINYINT NULL CHECK(rating BETWEEN 1 AND 5),
    review_text NVARCHAR(MAX) NULL,
    CONSTRAINT PK_rating PRIMARY KEY(userid,bookid),
    CONSTRAINT FK_rating_user FOREIGN KEY(userid) REFERENCES users(id),
    CONSTRAINT FK_rating_book FOREIGN KEY(bookid) REFERENCES books(bookid)
);
GO

-- ======================= CHỨC NĂNG USER: GIỎ HÀNG + ĐƠN HÀNG =======================
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
GO

-- Tài khoản test. Mật khẩu 123456 -> MD5: e10adc3949ba59abbe56e057f20f883e
INSERT users(email,fullname,phone,passwd,signup_date,last_login,is_admin) VALUES
('admin@hcmute.edu.vn',N'Quản trị viên 24110171',901101171,'e10adc3949ba59abbe56e057f20f883e',GETDATE(),NULL,1),
('user@hcmute.edu.vn',N'Nguyễn Minh Anh',902101171,'e10adc3949ba59abbe56e057f20f883e',GETDATE(),NULL,0),
('reader1@hcmute.edu.vn',N'Trần Hoàng Nam',903101171,'e10adc3949ba59abbe56e057f20f883e',GETDATE(),NULL,0),
('reader2@hcmute.edu.vn',N'Lê Thảo Vy',904101171,'e10adc3949ba59abbe56e057f20f883e',GETDATE(),NULL,0);

INSERT author(author_name,date_of_birth) VALUES
(N'Nguyễn Nhật Ánh','1955-05-07'),
(N'Nam Cao','1915-10-29'),
(N'Tô Hoài','1920-09-27'),
(N'Thạch Lam','1910-07-07'),
(N'Ngô Tất Tố','1893-01-01'),
(N'Vũ Trọng Phụng','1912-10-20'),
(N'Xuân Diệu','1916-02-02'),
(N'Huy Cận','1919-05-31');

INSERT books(isbn,title,publisher,price,description,publish_date,cover_image,quantity) VALUES
(100001,N'Mắt Biếc',N'NXB Trẻ',55.00,N'Một câu chuyện trong trẻo về tình yêu tuổi học trò, ký ức và những điều đẹp đẽ luôn ở lại trong mỗi người.','2019-11-15','assets/img/book-01.png',18),
(100002,N'Chí Phèo',N'NXB Văn Học',58.00,N'Tác phẩm hiện thực giàu sức nặng về số phận con người, lòng lương thiện và bi kịch bị xã hội cũ đẩy đến đường cùng.','2021-06-10','assets/img/book-02.png',12),
(100003,N'Dế Mèn Phiêu Lưu Ký',N'NXB Kim Đồng',61.00,N'Hành trình trưởng thành sinh động của Dế Mèn, giàu trí tưởng tượng, tinh thần khám phá và những bài học về tình bạn.','2020-09-20','assets/img/book-03.png',25),
(100004,N'Hai Đứa Trẻ',N'NXB Văn Học',64.00,N'Một truyện ngắn giàu chất thơ, tái hiện nhịp sống phố huyện cùng những ước mơ nhỏ bé nhưng bền bỉ về một tương lai sáng hơn.','2022-01-08','assets/img/book-04.png',14),
(100005,N'Tắt Đèn',N'NXB Văn Học',67.00,N'Bức tranh hiện thực sâu sắc về cuộc sống người nông dân trước Cách mạng, nổi bật với sức sống và tình thương của chị Dậu.','2021-03-12','assets/img/book-05.png',16),
(100006,N'Số Đỏ',N'NXB Văn Học',70.00,N'Một tác phẩm trào phúng sắc sảo, phơi bày những nghịch lý của xã hội thành thị nửa Tây nửa ta bằng giọng văn hài hước.','2020-08-18','assets/img/book-06.png',20),
(100007,N'Thơ Tình Xuân Diệu',N'NXB Hội Nhà Văn',73.00,N'Tuyển chọn những bài thơ tình tiêu biểu với cảm xúc nồng nhiệt, say mê cuộc sống và khát vọng yêu hết mình.','2022-02-14','assets/img/book-07.png',19),
(100008,N'Lửa Thiêng',N'NXB Văn Học',76.00,N'Tập thơ giàu chiều sâu suy tưởng, mang vẻ đẹp cổ điển và nỗi buồn nhân thế được thể hiện bằng ngôn ngữ tinh tế.','2021-10-05','assets/img/book-08.png',15),
(100009,N'Cho Tôi Xin Một Vé Đi Tuổi Thơ',N'NXB Trẻ',79.00,N'Cuốn sách gợi lại thế giới tuổi thơ bằng giọng kể hóm hỉnh, ấm áp và nhiều khoảnh khắc khiến người đọc mỉm cười.','2020-06-01','assets/img/book-09.png',27),
(100010,N'Lão Hạc',N'NXB Văn Học',82.00,N'Câu chuyện cảm động về một người cha nghèo giàu lòng tự trọng, qua đó thể hiện tình thương sâu sắc đối với người nông dân.','2022-04-09','assets/img/book-10.png',11),
(100011,N'Vợ Chồng A Phủ',N'NXB Giáo Dục',85.00,N'Tác phẩm viết về cuộc sống miền núi Tây Bắc, nổi bật với sức sống, khát vọng tự do và hành trình tự giải phóng của con người.','2021-11-20','assets/img/book-11.png',17),
(100012,N'Gió Đầu Mùa',N'NXB Văn Học',88.00,N'Những trang văn nhẹ nhàng, trong trẻo, giàu cảm xúc về con người và những rung động rất đỗi đời thường.','2020-12-12','assets/img/book-12.png',13),
(100013,N'Việc Làng',N'NXB Văn Học',91.00,N'Tác phẩm phản ánh nhiều phong tục, hủ tục và mâu thuẫn trong đời sống làng quê bằng ngòi bút hiện thực sắc bén.','2022-05-16','assets/img/book-13.png',10),
(100014,N'Giông Tố',N'NXB Văn Học',94.00,N'Một tiểu thuyết hiện thực mạnh mẽ về xã hội cũ, số phận con người và những xung đột dữ dội giữa tiền bạc, quyền lực và tình cảm.','2021-07-07','assets/img/book-14.png',21);

INSERT book_author(bookid,author_id) VALUES
(1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(7,7),(8,8),
(9,1),(10,2),(11,3),(12,4),(13,5),(14,6);

INSERT rating(userid,bookid,rating,review_text) VALUES
(2,1,5,N'Một câu chuyện rất đẹp và giàu cảm xúc. Mạch kể nhẹ nhàng nhưng để lại dư âm lâu, đặc biệt là cách tác giả viết về ký ức tuổi học trò và những điều chưa kịp nói.'),
(3,1,5,N'Văn phong trong trẻo, gần gũi và rất dễ đồng cảm. Đọc xong vẫn thấy bâng khuâng vì câu chuyện vừa dịu dàng vừa man mác buồn.'),
(2,2,5,N'Ngôn ngữ sắc lạnh nhưng giàu tình người. Nhân vật được xây dựng có chiều sâu, khiến mình suy nghĩ nhiều về định kiến và khát vọng được sống lương thiện.'),
(4,3,5,N'Một cuốn sách rất cuốn hút, giàu trí tưởng tượng và phù hợp với nhiều lứa tuổi. Những bài học về tình bạn và sự trưởng thành được kể rất tự nhiên.'),
(2,4,4,N'Không có nhiều biến cố lớn nhưng không khí truyện rất đẹp. Những chi tiết nhỏ về phố huyện tạo cảm giác yên tĩnh, buồn và đầy chất thơ.'),
(3,5,5,N'Tác phẩm có sức nặng hiện thực rõ rệt. Mình ấn tượng nhất với nghị lực, tình thương gia đình và sự phản kháng mạnh mẽ của chị Dậu.'),
(4,6,5,N'Châm biếm thông minh, nhiều đoạn vừa buồn cười vừa cay đắng. Càng đọc càng thấy rõ sự lố lăng và nghịch lý của xã hội được tác giả phơi bày.'),
(3,9,5,N'Rất dễ đọc, hóm hỉnh và ấm áp. Cuốn sách khiến mình nhớ lại nhiều kỷ niệm tuổi thơ và trân trọng hơn những điều giản dị quanh mình.');
GO
