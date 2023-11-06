package com.ment09.walletservice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ment09.walletservice.util.UtilMethods;
import com.ment09.walletservice.util.requests.IntrospectTokenRequest;
import com.ment09.walletservice.util.requests.RefreshTokenRequest;
import com.ment09.walletservice.util.urlencodedtemplates.CookieExtractor;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

/**
 * Фильтр, проверяющий JWT токен пользователя на валидность, и, в случае чего, при наличии актуального Refresh токена выдает новый.
*/
@Component
@RequiredArgsConstructor
public class CookieTokenFilter extends OncePerRequestFilter {

    private final CookieExtractor extractFromCookie;
    private final RefreshTokenRequest refreshTokenRequest;
    private final IntrospectTokenRequest introspectTokenRequest;
    private final ObjectMapper objectMapper;
    private final UtilMethods utilMethods;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> optionalAccessToken = extractFromCookie.extractValue(request, "ACCESS_TOKEN");
        System.out.println("Начинаем проверку токена...");
        if (optionalAccessToken.isPresent()) {
            System.out.println("Токен существует. Проводим интроспекцию...");
            //boolean healthy = healthyToken(request);
            boolean healthy = tokenLifeSpanHealthCheck(optionalAccessToken.get());
            if (healthy) {
                System.out.println("Токен здоров.");
                TokenAuthenticationWrapper requestWrapper = new TokenAuthenticationWrapper(request, optionalAccessToken.get());
                filterChain.doFilter(requestWrapper, response);
            }
            else {
                System.out.println("Токен недействителен. Запрашиваем новый");
                refreshToken(request, response, filterChain);
            }
        }
        else  {
            System.out.println("Токен не найден. Ищем рефреш токен.");
            refreshToken(request, response, filterChain);
        }
    }

    /**
     * Метод ищет Refresh токен в куки запроса и выдает новые токены
     */
    private void refreshToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        ResponseEntity<String> refreshResponse;
        try {
            String refreshToken = extractFromCookie.extractValue(request, "REFRESH_TOKEN").orElseThrow(NoSuchFieldError::new);
            refreshResponse = refreshTokenRequest.refreshToken(request);
        } catch (HttpClientErrorException | NoSuchFieldError e) {
            refreshResponse = new ResponseEntity<>(HttpStatusCode.valueOf(401));
        }
        if (refreshResponse.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            System.out.println("Новый токен успешно запрошен.");
            JsonNode jsonResponse = objectMapper.readTree(refreshResponse.getBody());
            String accessToken = jsonResponse.get("access_token").toString().replace("\"", "");
            TokenAuthenticationWrapper requestWrapper = new TokenAuthenticationWrapper(request, accessToken);
            utilMethods.extractAccessAndRefreshTokensFromResponse(response, refreshResponse.getBody());
            filterChain.doFilter(requestWrapper, response);
        }
        else {
            System.out.println("Рефреш-токен не действителен.");
            filterChain.doFilter(request, response);
        }
    }

    /**
     * Метод отправляет токен на интроспекцию
     */
    private boolean healthyToken(HttpServletRequest request) throws JsonProcessingException {
        return introspectTokenRequest.introspectAccessToken(request);
    }

    /**
     * Метод проверяет срок жизни токена
     */
    private boolean tokenLifeSpanHealthCheck(String accessToken) {
        try {
            JWT token = JWTParser.parse(accessToken);
            Instant exp = token.getJWTClaimsSet().getExpirationTime().toInstant();
            Instant current = Instant.now();
            return (exp != null && current.isBefore(exp));
        } catch (Exception e) {
            return false;
        }
    }

}