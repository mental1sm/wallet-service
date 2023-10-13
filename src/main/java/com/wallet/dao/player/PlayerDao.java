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
     * Загружает информацию об игроке по его логину и паролю.
     *
     * @param pLogin    Логин игрока.
     * @param pPassword Пароль игрока.
     * @return Объект игрока, соответствующий заданным логину и паролю, или null, если игрок не найден.
     */
    Player findPlayer(String pLogin, String pPassword) throws PlayerIsNotExistsException;

    /**
     * Загружает информацию об игроке на основе сеанса пользователя.
     *
     * @param userSession Сеанс пользователя, для которого нужно загрузить информацию об игроке.
     * @return Объект игрока, связанный с указанным сеансом пользователя, или null, если игрок не найден.
     */
    Player findPlayer(UserSession userSession);

}
