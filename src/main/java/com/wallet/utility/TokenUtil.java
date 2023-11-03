package com.wallet.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.config.KeycloakConfig;
import com.wallet.domain.dto.user.UserAuthDTO;
import com.wallet.utility.encoded.builder.EncodedBodyBuilder;
import javax.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class TokenUtil {
    private final EncodedBodyBuilder encodedBodyBuilder;
    private final KeycloakConfig keycloakConfig;
    /**
     * Возвращает токен от сервера авторизации
     * @param userAuthDTO ДТО-объект авторизации пользователя
     * @return Токен авторизации или null
    */
    public Optional<String> getAuthToken(UserAuthDTO userAuthDTO) throws IOException {
        URL url = new URL(keycloakConfig.getClientUrl());
        String encodedString = encodedBodyBuilder.authEncodedString(userAuthDTO);
        return getToken(url, encodedString);
    }

    /**
     * Отправляет на url-адрес сервера авторизации закодированную форму
     * @return Ответ от сервера
    */
    private Optional<String> getToken(URL url, String encodedString) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", MediaType.APPLICATION_FORM_URLENCODED);
        connection.setDoOutput(true);
        try (OutputStream oStream = connection.getOutputStream()){
            byte[] input = encodedString.getBytes();
            oStream.write(input, 0, input.length);
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())))
            {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.toString());
                String token = jsonNode.get("access_token").asText();
                return Optional.of(token);
            }
        }

        return Optional.empty();
    }

    /**
     * Обратиться к серверу аутентификации для получения токена админа
     */
    public Optional<String> getAdminToken() throws IOException {
        URL url = new URL(keycloakConfig.getAdminUrl());
        String encodedString = encodedBodyBuilder.adminAuthEncodedString();
        return getToken(url, encodedString);
    }

}
