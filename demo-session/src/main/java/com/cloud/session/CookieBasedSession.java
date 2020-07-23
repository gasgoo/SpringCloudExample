package com.cloud.session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CookieBasedSession {

    public static final String COOKIE_NAME_SESSION = "psession";

    public static String getRequestedSessionId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie == null) {
                continue;
            }

            if (!COOKIE_NAME_SESSION.equalsIgnoreCase(cookie.getName())) {
                continue;
            }

            return cookie.getValue();
        }
        return null;
    }

    /**
     * @Description 设置cookie
     * @Date 2020/7/23 15:49
     **/
    public static void onNewSession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        Cookie cookie = new Cookie(COOKIE_NAME_SESSION, sessionId);
        cookie.setHttpOnly(true);
        cookie.setPath(request.getContextPath() + "/");
        cookie.setDomain("dev.com");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
    }

}
