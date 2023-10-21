package com.wallet.presentation.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.dao.wallet.WalletDaoImpl;
import com.wallet.domain.dto.player.PlayerRegistrationDTO;
import com.wallet.in.UserRegInputHandler;
import com.wallet.infrastructure.configs.DatabaseConfig;
import com.wallet.services.accountService.AccountService;
import com.wallet.services.accountService.AccountServiceImpl;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;
import com.wallet.utility.exceptions.SomeFieldsIsEmptyException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;


public class RegistrationServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final AccountService accountService;

    public RegistrationServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.accountService = new AccountServiceImpl(new PlayerDaoImpl(), new WalletDaoImpl());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String jsonRequestBody = req.getReader().lines().collect(Collectors.joining());
        PlayerRegistrationDTO registrationDTO = objectMapper.readValue(jsonRequestBody, PlayerRegistrationDTO.class);
        String login = registrationDTO.getLogin();
        String password = registrationDTO.getPassword();
        String name = registrationDTO.getName();
        String surname = registrationDTO.getSurname();
        try {
            UserRegInputHandler.checkRegInput(registrationDTO);
            accountService.regUser(name, surname, login, password);
            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getWriter(), "Успешно регистрирован");
        } catch (PlayerAllreadyExistsException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), "Пользователь с таким логином уже зарегистрирован");
        } catch (PlayerIsNotExistsException | SomeFieldsIsEmptyException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), "Что-то пошло не так при регистрации. Проверьте заполненность полей.");
        }
    }
}
