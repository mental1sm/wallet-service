package com.wallet.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Класс LoggerInMemoryRepository представляет репозиторий для хранения журнала действий в памяти.
 * Этот класс реализует паттерн Singleton.
 */
public class LoggerInMemoryRepository {
    private static LoggerInMemoryRepository instance = null;
    private HashMap<UUID, ArrayList<String>> Logs;

    /**
     * Приватный конструктор для создания объекта LoggerInMemoryRepository.
     * Внутренний хеш-отображение Logs инициализируется при создании объекта.
     */
    private LoggerInMemoryRepository() {
        Logs = new HashMap<>();
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
    public void saveLog(UUID playerID, String log) {
        if (Logs.get(playerID) == null) { Logs.put(playerID, new ArrayList<String>());}
        ArrayList<String> playerLogs = Logs.get(playerID);
        playerLogs.add(log);
    }

    /**
     * Метод для получения всех записей журнала из репозитория.
     *
     * @return Список строк, представляющих все записи журнала.
     */
    public ArrayList<String> getAllLogs() {
        ArrayList<String> logView = new ArrayList<>();
        for (Map.Entry<UUID, ArrayList<String>> entry : Logs.entrySet()) {
            logView.addAll(entry.getValue());
        }
        return logView;
    }
}
