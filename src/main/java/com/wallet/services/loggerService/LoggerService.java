package com.wallet.services.loggerService;

import com.wallet.dao.log.LogDao;
import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.wallet.WalletDao;
import com.wallet.entities.Log;
import com.wallet.entities.Player;
import com.wallet.entities.Wallet;
import com.wallet.infrastructure.UserSession;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Класс LoggerService предоставляет сервис для журналирования действий игроков и управления журналом.
 * Этот класс реализует паттерн Singleton.
 */
public class LoggerService {
    @Getter
    private static LoggerService instance = null;
    private final LogDao logDao;
    private final PlayerDao playerDao;
    private final WalletDao walletDao;


    private LoggerService(LogDao logDao, PlayerDao playerDao, WalletDao walletDao) {
        this.logDao = logDao;
        this.playerDao = playerDao;
        this.walletDao = walletDao;
    }

    /**
     * Метод для получения единственного экземпляра LoggerService.
     *
     * @return Единственный экземпляр LoggerService.
     */
    public static LoggerService getInstance(LogDao logDao, PlayerDao playerDao, WalletDao walletDao) {
        if (instance == null) {
            instance = new LoggerService(logDao, playerDao, walletDao);
        }
        return instance;
    }

    /**
     * Метод для журналирования действия в рамках сеанса пользователя.
     *
     * @param session Сессия игрока, для которого выполняется журналирование.
     * @param action Описание действия, которое требуется зарегистрировать.
     * @param infoLevel Уровень информирования лога
     */
    public void log(UserSession session, String action, Log.InfoLevels infoLevel) {
        Player pl = playerDao.findPlayer(session);
        Log log = Log.builder()
                .id(0)
                .timestamp(new Date())
                .playerId(pl.getPlayerID())
                .infoLevel(infoLevel)
                .action(action)
                .build();
        logDao.saveLog(log);
    }

    /**
     * Метод для получения всех записей журнала для заданного игрока.
     *
     * @param playerID Идентификатор игрока, для которого нужно получить записи журнала.
     * @return Список строк, представляющих записи журнала для указанного игрока.
     */
    public ArrayList<Log> getPlayerLogs(long playerID) {
        return logDao.getLogsOfPlayer(playerID);
    }

    /**
     * Метод для получения всех записей журнала.
     *
     * @return Список строк, представляющих записи журнала.
     */
    public ArrayList<Log> getAllImportantLogs(int page) {
        return logDao.getImportantLogs(100*(long)page, 100);
    }
    public ArrayList<Log> getAllLogs(int page) {
        return logDao.getAllLogs();
    }
}
