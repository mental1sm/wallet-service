package com.wallet.presentation.servlets.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wallet.dao.transaction.TransactionDaoImpl;
import com.wallet.dao.wallet.WalletDaoImpl;
import com.wallet.services.walletService.WalletService;
import com.wallet.services.walletService.WalletServiceImpl;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;
import com.wallet.utility.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WalletServletAPI extends CustomServletTemplate {
    private final ObjectMapper objectMapper;
    private final WalletService walletService;

    public WalletServletAPI() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.walletService = new WalletServiceImpl(new WalletDaoImpl(), new TransactionDaoImpl());
    }

    /**
     * Возвращает информацию о кошельке пользователя
     * "wallet_id":
     **/
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try
        {
            String _login = getLogin(req).orElseThrow(PlayerIsNotExistsException::new);
            Map<String, Object> receivedData = readJson(req);
            long walletId = Long.parseLong(receivedData.get("wallet_id").toString());
            HashMap<String, String> userInfo = walletService.getUserInfo(_login, walletId);
            System.out.println(walletId);
            System.out.println(userInfo);
            String json = objectMapper.writeValueAsString(userInfo);
            sendResponse(resp, HttpServletResponse.SC_OK, json);
        } catch (PlayerIsNotExistsException e) {
            sendResponse(resp, HttpServletResponse.SC_NO_CONTENT, "Пользователь не найден");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnauthorizedException e) {
            sendResponse(resp, HttpServletResponse.SC_UNAUTHORIZED, "Ваши авторизационные неверны или устарели.");
        }

    }
}
