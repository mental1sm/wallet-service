package com.wallet.dao.player;

import com.wallet.domain.entities.Player;
import com.wallet.infrastructure.UserSession;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Интерфейс, представляющий доступ к данным игрока.
 */
public interface PlayerDao {

    /**
     * Сохраняет информацию об игроке в базе данных.
     *
     * @param pl Объект игрока, который должен быть сохранен.
     */
    void savePlayer(Player pl) throws PlayerAllreadyExistsException;

    /**
     * Удаляет информацию об игроке в базе данных.
     *
     * @param pl Объект игрока, который должен быть удален.
     */
    void deletePlayer(Player pl) throws PlayerIsNotExistsException;

    /**
     * Обновляет информацию об игроке в базе данных.
     *
     * @param pl Объект игрока, который должен быть обновлен.
     */
    void updatePlayer(Player pl) throws PlayerIsNotExistsException;

    /**
     * Загружает информацию об игроке по его логину.
     *
     * @param pLogin Логин игрока.
     * @return Объект игрока, соответствующий заданным логину, или null, если игрок не найден.
     */
    Optional<Player> findPlayer(String pLogin) throws PlayerIsNotExistsException;

    /**
     * Загружает информацию об игроке по его id.
     *
     * @param id id игрока.
     * @return Объект игрока, соответствующий заданным id, или null, если игрок не найден.
     */
    Optional<Player> findPlayer(long id) throws PlayerIsNotExistsException;

    /**
     * Загружает информацию об игроке на основе сеанса пользователя.
     *
     * @param userSession Сеанс пользователя, для которого нужно загрузить информацию об игроке.
     * @return Объект игрока, связанный с указанным сеансом пользователя, или null, если игрок не найден.
     */
    Optional<Player> findPlayer(UserSession userSession) throws PlayerIsNotExistsException;

}
