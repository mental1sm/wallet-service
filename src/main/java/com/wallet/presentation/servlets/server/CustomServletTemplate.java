package com.wallet.presentation.servlets.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wallet.presentation.servlets.client.util.KeycloakContextExtractor;
import com.wallet.utility.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.keycloak.KeycloakSecurityContext;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomServletTemplate extends HttpServlet {
    protected final ObjectMapper objectMapper;

    public CustomServletTemplate() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }
    /**
     * Преобразование json в словарь
     */
    protected Map<String, Object> readJson(HttpServletRequest request) throws IOException {
        String jsonBody = request.getReader().lines().collect(Collectors.joining());
        JsonNode node = objectMapper.readTree(jsonBody);

        return objectMapper.convertValue(node, new TypeReference<Map<String, Object>>() {});
    }

    /**
     * Отправить ответ и код статуса
     */
    protected void sendResponse(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setStatus(status);
        resp.getWriter().println(message);
    }

    /**
     * Выдает логин пользователя при наличии Keycloak контекста или же access token
     * @param req Запрос на сервлет
     * @return String логин пользователя
     */
    protected static Optional<String> getLogin(HttpServletRequest req) throws UnauthorizedException {
        KeycloakSecurityContext keycloakSecurityContext = (KeycloakSecurityContext) req.getAttribute(KeycloakSecurityContext.class.getName());
        String _login;
        if (keycloakSecurityContext != null) {
            _login = keycloakSecurityContext.getIdToken().getName();
        } else {
            Map<String, Object> keycloakContext = null;
            try {
                keycloakContext = KeycloakContextExtractor.getSecurityContextFromAccessToken(req);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
            _login = keycloakContext.get("preferred_username").toString();
        }
        return Optional.ofNullable(_login);
    }
}
