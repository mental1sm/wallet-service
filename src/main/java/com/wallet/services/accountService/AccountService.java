package com.wallet.services.accountService;

import com.wallet.infrastructure.UserSession;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;

import java.util.Optional;

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
     * @return Объект UserSession - сессия пользователя
     */
    Optional<UserSession> regUser(String name, String surname, String pLogin, String pPassword) throws PlayerAllreadyExistsException;

    /**
     * Метод, аутентифицирующий пользователя.
     *
     * @param pLogin    Уникальный логин игрока
     * @param pPassword Пароль от аккаунта
     * @return Объект UserSession - сессия пользователя
     */
    Optional<UserSession> authUser(String pLogin, String pPassword);
}
