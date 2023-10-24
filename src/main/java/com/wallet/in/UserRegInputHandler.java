package com.wallet.in;

import com.wallet.domain.dto.player.PlayerRegistrationDTO;
import com.wallet.presentation.Localisation;
import com.wallet.utility.exceptions.SomeFieldsIsEmptyException;

import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Filter;

/**
 * Отвечает за ввод пользователя, связанный с регистрацией.
 */
public class UserRegInputHandler {

    /**
     * Перехватывает управление вводом пользователя и бросает ошибку, если данные в неправильном формате
     */
    public static void checkRegInput(PlayerRegistrationDTO registrationDTO) throws SomeFieldsIsEmptyException {
        Optional<String> login = Optional.ofNullable(registrationDTO.getLogin());
        Optional<String> password = Optional.ofNullable(registrationDTO.getPassword());
        Optional<String> name = Optional.ofNullable(registrationDTO.getName());
        Optional<String> surname = Optional.ofNullable(registrationDTO.getSurname());
        Optional<String> email = Optional.ofNullable(registrationDTO.getEmail());
        if (login.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty() || email.isEmpty()) {
            throw new SomeFieldsIsEmptyException();
        }
    }
}
