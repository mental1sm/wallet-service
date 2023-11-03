package com.wallet.service.account;

import com.wallet.domain.dto.user.UserRegistrationDTO;
import com.wallet.domain.entities.Wallet;
import com.wallet.utility.exceptions.UserAllreadyExistsException;
import com.wallet.utility.exceptions.UserIsNotExistsException;

import java.util.ArrayList;

/**
 * Представляет класс для управления аккаунтом пользователя.
*/
public interface AccountService {
    /**
     * Метод, регистрирующий пользователя.
     *
     * @param userRegistrationDTO Дто регистрации пользователя
     */
    void regUser(UserRegistrationDTO userRegistrationDTO) throws UserAllreadyExistsException, UserIsNotExistsException;
    ArrayList<Wallet> getWalletsOfUser(String login) throws UserIsNotExistsException;
}
