package com.wallet.dao.logger;

import com.wallet.domain.entities.Log;
import com.wallet.domain.entities.User;

import java.util.ArrayList;

public interface LoggerDao {
    /**
     * Сохранить лог в БД
     * @param log объект лога
    */
    void saveLog(Log log);

    /**
     * Получить все логи конкретного пользователя
     * @param userLogin пользователь
     * @return ArrayList логов
    */
    ArrayList<Log> getLogsOfConcreteUser(String userLogin);

    /**
     * Получить все логи для указанного действия
     * @param action действие
     * @return ArrayList логов
     */
    ArrayList<Log> getAllLogsFilterByAction(String action);

    /**
     * Получить все логи
     * @return ArrayList логов
     */
    ArrayList<Log> getAllLogs();
}
