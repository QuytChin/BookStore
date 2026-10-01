package vn.edu.hcmute.bookstore.util;

import java.security.SecureRandom;

public final class OtpUtil_24110171 {
    private static final SecureRandom RANDOM = new SecureRandom();
    private OtpUtil_24110171() {}
    public static String generate6Digits() { return String.format("%06d", RANDOM.nextInt(1_000_000)); }
}
