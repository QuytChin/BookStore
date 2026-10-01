package vn.edu.hcmute.bookstore.config;
import jakarta.servlet.ServletContextEvent;import jakarta.servlet.ServletContextListener;import jakarta.servlet.annotation.WebListener;
@WebListener
public class AppLifecycleListener_24110171 implements ServletContextListener{
    @Override public void contextDestroyed(ServletContextEvent sce){JPAUtil_24110171.close();}
}
