package com.wallet.in.validators;

import com.wallet.domain.dto.user.UserRegistrationDTO;
import com.wallet.utility.exceptions.SomeFieldsIsEmptyException;

import java.util.Optional;

/**
 * Отвечает за ввод пользователя, связанный с регистрацией.
 */
public class UserRegInputHandler {

    /**
     * Перехватывает управление вводом пользователя и бросает ошибку, если данные в неправильном формате
     */
    public static void checkRegInput(UserRegistrationDTO registrationDTO) throws SomeFieldsIsEmptyException {
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
