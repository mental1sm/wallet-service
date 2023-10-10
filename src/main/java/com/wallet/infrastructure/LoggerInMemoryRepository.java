package com.wallet.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс LoggerInMemoryRepository представляет репозиторий для хранения журнала действий в памяти.
 * Этот класс реализует паттерн Singleton.
 */
public class LoggerInMemoryRepository {
    private static LoggerInMemoryRepository instance = null;
    private HashMap<String, String> Logs;

    /**
     * Приватный конструктор для создания объекта LoggerInMemoryRepository.
     * Внутренний хеш-отображение Logs инициализируется при создании объекта.
     */
    private LoggerInMemoryRepository() {
        this.Logs = new HashMap<>();
    }

    /**
     * Метод для получения единственного экземпляра LoggerInMemoryRepository (реализация Singleton).
     *
     * @return Единственный экземпляр LoggerInMemoryRepository.
     */
    public static LoggerInMemoryRepository getInstance() {
        if (instance == null) {
            instance = new LoggerInMemoryRepository();
        }
        return instance;
    }

    /**
     * Метод для сохранения записи журнала для указанного игрока.
     *
     * @param playerID Идентификатор игрока, для которого выполняется сохранение записи журнала.
     * @param log      Запись журнала, которую нужно сохранить.
     */
    public void saveLog(String playerID, String log) {
        this.Logs.put(playerID, log);
    }

    /**
     * Метод для получения всех записей журнала из репозитория.
     *
     * @return Список строк, представляющих все записи журнала.
     */
    public ArrayList<String> getAllLogs() {
        ArrayList<String> logView = new ArrayList<>();
        for (Map.Entry<String, String> entry : this.Logs.entrySet()) {
            logView.add(entry.getValue());
        }
        return logView;
    }
}
