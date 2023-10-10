package com.wallet.services.loggerService;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.dao.wallet.WalletDao;
import com.wallet.dao.wallet.WalletDaoImpl;
import com.wallet.entities.Player;
import com.wallet.infrastructure.LoggerInMemoryRepository;
import com.wallet.infrastructure.UserSession;

import java.util.ArrayList;
import java.util.Date;

/**
 * Класс LoggerService предоставляет сервис для журналирования действий игроков и управления журналом.
 * Этот класс реализует паттерн Singleton.
 */
public class LoggerService {
    private static LoggerService instance = null;
    private final LoggerInMemoryRepository loggerRepository;
    private final PlayerDao playerDao;
    private final WalletDao walletDao;


    private LoggerService() {
        this.loggerRepository = LoggerInMemoryRepository.getInstance();
        this.playerDao = new PlayerDaoImpl();
        this.walletDao = new WalletDaoImpl();
    }

    /**
     * Метод для получения единственного экземпляра LoggerService.
     *
     * @return Единственный экземпляр LoggerService.
     */
    public static LoggerService getInstance() {
        if (instance == null) {
            instance = new LoggerService();
        }
        return instance;
    }

    /**
     * Метод для журналирования действия игрока.
     *
     * @param player Игрок, для которого выполняется журналирование.
     * @param action Описание действия, которое требуется зарегистрировать.
     */
    public void log(Player player, String action) {
        String playerID = playerDao.getPID(player);
        String walletID = walletDao.loadWallet(playerID).getWalletId();
        String date = String.valueOf(new Date());
        String logString = String.format("[%s] - [ID игрока: %s] [ID кошелька: %s] [%s]", date, playerID, walletID, action);
        loggerRepository.saveLog(playerID, logString);
    }

    /**
     * Метод для журналирования действия в рамках сеанса пользователя.
     *
     * @param session Сеанс пользователя, для которого выполняется журналирование.
     * @param action  Описание действия, которое требуется зарегистрировать.
     */
    public void log(UserSession session, String action) {
        Player player = playerDao.loadPlayer(session);
        String playerID = playerDao.getPID(player);
        String walletID = walletDao.loadWallet(playerID).getWalletId();
        String date = String.valueOf(new Date());
        String logString = String.format("[%s] - [ID игрока: %s] [ID кошелька: %s] [%s]", date, playerID, walletID, action);
        loggerRepository.saveLog(playerID, logString);
    }

    /**
     * Метод для получения всех записей журнала для заданного игрока.
     *
     * @param playerID Идентификатор игрока, для которого нужно получить записи журнала.
     * @return Список строк, представляющих записи журнала для указанного игрока.
     */
    public ArrayList<String> getPlayerLogs(String playerID) {
        return loggerRepository.getAllLogs();
    }

    /**
     * Метод для получения всех записей журнала.
     *
     * @return Список строк, представляющих записи журнала.
     */
    public ArrayList<String> getAllLogs() {
        return loggerRepository.getAllLogs();
    }
}