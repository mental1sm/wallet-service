package com.ment09.walletservice.config;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.oauth2.jwt.JwtValidators;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CookieTokenFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromCookie(request);
        if (token != null) {
            TokenAuthenticationWrapper requestWrapper = new TokenAuthenticationWrapper(request, token);
            filterChain.doFilter(requestWrapper, response);
        }
        else {
            filterChain.doFilter(request, response);
        }
    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            String tokenCookieName = "ACCESS_TOKEN";

            for (Cookie cookie : cookies) {
                if (Objects.equals(cookie.getName(), tokenCookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
