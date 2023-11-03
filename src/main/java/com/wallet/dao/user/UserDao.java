package com.wallet.dao.user;

import com.wallet.domain.entities.User;
import com.wallet.utility.exceptions.UserAllreadyExistsException;
import com.wallet.utility.exceptions.UserIsNotExistsException;

import java.util.Optional;

/**
 * Интерфейс, представляющий доступ к данным игрока.
 */
public interface UserDao {

    /**
     * Сохраняет информацию об игроке в базе данных.
     *
     * @param pl Объект игрока, который должен быть сохранен.
     */
    void savePlayer(User pl) throws UserAllreadyExistsException;

    /**
     * Удаляет информацию об игроке в базе данных.
     *
     * @param pl Объект игрока, который должен быть удален.
     */
    void deletePlayer(User pl) throws UserIsNotExistsException;

    /**
     * Обновляет информацию об игроке в базе данных.
     *
     * @param pl Объект игрока, который должен быть обновлен.
     */
    void updatePlayer(User pl) throws UserIsNotExistsException;

    /**
     * Загружает информацию об игроке по его логину.
     *
     * @param pLogin Логин игрока.
     * @return Объект игрока, соответствующий заданным логину, или null, если игрок не найден.
     */
    Optional<User> findPlayer(String pLogin) throws UserIsNotExistsException;

    /**
     * Загружает информацию об игроке по его id.
     *
     * @param id id игрока.
     * @return Объект игрока, соответствующий заданным id, или null, если игрок не найден.
     */
    Optional<User> findPlayer(long id) throws UserIsNotExistsException;

}
