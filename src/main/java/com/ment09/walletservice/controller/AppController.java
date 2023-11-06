package com.ment09.walletservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ment09.walletservice.util.UtilMethods;
import com.ment09.walletservice.util.requests.AuthTokenRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AppController {

    private final AuthTokenRequest authTokenRequest;
    private final UtilMethods utilMethods;
    private final ObjectMapper objectMapper;

    @GetMapping("/")
    public String getHome() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/secured")
    public String getSecuredIndex() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            System.out.println(jwt.getHeaders());
            System.out.println(jwt.getClaims());
        }

        return "index-secured";
    }

    @GetMapping(value = "/auth/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public String authorizePage() {
        return "login";
    }

    @PostMapping(value = "/auth/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authorize(@RequestBody Map<String, Object> body, HttpServletResponse response) throws JsonProcessingException {
        String authResponseBody = authTokenRequest.getAuthToken(body).getBody();
        utilMethods.extractAccessAndRefreshTokensFromResponse(response, authResponseBody);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Успешная авторизация");
        return ResponseEntity.ok().body(objectMapper.writeValueAsString(responseBody));
    }
}
