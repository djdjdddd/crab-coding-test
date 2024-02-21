package com.crabcodingtest.common.filter;

import com.crabcodingtest.common.util.CookieUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

/**
 * @author : yong
 * @fileName : StatelessCSRFFilter
 * @date : 2024-02-18
 * @description :
 */
public class StatelessCSRFFilter extends OncePerRequestFilter {

    // 1. 스프링 시큐리티 깃허브 : https://github.com/spring-projects/spring-security/blob/main/web/src/main/java/org/springframework/security/web/csrf/CsrfFilter.java
    // 2. 창희 깃허브 : https://github.com/changheedev/spring-security-jwt-social/blob/master/docs/Security.md

    public static final String CSRF_TOKEN = "CSRF-TOKEN";
    public static final String X_CSRF_TOKEN = "X-CSRF-TOKEN";
    private final RequestMatcher requireCsrfProtectionMatcher = new DefaultRequiresCsrfMatcher();
    private final AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // csrf 보호가 필요한 method 인지 확인
        if(requireCsrfProtectionMatcher.matches(request)){
            Optional<String> optionalCsrfToken = Optional.ofNullable(request.getHeader(X_CSRF_TOKEN));
            Optional<Cookie> optionalCsrfCookie = CookieUtils.getCookie(request, CSRF_TOKEN);

            // CookieUtils 클래스는 다른 사람 코드를 그대로 복붙한 거라 검증되지 않았음..
            if (!optionalCsrfCookie.isPresent() || !optionalCsrfToken.isPresent() || !optionalCsrfToken.get().equals(optionalCsrfCookie.get().getValue())) {
                accessDeniedHandler.handle(request, response, new AccessDeniedException(
                        "CSRF 토큰이 유효하지 않습니다."));
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    // 스프링 시큐리티 깃허브에서 그대로 참조하였음
    // https://github.com/spring-projects/spring-security/blob/main/web/src/main/java/org/springframework/security/web/csrf/CsrfFilter.java
    private static final class DefaultRequiresCsrfMatcher implements RequestMatcher {

        private final HashSet<String> allowedMethods = new HashSet<>(Arrays.asList("GET", "HEAD", "TRACE", "OPTIONS"));

        @Override
        public boolean matches(HttpServletRequest request) {
            return !this.allowedMethods.contains(request.getMethod());
        }

        @Override
        public String toString() {
            return "CsrfNotRequired " + this.allowedMethods;
        }

    }
}
