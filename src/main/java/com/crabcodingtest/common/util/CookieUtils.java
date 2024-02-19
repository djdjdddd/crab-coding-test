package com.crabcodingtest.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.util.Base64;
import java.util.Optional;

/**
 * @author : yong
 * @fileName : CookieUtils
 * @date : 2024-02-19
 * @description : 웹 애플리케이션에서 쿠키를 다루는 클래스
 * 참조 : https://github.com/changheedev/spring-security-jwt-social/blob/master/docs/Login.md
 */
public class CookieUtils {

    // 웹상의 많은 자료들이 LocalStorage 를 이용해 토큰을 저장하는 방식을 소개하고 있는데 LocalStorage 는 javascript 코드로 접근이 가능하기 때문에 XSS 공격에 취약하다는 단점이 있기 때문에 중요한 데이터를 보관하는 장소로는 사용하지 않는 것을 권장하고 있습니다.
    // 반면에 쿠키는 httpOnly 옵션을 사용하면 http 통신 상에서만 쿠키가 사용되어 javascript 코드를 통한 접근을 막을 수 있으며, secure 옵션을 사용하면 https 통신에서만 쿠키를 전송하게 되어 보안을 더 강화할 수 있습니다.
    // 물론, 쿠키는 CSRF (Cross Site Request Forgery - 사이트 간 요청 위조) 공격에 노출될 수 있지만 XSS 공격에 비해 완벽한 대비가 가능합니다. 다만, 유출 되었을 때 위험도가 큰 Refresh-Token 을 보관하는 용도로는 쿠키가 적절하지 않기 때문에 Refresh-Token 의 사용은 포기하고 Access-Token 의 만료기간을 좀 더 늘려주는 방향으로 구현하게 되었습니다.

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return Optional.of(cookie);
                }
            }
        }

        return Optional.empty();
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        addCookie(response, name, value, false, false, maxAge);
    }

    public static void addCookie(HttpServletResponse response, String name, String value, boolean httpOnly, boolean secure, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(httpOnly); // 반면에 쿠키는 httpOnly 옵션을 사용하면 http 통신 상에서만 쿠키가 사용되어 javascript 코드를 통한 접근을 막을 수 있으며, secure 옵션을 사용하면 https 통신에서만 쿠키를 전송하게 되어 보안을 더 강화할 수 있습니다.
        cookie.setSecure(secure);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }

}
