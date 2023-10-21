package com.wallet.dao.log;

import com.wallet.domain.entities.Log;

import java.util.ArrayList;

/**
 * Интерфейс, представляющий собой
*/
public interface LogDao {

    /**
     * Создает запись в логах
     * @param log Объект лога
    */
    void saveLog(Log log);


    /**
     * Метод возвращает количество всех логов с уровнем информирования WARNING и ERROR (для пагинации)
     * @return Количество всех важных логов
     */
    long getAmountOfImportantLogs();

    /**
     * Метод возвращает логи с уровнем информирования WARNING и ERROR
     * @param start Начальная граница
     * @param end Конечная граница
     * @return Логи с уровнем информирования WARNING и ERROR от start до end позиции
     */
    ArrayList<Log> getImportantLogs(long start, long end);

    ArrayList<Log> getAllLogs();
}
