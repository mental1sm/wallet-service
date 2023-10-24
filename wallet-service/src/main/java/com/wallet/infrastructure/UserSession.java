package com.wallet.infrastructure;

import com.wallet.utility.IdGenerator;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Класс UserSession представляет собой сессию пользователя, связанную с определенным игроком.
 * Каждая сессия имеет уникальный идентификатор сессии (sessionId) и связана с идентификатором игрока (playerID).
 */
public class UserSession {

    /**
     * Уникальный идентификатор сессии пользователя.
     */
    private UUID sessionId;

    /**
     * Идентификатор игрока, связанный с данной сессией.
     */
    @Getter
    private final long playerID;

    @Getter @Setter
    private int currentWalletNum;

    /**
     * Конструктор класса UserSession создает новую сессию пользователя с уникальным идентификатором
     * сессии (sessionId) и связывает ее с идентификатором игрока (playerID).
     *
     * @param playerID Идентификатор игрока, связываемый с данной сессией.
     */
    public UserSession(long playerID) {
        sessionId = IdGenerator.genId();
        this.playerID = playerID;
    }
}
