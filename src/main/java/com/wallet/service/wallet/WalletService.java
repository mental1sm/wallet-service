package com.wallet.service.wallet;


import com.wallet.domain.entities.Transaction;
import com.wallet.domain.entities.Wallet;
import com.wallet.utility.exceptions.UserIsNotExistsException;
import com.wallet.utility.exceptions.WalletIsNotExistsException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 * Интерфейс WalletService представляет собой сервис для управления кошельками пользователей.
 * Он предоставляет методы для внесения и снятия денег, проверки баланса, получения информации о пользователе
 * и получения истории транзакций.
 */
public interface WalletService {

    /**
     * Метод depositMoney позволяет внести деньги на кошелек пользователя.
     *
     * @param walletId id кошелька
     * @param amount  Сумма денег, которую нужно внести на кошелек.
     */
    void depositMoney(Wallet wallet, BigDecimal amount) throws UserIsNotExistsException;

    /**
     * Метод withdrawMoney позволяет снять деньги с кошелька пользователя.
     *
     * @param wallet кошелек
     * @param amount  Сумма денег, которую нужно снять с кошелька.
     */
    void withdrawMoney(Wallet wallet, BigDecimal amount) throws UserIsNotExistsException;

    /**
     * Метод checkMoneyAmount позволяет проверить текущий баланс на кошельке пользователя.
     *
     * @param wallet кошелек
     * @return Текущий баланс на кошельке пользователя.
     */
    BigDecimal checkMoneyAmount(Wallet wallet) throws UserIsNotExistsException;

    /**
     * Метод getUserInfo позволяет получить информацию о пользователе.
     *
     * @param wallet кошелек
     * @return HashMap, содержащая информацию о пользователе, например, имя и фамилию.
     */
    HashMap<String, String> getUserInfo(Wallet wallet) throws UserIsNotExistsException;

    /**
     * Метод getTransactionHistory позволяет получить историю транзакций пользователя.
     *
     * @param wallet кошелек
     * @return Список транзакций, совершенных пользователем.
     */
    ArrayList<Transaction> getTransactionHistory(Wallet wallet) throws UserIsNotExistsException;

    /**
     * Формирует и обрабатывает транзакцию
     * @param wallet кошелек
     * @param moneyAmount Сумма транзакции
     * @param type Тип транзакции
    */
    void processTransactionFromRawData(Wallet wallet, BigDecimal moneyAmount, Transaction.Type type) throws UserIsNotExistsException;

    /**
     * Возвращает информацию о кошельке по его id
     * @param walletId id кошелька
     * @return Объект кошелька
     */
    Optional<Wallet> getWalletById(long walletId, String login) throws UserIsNotExistsException, WalletIsNotExistsException;
}