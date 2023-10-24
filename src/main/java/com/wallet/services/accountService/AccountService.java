package com.wallet.services.accountService;

import com.wallet.domain.entities.Wallet;
import com.wallet.utility.exceptions.InvalidPasswordException;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;

import java.util.ArrayList;

/**
 * Представляет класс для управления аккаунтом пользователя.
*/
public interface AccountService {
    /**
     * Метод, регистрирующий пользователя.
     *
     * @param name      Имя игрока
     * @param surname   Фамилий игрока
     * @param pLogin    Уникальный логин игрока
     * @param pPassword Пароль от аккаунта
     * @return
     */
    boolean regUser(String name, String surname, String pLogin, String email, String pPassword) throws PlayerAllreadyExistsException, PlayerIsNotExistsException;

    ArrayList<Wallet> getWalletsOfUser(String login) throws PlayerIsNotExistsException;
}
