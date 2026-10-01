package vn.edu.hcmute.bookstore.service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/** Gửi OTP kích hoạt tài khoản qua Gmail SMTP. */
public class MailService_24110171 {
    private final Properties cfg = new Properties();

    public MailService_24110171() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("mail.properties")) {
            if (in != null) {
                cfg.load(in);
            }
        } catch (Exception ignored) {
        }
    }

    public boolean sendOtp(String to, String otp) {
        String user = env("SMTP_USER", cfg.getProperty("mail.username", "")).trim();

        // Hỗ trợ cả tên cấu hình mail.password và mail.appPassword.
        String configuredPassword = cfg.getProperty("mail.password", "").trim();
        if (configuredPassword.isBlank()) {
            configuredPassword = cfg.getProperty("mail.appPassword", "").trim();
        }
        String pass = env("SMTP_PASSWORD", configuredPassword).trim();

        String configuredFrom = cfg.getProperty("mail.from", "").trim();
        if (configuredFrom.isBlank()) {
            configuredFrom = user;
        }
        String from = env("SMTP_FROM", configuredFrom).trim();

        if (user.isBlank() || pass.isBlank()) {
            System.out.println("[DEMO OTP 24110171] " + to + " => " + otp);
            return false;
        }

        try {
            Properties p = new Properties();
            p.put("mail.smtp.host", env("SMTP_HOST", cfg.getProperty("mail.host", "smtp.gmail.com")));
            p.put("mail.smtp.port", env("SMTP_PORT", cfg.getProperty("mail.port", "587")));
            p.put("mail.smtp.auth", cfg.getProperty("mail.auth", "true"));
            p.put("mail.smtp.starttls.enable", cfg.getProperty("mail.starttls", "true"));
            p.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            Session session = Session.getInstance(p, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from, "BookStore 24110171", StandardCharsets.UTF_8.name()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("OTP kích hoạt tài khoản BookStore - 24110171", StandardCharsets.UTF_8.name());
            message.setText(
                    "Mã OTP của bạn là: " + otp
                            + "\n\nOTP có hiệu lực trong 5 phút."
                            + "\nNếu bạn không thực hiện đăng ký, hãy bỏ qua email này.",
                    StandardCharsets.UTF_8.name());
            Transport.send(message);
            return true;
        } catch (Exception e) {
            System.out.println("Không gửi được mail OTP: " + e.getMessage() + ". OTP demo: " + otp);
            return false;
        }
    }

    private String env(String name, String fallback) {
        String value = System.getenv(name);
        return (value == null || value.isBlank()) ? fallback : value;
    }
}
