package com.wallet.presentation.servlets.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.dao.wallet.WalletDaoImpl;
import com.wallet.domain.dto.player.PlayerRegistrationDTO;
import com.wallet.in.UserRegInputHandler;
import com.wallet.infrastructure.configs.KeycloakConfig;
import com.wallet.presentation.servlets.client.TokenHandler;
import com.wallet.services.accountService.AccountService;
import com.wallet.services.accountService.AccountServiceImpl;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;
import com.wallet.utility.exceptions.SomeFieldsIsEmptyException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.keycloak.KeycloakSecurityContext;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServletAPI extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final AccountService accountService;

    public UserServletAPI() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.accountService = new AccountServiceImpl(new PlayerDaoImpl(), new WalletDaoImpl());
    }

    /**
     * Регистрация нового пользователя
    */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            String jsonRequestBody = req.getReader().lines().collect(Collectors.joining());
            PlayerRegistrationDTO registrationDTO = objectMapper.readValue(jsonRequestBody, PlayerRegistrationDTO.class);
            UserRegInputHandler.checkRegInput(registrationDTO);

            Optional<String> adminToken = getAccessToken();
            if (adminToken.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                return;
            }

            int responseCode = regUserInRealm(registrationDTO, adminToken.get());

            if (responseCode == HttpServletResponse.SC_CREATED) {
                accountService.regUser(
                        registrationDTO.getName(),
                        registrationDTO.getSurname(),
                        registrationDTO.getLogin(),
                        registrationDTO.getEmail(),
                        registrationDTO.getPassword());
                sendResponse(resp, responseCode, "Успешно зарегистрирован");
            } else {
                throw new PlayerIsNotExistsException();
            }
        } catch (PlayerAllreadyExistsException e) {
            sendResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Пользователь с таким логином уже зарегистрирован");
        } catch (PlayerIsNotExistsException | SomeFieldsIsEmptyException e) {
            sendResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Что-то пошло не так при регистрации. Проверьте заполненность полей.");
        } catch (IOException e) {
            sendResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Что-то пошло не так при регистрации. Проверьте формат отправляемых данных.");

        }
    }


    /**
     * Отправить ответ и код статуса
    */
    private void sendResponse(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setStatus(status);
        resp.getWriter().println(message);
    }

    /**
     * Зарегестрировать пользователя в realm сервиса
    */
    private int regUserInRealm(PlayerRegistrationDTO playerRegistrationDTO, String adminToken) throws IOException {
        URL url = new URL("http://localhost:8282/admin/realms/wallet-realm/users");
        ObjectNode json = objectMapper.createObjectNode();
        json.put("username", playerRegistrationDTO.getLogin());
        json.put("email", playerRegistrationDTO.getEmail());
        json.put("firstName", playerRegistrationDTO.getName());
        json.put("lastName", playerRegistrationDTO.getSurname());
        json.put("enabled", "true");

        ArrayNode credentialsNode = objectMapper.createArrayNode();
        ObjectNode credentialsObject = objectMapper.createObjectNode();
        credentialsObject.put("type", "password");
        credentialsObject.put("value", playerRegistrationDTO.getPassword());
        credentialsNode.add(credentialsObject);
        json.set("credentials", credentialsNode);

        String jsonString = objectMapper.writeValueAsString(json);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + adminToken);
        connection.setDoOutput(true);
        try (OutputStream oStream = connection.getOutputStream();) {
            byte[] input = jsonString.getBytes();
            oStream.write(input, 0, input.length);
        }

        return connection.getResponseCode();

    }

    /**
     * Обратиться к серверу аутентификации для получения токена админа
     */
    private Optional<String> getAccessToken() throws IOException {
        URL url = new URL("http://localhost:8282/realms/master/protocol/openid-connect/token");
        KeycloakConfig config = KeycloakConfig.getInstance();
        String secret = config.getProperty("secret");
        String adminUsername = config.getProperty("admin_username");
        String adminPassword = config.getProperty("admin_password");
        String encodedString = String.format(
                "grant_type=password&client_id=admin-cli&username=%s&password=%s&client_secret=%s",
                URLEncoder.encode(adminUsername, StandardCharsets.UTF_8),
                URLEncoder.encode(adminPassword, StandardCharsets.UTF_8),
                URLEncoder.encode(secret, StandardCharsets.UTF_8));

        return TokenHandler.getToken(url, encodedString);
    }
}
