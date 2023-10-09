package com.wallet.infrastructure;

import com.wallet.entities.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Класс PlayerInMemoryRepository представляет репозиторий для хранения и управления объектами Player
 * в памяти, используя HashMap.
 */
public class PlayerInMemoryRepository {
    private static PlayerInMemoryRepository instance = null;
    public HashMap<String, Player> Players;

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
    public void savePlayer(Player player) {
        this.Players.put(player.getPlayerID(), player);
    }

    /**
     * Метод возвращает объект Player по указанному идентификатору игрока.
     *
     * @param playerId Идентификатор игрока.
     * @return Объект Player с соответствующим идентификатором или null, если игрок не найден.
     */
    public Player getPlayerById(String playerId) {
        return this.Players.get(playerId);
    }

    /**
     * Метод getPlayerByLoginPassword возвращает объект Player, соответствующий заданным
     * логину и паролю. Проверяет соответствие логина и пароля для каждого игрока в репозитории.
     *
     * @param login    Логин игрока.
     * @param password Пароль игрока.
     * @return Объект Player, если соответствие найдено, иначе null.
     */
    public Player getPlayerByLoginPassword(String login, String password) {
        Player selectedPlayer = null;
        // Перебор всех игроков для поиска совпадения
        for (Map.Entry<String, Player> entry : this.Players.entrySet()) {
            String PID = entry.getKey();
            Player pl = entry.getValue();
            if (Objects.equals(pl.getPLogin(), login)) {
                selectedPlayer = pl;
            } else {
                return null;
            }
        }
        try {
            if (Objects.equals(selectedPlayer.getPPassword(), password)) {
                return selectedPlayer;
            }
        } catch (Exception ignored) {}

        return null;
    }
}
