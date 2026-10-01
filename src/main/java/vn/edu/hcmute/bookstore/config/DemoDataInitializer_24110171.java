package vn.edu.hcmute.bookstore.config;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vn.edu.hcmute.bookstore.model.Author_24110171;
import vn.edu.hcmute.bookstore.model.Book_24110171;
import vn.edu.hcmute.bookstore.model.RatingId_24110171;
import vn.edu.hcmute.bookstore.model.Rating_24110171;
import vn.edu.hcmute.bookstore.model.User_24110171;
import vn.edu.hcmute.bookstore.util.PasswordUtil_24110171;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Tạo dữ liệu demo khi database đang trống.
 * Dữ liệu có tiếng Việt, ảnh bìa, mô tả và review để dễ chụp báo cáo.
 */
@WebListener
public class DemoDataInitializer_24110171 implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EntityManager em = null;
        try {
            em = JPAUtil_24110171.createEntityManager();

            // Tự chuyển các cột văn bản sang Unicode trước khi cập nhật dữ liệu mẫu.
            // Nhờ vậy database cũ cũng hiển thị đúng: Mắt Biếc, Nguyễn Nhật Ánh, NXB Trẻ...
            em.getTransaction().begin();
            ensureUnicodeSchema(em);
            ensureCommerceSchema(em);
            em.getTransaction().commit();

            Long totalBooks = em.createQuery("select count(b) from Book_24110171 b", Long.class)
                    .getSingleResult();
            if (totalBooks > 0) {
                // Database đã có dữ liệu: cập nhật lại dữ liệu mẫu để ảnh bìa/tên sách
                // luôn đúng ngay cả khi người dùng đang dùng database cũ.
                em.getTransaction().begin();
                refreshExistingDemoData(em);
                em.getTransaction().commit();
                return;
            }

            em.getTransaction().begin();

            User_24110171 admin = user("admin@hcmute.edu.vn", "Quản trị viên 24110171", true);
            User_24110171 user = user("user@hcmute.edu.vn", "Nguyễn Minh Anh", false);
            User_24110171 reader1 = user("reader1@hcmute.edu.vn", "Trần Hoàng Nam", false);
            User_24110171 reader2 = user("reader2@hcmute.edu.vn", "Lê Thảo Vy", false);
            em.persist(admin);
            em.persist(user);
            em.persist(reader1);
            em.persist(reader2);

            String[] authorNames = {
                    "Nguyễn Nhật Ánh", "Nam Cao", "Tô Hoài", "Thạch Lam",
                    "Ngô Tất Tố", "Vũ Trọng Phụng", "Xuân Diệu", "Huy Cận"
            };
            LocalDate[] births = {
                    LocalDate.of(1955, 5, 7), LocalDate.of(1915, 10, 29),
                    LocalDate.of(1920, 9, 27), LocalDate.of(1910, 7, 7),
                    LocalDate.of(1893, 1, 1), LocalDate.of(1912, 10, 20),
                    LocalDate.of(1916, 2, 2), LocalDate.of(1919, 5, 31)
            };
            List<Author_24110171> authors = new ArrayList<>();
            for (int i = 0; i < authorNames.length; i++) {
                Author_24110171 a = new Author_24110171();
                a.setAuthorName(authorNames[i]);
                a.setDateOfBirth(births[i]);
                em.persist(a);
                authors.add(a);
            }
            em.flush();

            String[] titles = {
                    "Mắt Biếc", "Chí Phèo", "Dế Mèn Phiêu Lưu Ký", "Hai Đứa Trẻ",
                    "Tắt Đèn", "Số Đỏ", "Thơ Tình Xuân Diệu", "Lửa Thiêng",
                    "Cho Tôi Xin Một Vé Đi Tuổi Thơ", "Lão Hạc", "Vợ Chồng A Phủ",
                    "Gió Đầu Mùa", "Việc Làng", "Giông Tố"
            };
            String[] publishers = {
                    "NXB Trẻ", "NXB Văn Học", "NXB Kim Đồng", "NXB Văn Học",
                    "NXB Văn Học", "NXB Văn Học", "NXB Hội Nhà Văn", "NXB Văn Học",
                    "NXB Trẻ", "NXB Văn Học", "NXB Giáo Dục", "NXB Văn Học",
                    "NXB Văn Học", "NXB Văn Học"
            };
            String[] descriptions = {
                    "Một câu chuyện trong trẻo về tình yêu tuổi học trò, ký ức và những điều đẹp đẽ luôn ở lại trong mỗi người.",
                    "Tác phẩm hiện thực giàu sức nặng về số phận con người, lòng lương thiện và bi kịch bị xã hội cũ đẩy đến đường cùng.",
                    "Hành trình trưởng thành sinh động của Dế Mèn, giàu trí tưởng tượng, tinh thần khám phá và những bài học về tình bạn.",
                    "Một truyện ngắn giàu chất thơ, tái hiện nhịp sống phố huyện cùng những ước mơ nhỏ bé nhưng bền bỉ về một tương lai sáng hơn.",
                    "Bức tranh hiện thực sâu sắc về cuộc sống người nông dân trước Cách mạng, nổi bật với sức sống và tình thương của chị Dậu.",
                    "Một tác phẩm trào phúng sắc sảo, phơi bày những nghịch lý của xã hội thành thị nửa Tây nửa ta bằng giọng văn hài hước.",
                    "Tuyển chọn những bài thơ tình tiêu biểu với cảm xúc nồng nhiệt, say mê cuộc sống và khát vọng yêu hết mình.",
                    "Tập thơ giàu chiều sâu suy tưởng, mang vẻ đẹp cổ điển và nỗi buồn nhân thế được thể hiện bằng ngôn ngữ tinh tế.",
                    "Cuốn sách gợi lại thế giới tuổi thơ bằng giọng kể hóm hỉnh, ấm áp và nhiều khoảnh khắc khiến người đọc mỉm cười.",
                    "Câu chuyện cảm động về một người cha nghèo giàu lòng tự trọng, qua đó thể hiện tình thương sâu sắc đối với người nông dân.",
                    "Tác phẩm viết về cuộc sống miền núi Tây Bắc, nổi bật với sức sống, khát vọng tự do và hành trình tự giải phóng của con người.",
                    "Những trang văn nhẹ nhàng, trong trẻo, giàu cảm xúc về con người và những rung động rất đỗi đời thường.",
                    "Tác phẩm phản ánh nhiều phong tục, hủ tục và mâu thuẫn trong đời sống làng quê bằng ngòi bút hiện thực sắc bén.",
                    "Một tiểu thuyết hiện thực mạnh mẽ về xã hội cũ, số phận con người và những xung đột dữ dội giữa tiền bạc, quyền lực và tình cảm."
            };
            int[] authorIndexes = {0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5};
            LocalDate[] publishDates = {
                    LocalDate.of(2019, 11, 15), LocalDate.of(2021, 6, 10), LocalDate.of(2020, 9, 20),
                    LocalDate.of(2022, 1, 8), LocalDate.of(2021, 3, 12), LocalDate.of(2020, 8, 18),
                    LocalDate.of(2022, 2, 14), LocalDate.of(2021, 10, 5), LocalDate.of(2020, 6, 1),
                    LocalDate.of(2022, 4, 9), LocalDate.of(2021, 11, 20), LocalDate.of(2020, 12, 12),
                    LocalDate.of(2022, 5, 16), LocalDate.of(2021, 7, 7)
            };

            List<Book_24110171> books = new ArrayList<>();
            for (int i = 0; i < titles.length; i++) {
                Book_24110171 b = new Book_24110171();
                b.setIsbn(100001 + i);
                b.setTitle(titles[i]);
                b.setPublisher(publishers[i]);
                b.setPrice(new BigDecimal(String.valueOf(55 + i * 3) + ".00"));
                b.setDescription(descriptions[i]);
                b.setPublishDate(publishDates[i]);
                b.setCoverImage("assets/img/book-" + String.format("%02d", i + 1) + ".png");
                b.setQuantity(12 + (i * 2));
                b.getAuthors().add(authors.get(authorIndexes[i]));
                em.persist(b);
                books.add(b);
            }
            em.flush();

            addReview(em, user, books.get(0), 5, "Một câu chuyện rất đẹp và giàu cảm xúc. Mạch kể nhẹ nhàng nhưng để lại dư âm lâu, đặc biệt là cách tác giả viết về ký ức tuổi học trò và những điều chưa kịp nói.");
            addReview(em, reader1, books.get(0), 5, "Văn phong trong trẻo, gần gũi và rất dễ đồng cảm. Đọc xong vẫn thấy bâng khuâng vì câu chuyện vừa dịu dàng vừa man mác buồn.");
            addReview(em, user, books.get(1), 5, "Ngôn ngữ sắc lạnh nhưng giàu tình người. Nhân vật được xây dựng có chiều sâu, khiến mình suy nghĩ nhiều về định kiến và khát vọng được sống lương thiện.");
            addReview(em, reader2, books.get(2), 5, "Một cuốn sách rất cuốn hút, giàu trí tưởng tượng và phù hợp với nhiều lứa tuổi. Những bài học về tình bạn và sự trưởng thành được kể rất tự nhiên.");
            addReview(em, user, books.get(3), 4, "Không có nhiều biến cố lớn nhưng không khí truyện rất đẹp. Những chi tiết nhỏ về phố huyện tạo cảm giác yên tĩnh, buồn và đầy chất thơ.");
            addReview(em, reader1, books.get(4), 5, "Tác phẩm có sức nặng hiện thực rõ rệt. Mình ấn tượng nhất với nghị lực, tình thương gia đình và sự phản kháng mạnh mẽ của chị Dậu.");
            addReview(em, reader2, books.get(5), 5, "Châm biếm thông minh, nhiều đoạn vừa buồn cười vừa cay đắng. Càng đọc càng thấy rõ sự lố lăng và nghịch lý của xã hội được tác giả phơi bày.");
            addReview(em, reader1, books.get(8), 5, "Rất dễ đọc, hóm hỉnh và ấm áp. Cuốn sách khiến mình nhớ lại nhiều kỷ niệm tuổi thơ và trân trọng hơn những điều giản dị quanh mình.");

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("DemoDataInitializer: " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }



    /** Đảm bảo SQL Server lưu được đầy đủ dấu tiếng Việt kể cả với database tạo từ bản cũ. */
    private void ensureUnicodeSchema(EntityManager em) {
        em.createNativeQuery("ALTER TABLE author ALTER COLUMN author_name NVARCHAR(100) NULL").executeUpdate();
        em.createNativeQuery("ALTER TABLE books ALTER COLUMN title NVARCHAR(200) NULL").executeUpdate();
        em.createNativeQuery("ALTER TABLE books ALTER COLUMN publisher NVARCHAR(100) NULL").executeUpdate();
        em.createNativeQuery("ALTER TABLE books ALTER COLUMN description NVARCHAR(MAX) NULL").executeUpdate();
        em.createNativeQuery("ALTER TABLE rating ALTER COLUMN review_text NVARCHAR(MAX) NULL").executeUpdate();
    }

    /**
     * Bổ sung các bảng cho giỏ hàng và đơn hàng nếu project đang chạy trên database cũ.
     * Nhờ vậy chỉ cần deploy lại project là có thể dùng chức năng mới mà không phải DROP dữ liệu cũ.
     */
    private void ensureCommerceSchema(EntityManager em) {
        em.createNativeQuery("""
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
                )
            END
            """).executeUpdate();

        em.createNativeQuery("""
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
                    CONSTRAINT CK_order_status CHECK(status IN ('NEW','CONFIRMED','PREPARING','SHIPPING','DELIVERING','DELIVERED','CANCELED','RETURNED')),
                    CONSTRAINT FK_order_user FOREIGN KEY(user_id) REFERENCES users(id)
                )
            END
            """).executeUpdate();

        em.createNativeQuery("""
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
                )
            END
            """).executeUpdate();
    }

    /**
     * Cập nhật dữ liệu mẫu trong database cũ mà không cần xóa database.
     * Mục đích chính là gắn ảnh bìa PNG và chuẩn hóa tên sách/tác giả có dấu.
     */
    private void refreshExistingDemoData(EntityManager em) {
        String[] titles = {
                "Mắt Biếc", "Chí Phèo", "Dế Mèn Phiêu Lưu Ký", "Hai Đứa Trẻ",
                "Tắt Đèn", "Số Đỏ", "Thơ Tình Xuân Diệu", "Lửa Thiêng",
                "Cho Tôi Xin Một Vé Đi Tuổi Thơ", "Lão Hạc", "Vợ Chồng A Phủ",
                "Gió Đầu Mùa", "Việc Làng", "Giông Tố"
        };
        String[] publishers = {
                "NXB Trẻ", "NXB Văn Học", "NXB Kim Đồng", "NXB Văn Học",
                "NXB Văn Học", "NXB Văn Học", "NXB Hội Nhà Văn", "NXB Văn Học",
                "NXB Trẻ", "NXB Văn Học", "NXB Giáo Dục", "NXB Văn Học",
                "NXB Văn Học", "NXB Văn Học"
        };
        String[] descriptions = {
                "Một câu chuyện trong trẻo về tình yêu tuổi học trò, ký ức và những điều đẹp đẽ luôn ở lại trong mỗi người.",
                "Tác phẩm hiện thực giàu sức nặng về số phận con người, lòng lương thiện và bi kịch bị xã hội cũ đẩy đến đường cùng.",
                "Hành trình trưởng thành sinh động của Dế Mèn, giàu trí tưởng tượng, tinh thần khám phá và những bài học về tình bạn.",
                "Một truyện ngắn giàu chất thơ, tái hiện nhịp sống phố huyện cùng những ước mơ nhỏ bé nhưng bền bỉ về một tương lai sáng hơn.",
                "Bức tranh hiện thực sâu sắc về cuộc sống người nông dân trước Cách mạng, nổi bật với sức sống và tình thương của chị Dậu.",
                "Một tác phẩm trào phúng sắc sảo, phơi bày những nghịch lý của xã hội thành thị nửa Tây nửa ta bằng giọng văn hài hước.",
                "Tuyển chọn những bài thơ tình tiêu biểu với cảm xúc nồng nhiệt, say mê cuộc sống và khát vọng yêu hết mình.",
                "Tập thơ giàu chiều sâu suy tưởng, mang vẻ đẹp cổ điển và nỗi buồn nhân thế được thể hiện bằng ngôn ngữ tinh tế.",
                "Cuốn sách gợi lại thế giới tuổi thơ bằng giọng kể hóm hỉnh, ấm áp và nhiều khoảnh khắc khiến người đọc mỉm cười.",
                "Câu chuyện cảm động về một người cha nghèo giàu lòng tự trọng, qua đó thể hiện tình thương sâu sắc đối với người nông dân.",
                "Tác phẩm viết về cuộc sống miền núi Tây Bắc, nổi bật với sức sống, khát vọng tự do và hành trình tự giải phóng của con người.",
                "Những trang văn nhẹ nhàng, trong trẻo, giàu cảm xúc về con người và những rung động rất đỗi đời thường.",
                "Tác phẩm phản ánh nhiều phong tục, hủ tục và mâu thuẫn trong đời sống làng quê bằng ngòi bút hiện thực sắc bén.",
                "Một tiểu thuyết hiện thực mạnh mẽ về xã hội cũ, số phận con người và những xung đột dữ dội giữa tiền bạc, quyền lực và tình cảm."
        };
        LocalDate[] publishDates = {
                LocalDate.of(2019, 11, 15), LocalDate.of(2021, 6, 10), LocalDate.of(2020, 9, 20),
                LocalDate.of(2022, 1, 8), LocalDate.of(2021, 3, 12), LocalDate.of(2020, 8, 18),
                LocalDate.of(2022, 2, 14), LocalDate.of(2021, 10, 5), LocalDate.of(2020, 6, 1),
                LocalDate.of(2022, 4, 9), LocalDate.of(2021, 11, 20), LocalDate.of(2020, 12, 12),
                LocalDate.of(2022, 5, 16), LocalDate.of(2021, 7, 7)
        };

        List<Book_24110171> books = em.createQuery(
                "select b from Book_24110171 b order by b.bookId", Book_24110171.class)
                .setMaxResults(14)
                .getResultList();
        for (int i = 0; i < books.size() && i < titles.length; i++) {
            Book_24110171 b = books.get(i);
            b.setTitle(titles[i]);
            b.setPublisher(publishers[i]);
            b.setPrice(new BigDecimal((55 + i * 3) + ".00"));
            b.setDescription(descriptions[i]);
            b.setPublishDate(publishDates[i]);
            b.setCoverImage("assets/img/book-" + String.format("%02d", i + 1) + ".png");
            if (b.getQuantity() == null) {
                b.setQuantity(12 + (i * 2));
            }
        }

        String[] authorNames = {
                "Nguyễn Nhật Ánh", "Nam Cao", "Tô Hoài", "Thạch Lam",
                "Ngô Tất Tố", "Vũ Trọng Phụng", "Xuân Diệu", "Huy Cận"
        };
        LocalDate[] births = {
                LocalDate.of(1955, 5, 7), LocalDate.of(1915, 10, 29),
                LocalDate.of(1920, 9, 27), LocalDate.of(1910, 7, 7),
                LocalDate.of(1893, 1, 1), LocalDate.of(1912, 10, 20),
                LocalDate.of(1916, 2, 2), LocalDate.of(1919, 5, 31)
        };
        List<Author_24110171> authors = em.createQuery(
                "select a from Author_24110171 a order by a.authorId", Author_24110171.class)
                .setMaxResults(8)
                .getResultList();
        for (int i = 0; i < authors.size() && i < authorNames.length; i++) {
            authors.get(i).setAuthorName(authorNames[i]);
            authors.get(i).setDateOfBirth(births[i]);
        }

        // Chuẩn hóa lại quan hệ sách - tác giả trong database cũ.
        int[] authorIndexes = {0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5};
        for (int i = 0; i < books.size() && i < authorIndexes.length; i++) {
            int ai = authorIndexes[i];
            if (ai < authors.size()) {
                books.get(i).getAuthors().clear();
                books.get(i).getAuthors().add(authors.get(ai));
            }
        }
    }

    private void addReview(EntityManager em, User_24110171 user, Book_24110171 book, int stars, String text) {
        Rating_24110171 r = new Rating_24110171();
        r.setId(new RatingId_24110171(user.getId(), book.getBookId()));
        r.setUser(user);
        r.setBook(book);
        r.setRating((byte) stars);
        r.setReviewText(text);
        em.persist(r);
    }

    private User_24110171 user(String email, String name, boolean admin) {
        User_24110171 u = new User_24110171();
        u.setEmail(email);
        u.setFullname(name);
        u.setPasswd(PasswordUtil_24110171.md5("123456"));
        u.setSignupDate(LocalDateTime.now());
        u.setAdmin(admin);
        return u;
    }
}
