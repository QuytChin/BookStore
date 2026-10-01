package vn.edu.hcmute.bookstore.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/** MD5 dùng để khớp đúng cột passwd varchar(32) của đề. Production nên dùng BCrypt/Argon2. */
public final class PasswordUtil_24110171 {
    private PasswordUtil_24110171() {}
    public static String md5(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
