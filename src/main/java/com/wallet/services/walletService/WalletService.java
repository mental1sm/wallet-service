package com.wallet.services.walletService;


import com.wallet.entities.Transaction;
import com.wallet.infrastructure.UserSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Интерфейс WalletService представляет собой сервис для управления кошельками пользователей.
 * Он предоставляет методы для внесения и снятия денег, проверки баланса, получения информации о пользователе
 * и получения истории транзакций.
 */
public interface WalletService {

    /**
     * Метод depositMoney позволяет внести деньги на кошелек пользователя.
     *
     * @param session Сессия пользователя, для которой выполняется операция.
     * @param amount  Сумма денег, которую нужно внести на кошелек.
     */
    void depositMoney(UserSession session, BigDecimal amount);

    /**
     * Метод withdrawMoney позволяет снять деньги с кошелька пользователя.
     *
     * @param session Сессия пользователя, для которой выполняется операция.
     * @param amount  Сумма денег, которую нужно снять с кошелька.
     */
    void withdrawMoney(UserSession session, BigDecimal amount);

    /**
     * Метод checkMoneyAmount позволяет проверить текущий баланс на кошельке пользователя.
     *
     * @param session Сессия пользователя, для которой выполняется операция.
     * @return Текущий баланс на кошельке пользователя.
     */
    BigDecimal checkMoneyAmount(UserSession session);

    /**
     * Метод getUserInfo позволяет получить информацию о пользователе.
     *
     * @param session Сессия пользователя, для которой выполняется операция.
     * @return HashMap, содержащая информацию о пользователе, например, имя и фамилию.
     */
    HashMap<String, String> getUserInfo(UserSession session);

    /**
     * Метод getTransactionHistory позволяет получить историю транзакций пользователя.
     *
     * @param session Сессия пользователя, для которой выполняется операция.
     * @return Список транзакций, совершенных пользователем.
     */
    ArrayList<Transaction> getTransactionHistory(UserSession session);
}