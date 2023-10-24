package com.wallet.presentation.servlets.server;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wallet.dao.transaction.TransactionDaoImpl;
import com.wallet.dao.wallet.WalletDaoImpl;
import com.wallet.presentation.servlets.client.KeycloakContextExtractor;
import com.wallet.services.walletService.WalletService;
import com.wallet.services.walletService.WalletServiceImpl;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.keycloak.KeycloakSecurityContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class WalletServletAPI extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final WalletService walletService;

    public WalletServletAPI() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.walletService = new WalletServiceImpl(new WalletDaoImpl(), new TransactionDaoImpl());
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        KeycloakSecurityContext keycloakSecurityContext = (KeycloakSecurityContext) req.getAttribute(KeycloakSecurityContext.class.getName());
        String login;
        if (keycloakSecurityContext != null) { login = keycloakSecurityContext.getIdToken().getName(); }
        else {
            Map<String, Object> keycloakContext = KeycloakContextExtractor.getSecurityContextFromAccessToken(req);
            login = keycloakContext.get("preferred_username").toString();
        }
        try (PrintWriter printWriter = resp.getWriter();)
        {
            HashMap<String, String> userInfo = walletService.getUserInfo(login);

            String json = objectMapper.writeValueAsString(userInfo);
            printWriter.println(json);
        } catch (PlayerIsNotExistsException e) {
            sendResponse(resp, HttpServletResponse.SC_NO_CONTENT, "Пользователь не найден");
        } catch (IOException e) {
            e.printStackTrace();
        }

        }
    /**
     * Отправить ответ и код статуса
     */
    private void sendResponse(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setStatus(status);
        resp.getWriter().println(message);
    }



}
