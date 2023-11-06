package com.ment09.walletservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UtilMethods {

    private final ObjectMapper objectMapper;

    public HttpServletResponse extractAccessAndRefreshTokensFromResponse(HttpServletResponse response, String responseBody) throws JsonProcessingException {
        JsonNode jsonResponse = objectMapper.readTree(responseBody);
        String accessToken = jsonResponse.get("access_token").toString().replace("\"", "");
        String accessTokenLifeSpan = jsonResponse.get("expires_in").toString();
        String refreshToken = jsonResponse.get("refresh_token").toString().replace("\"", "");
        String refreshTokenLifeSpan = jsonResponse.get("refresh_expires_in").toString();
        Cookie accessTokenCookie = new Cookie("ACCESS_TOKEN", accessToken);
        accessTokenCookie.setMaxAge(Integer.parseInt(accessTokenLifeSpan));
        Cookie refreshTokenCookie = new Cookie("REFRESH_TOKEN", refreshToken);
        refreshTokenCookie.setMaxAge(Integer.parseInt(refreshTokenLifeSpan));
        accessTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setHttpOnly(true);
        accessTokenCookie.setDomain("localhost");
        refreshTokenCookie.setDomain("localhost");
        accessTokenCookie.setSecure(true);
        refreshTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        refreshTokenCookie.setPath("/");
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        return response;
    }
}
