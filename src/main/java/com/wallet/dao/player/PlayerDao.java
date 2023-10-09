package com.wallet.dao.player;

import com.wallet.entities.Player;
import com.wallet.infrastructure.UserSession;

/**
 * Интерфейс, представляющий доступ к данным игрока.
 */
public interface PlayerDao {

    /**
     * Сохраняет информацию об игроке в базе данных.
     *
     * @param pl Объект игрока, который должен быть сохранен.
     */
    void savePlayer(Player pl);

    /**
     * Загружает информацию об игроке по его логину и паролю.
     *
     * @param pLogin    Логин игрока.
     * @param pPassword Пароль игрока.
     * @return Объект игрока, соответствующий заданным логину и паролю, или null, если игрок не найден.
     */
    Player loadPlayer(String pLogin, String pPassword);

    /**
     * Загружает информацию об игроке на основе сеанса пользователя.
     *
     * @param userSession Сеанс пользователя, для которого нужно загрузить информацию об игроке.
     * @return Объект игрока, связанный с указанным сеансом пользователя, или null, если игрок не найден.
     */
    Player loadPlayer(UserSession userSession);

    /**
     * Получает имя игрока.
     *
     * @param pl Объект игрока.
     * @return Имя игрока.
     */
    String getName(Player pl);

    /**
     * Получает фамилию игрока.
     *
     * @param pl Объект игрока.
     * @return Фамилия игрока.
     */
    String getSurname(Player pl);

    /**
     * Получает идентификатор игрока.
     *
     * @param pl Объект игрока.
     * @return Идентификатор игрока.
     */
    String getPID(Player pl);

    /**
     * Устанавливает новый пароль для игрока.
     *
     * @param newPassword Новый пароль для установки.
     * @param pl          Объект игрока, для которого нужно установить новый пароль.
     */
    void setPassword(String newPassword, Player pl);
}

