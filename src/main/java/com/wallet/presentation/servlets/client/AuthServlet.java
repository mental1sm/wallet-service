package com.wallet.presentation.servlets.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wallet.domain.dto.player.PlayerAuthDTO;
import com.wallet.infrastructure.configs.KeycloakConfig;
import com.wallet.presentation.servlets.client.util.TokenHandler;
import com.wallet.presentation.servlets.server.CustomServletTemplate;
import com.wallet.utility.aspects.SpeedTest;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервлет авторизации
*/
public class AuthServlet extends CustomServletTemplate {
    ObjectMapper objectMapper;

    public AuthServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    /**
     * Авторизует пользователя и отдает ему bearer Access Token
     * login
     * password
    */
    @SpeedTest
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String jsonRequestBody = req.getReader().lines().collect(Collectors.joining());
        PlayerAuthDTO playerAuthDTO = objectMapper.readValue(jsonRequestBody, PlayerAuthDTO.class);
        Optional<String> access_token = getAccessToken(playerAuthDTO.getLogin(), playerAuthDTO.getPassword());
        if (access_token.isEmpty()) {
            sendResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Something went wrong!");
        }
        else {
            resp.setStatus(HttpServletResponse.SC_OK);
            Map<String, String> data = new HashMap<>();
            data.put("access_token", access_token.get());
            String json = objectMapper.writeValueAsString(data);
            sendResponse(resp, HttpServletResponse.SC_OK, json);
        }

    }

    /**
     * Обратиться к серверу аутентификации для получения токена пользователя.
     * @param login - Логин, полученный из json-строки запроса.
     * @param password - Пароль, полученный из json-строки запроса.
     * @return Access Token в виде строки.
     */
    @SpeedTest
    private Optional<String> getAccessToken(String login, String password) throws IOException {
        URL url = new URL("http://localhost:8282/realms/wallet-realm/protocol/openid-connect/token");
        String secret = KeycloakConfig.getInstance().getProperty("secret");
        String encodedString = String.format(
                "grant_type=password&client_id=wallet-client&username=%s&password=%s&client_secret=%s",
                URLEncoder.encode(login, StandardCharsets.UTF_8),
                URLEncoder.encode(password, StandardCharsets.UTF_8),
                URLEncoder.encode(secret, StandardCharsets.UTF_8));

        return TokenHandler.getToken(url, encodedString);
    }


}
