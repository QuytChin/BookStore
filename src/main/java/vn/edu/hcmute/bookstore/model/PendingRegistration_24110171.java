package vn.edu.hcmute.bookstore.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/** Dữ liệu đăng ký tạm trong Session, chỉ lưu vào DB sau khi OTP đúng. */
public class PendingRegistration_24110171 implements Serializable {
    private final String email;
    private final String fullname;
    private final Integer phone;
    private final String passwordHash;
    private final String otp;
    private final LocalDateTime expiredAt;

    public PendingRegistration_24110171(String email, String fullname, Integer phone,
                                        String passwordHash, String otp, LocalDateTime expiredAt) {
        this.email = email;
        this.fullname = fullname;
        this.phone = phone;
        this.passwordHash = passwordHash;
        this.otp = otp;
        this.expiredAt = expiredAt;
    }
    public String getEmail() { return email; }
    public String getFullname() { return fullname; }
    public Integer getPhone() { return phone; }
    public String getPasswordHash() { return passwordHash; }
    public String getOtp() { return otp; }
    public LocalDateTime getExpiredAt() { return expiredAt; }
}
