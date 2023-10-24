package com.wallet.in;

import com.wallet.domain.dto.player.PlayerRegistrationDTO;
import com.wallet.presentation.Localisation;
import com.wallet.utility.exceptions.SomeFieldsIsEmptyException;

import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

/**
 * Отвечает за ввод пользователя, связанный с авторизацией
 */
public class UserAuthInputHandler {
    /**
     * Перехватывает управление вводом пользователя и бросает ошибку, если данные в неправильном формате
     */
    public static void checkAuthInput(PlayerRegistrationDTO registrationDTO) throws SomeFieldsIsEmptyException {
        Optional<String> login = Optional.ofNullable(registrationDTO.getLogin());
        Optional<String> password = Optional.ofNullable(registrationDTO.getPassword());
        if (login.isEmpty() || password.isEmpty()) {
            throw new SomeFieldsIsEmptyException();
        }
    }
}
