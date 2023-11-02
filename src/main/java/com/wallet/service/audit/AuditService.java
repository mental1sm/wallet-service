package com.wallet.service.audit;

import com.wallet.domain.entities.Log;

import java.util.ArrayList;

public interface AuditService {
    /**
     * Добавить в журнал аудита запись
     * @param login логин пользователя, аудит которого ведется
     * @param action действие пользователя
     * @param description описание действия пользователя
    */
    void logThis(String login, String action, String description);

    /**
     * Получить все записи аудита для конкретного пользователя
     * @param login логин пользователя
     * @return ArrayList записей аудита
    */
    ArrayList<Log> getLogsOfUser(String login);

    /**
     * Получить все записи аудита в целом
     * @return ArrayList записей аудита
    */
    ArrayList<Log> getAllLogs();

    /**
     * Получить все записи аудита по конкретному действию
     * @param action действие
     * @return ArrayList записей аудита
    */
    ArrayList<Log> getAllLogsFilterByAction(String action);
}
