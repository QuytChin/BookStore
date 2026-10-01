package vn.edu.hcmute.bookstore.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class JPAUtil_24110171 {
    private static final EntityManagerFactory EMF = buildEntityManagerFactory();
    private JPAUtil_24110171() {}

    private static EntityManagerFactory buildEntityManagerFactory() {
        try {
            Properties p = new Properties();
            try (InputStream in = JPAUtil_24110171.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (in != null) p.load(in);
            }
            Map<String, Object> props = new HashMap<>();
            props.put("jakarta.persistence.jdbc.url", env("DB_URL", p.getProperty("db.url")));
            props.put("jakarta.persistence.jdbc.user", env("DB_USER", p.getProperty("db.user")));
            props.put("jakarta.persistence.jdbc.password", env("DB_PASSWORD", p.getProperty("db.password")));
            props.put("jakarta.persistence.jdbc.driver", env("DB_DRIVER", p.getProperty("db.driver")));
            props.put("hibernate.dialect", env("DB_DIALECT", p.getProperty("db.dialect")));
            props.put("hibernate.hbm2ddl.auto", env("DB_DDL", p.getProperty("hibernate.hbm2ddl.auto", "validate")));
            props.put("hibernate.show_sql", env("HIBERNATE_SHOW_SQL", "false"));
            return Persistence.createEntityManagerFactory("BookStorePU", props);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Không khởi tạo được JPA: " + e.getMessage());
        }
    }

    private static String env(String name, String fallback) {
        String value = System.getenv(name);
        return (value == null || value.isBlank()) ? fallback : value;
    }

    public static EntityManager createEntityManager() { return EMF.createEntityManager(); }
    public static void close() { if (EMF.isOpen()) EMF.close(); }
}
