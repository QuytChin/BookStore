package vn.edu.hcmute.bookstore.util;

import jakarta.servlet.http.HttpServletRequest;

public final class WebUtil_24110171 {
    private WebUtil_24110171() {}
    public static int intParam(HttpServletRequest req, String name, int fallback) {
        try { return Integer.parseInt(req.getParameter(name)); }
        catch (Exception e) { return fallback; }
    }
    public static String trim(String value) { return value == null ? "" : value.trim(); }
}
