package com.wallet.dao.player;

import com.wallet.entities.Player;
import com.wallet.infrastructure.UserSession;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;

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
    void deletePlayer(Player pl);

    /**
     * Обновляет информацию об игроке в базе данных.
     *
     * @param pl Объект игрока, который должен быть обновлен.
     */
    void updatePlayer(Player pl);

    /**
     * Загружает информацию об игроке по его логину.
     *
     * @param pLogin    Логин игрока.
     * @return Объект игрока, соответствующий заданным логину, или null, если игрок не найден.
     */
    Player findPlayer(String pLogin);

    /**
     * Загружает информацию об игроке по его id.
     *
     * @param id    id игрока.
     * @return Объект игрока, соответствующий заданным id, или null, если игрок не найден.
     */
    Player findPlayer(long id);

    /**
     * Загружает информацию об игроке на основе сеанса пользователя.
     *
     * @param userSession Сеанс пользователя, для которого нужно загрузить информацию об игроке.
     * @return Объект игрока, связанный с указанным сеансом пользователя, или null, если игрок не найден.
     */
    Player findPlayer(UserSession userSession);

}
