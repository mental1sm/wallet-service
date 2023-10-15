package com.wallet.infrastructure;

import com.wallet.entities.Player;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Класс PlayerInMemoryRepository представляет репозиторий для хранения и управления объектами Player
 * в памяти, используя HashMap.
 */
public class PlayerInMemoryRepository {
    private static PlayerInMemoryRepository instance = null;
    public HashMap<UUID, Player> Players;

    /**
     * Приватный конструктор класса создает экземпляр HashMap для хранения
     * объектов Player.
     */
    private PlayerInMemoryRepository() {
        this.Players = new HashMap<>();
    }

    /**
     * Метод getInstance возвращает единственный экземпляр класса с использованием
     * паттерна Singleton.
     *
     * @return Единственный экземпляр PlayerInMemoryRepository.
     */
    public static PlayerInMemoryRepository getInstance() {
        if (instance == null) {
            instance = new PlayerInMemoryRepository();
        }
        return instance;
    }

    /**
     * Метод savePlayer сохраняет объект Player в репозитории.
     *
     * @param player Объект Player для сохранения.
     */
    public void savePlayer(Player player) throws PlayerAllreadyExistsException {
        if (checkExisting(player.getPLogin())) {
            throw new PlayerAllreadyExistsException("Пользователь с таким логином уже зарегистрирован.");
        } else {
            this.Players.put(player.getPlayerID(), player);
        }
    }

    /**
     * Метод для проверки существования игрока по логину
     * @param login - Логин игрокаъ
     * @return boolean значение
    */
    public boolean checkExisting(String login) {
        Player selectedPlayer = null;

        for (Map.Entry<UUID, Player> entry : this.Players.entrySet()) {
            UUID PID = entry.getKey();
            Player pl = entry.getValue();
            if (Objects.equals(pl.getPLogin(), login)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод возвращает объект Player по указанному идентификатору игрока.
     *
     * @param playerId Идентификатор игрока.
     * @return Объект Player с соответствующим идентификатором или null, если игрок не найден.
     */
    public Player getPlayerById(UUID playerId) throws PlayerIsNotExistsException {
        Player selectedPlayer;
        try {
            selectedPlayer = Players.get(playerId);
        } catch (NullPointerException e) {
            throw new PlayerIsNotExistsException("Такого игрока не существует.");
        }
        return selectedPlayer;
    }

    /**
     * Метод getPlayerByLoginPassword возвращает объект Player, соответствующий заданным
     * логину и паролю. Проверяет соответствие логина и пароля для каждого игрока в репозитории.
     *
     * @param login    Логин игрока.
     * @param password Пароль игрока.
     * @return Объект Player, если соответствие найдено, иначе null.
     */
    public Player getPlayerByLoginPassword(String login, String password) throws PlayerIsNotExistsException {
        Player selectedPlayer = null;
        // Перебор всех игроков для поиска совпадения
        for (Map.Entry<UUID, Player> entry : this.Players.entrySet()) {
            UUID PID = entry.getKey();
            Player pl = entry.getValue();
            if (Objects.equals(pl.getPLogin(), login)) {
                selectedPlayer = pl;
            }
        }
        try {
            if (Objects.equals(selectedPlayer.getPPassword(), password)) {
                return selectedPlayer;
            }
        } catch (NullPointerException e) {
            throw new PlayerIsNotExistsException("Такого пользователя не существует");
        }

        return null;
    }
}
