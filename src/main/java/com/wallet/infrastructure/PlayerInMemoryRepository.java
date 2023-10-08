package com.wallet.infrastructure;

import com.wallet.entities.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerInMemoryRepository {
    private static PlayerInMemoryRepository instance = null;
    public HashMap<String, Player> Players;

    private PlayerInMemoryRepository() {
        this.Players = new HashMap<>();
    }

    public static PlayerInMemoryRepository getInstance() {
        if (instance == null) {
            instance = new PlayerInMemoryRepository();
        }
        return instance;
    }

    public void savePlayer(Player player) {
        this.Players.put(player.getPlayerID(), player);
    }

    public Player getPlayerById(String playerId) {
        return this.Players.get(playerId);
    }

    public Player getPlayerByLoginPassword(String login, String password) {
        Player selectedPlayer = null;
        for (Map.Entry<String, Player> entry : this.Players.entrySet()) {
            String PID = entry.getKey();
            Player pl = entry.getValue();
            if (Objects.equals(pl.getPLogin(), login)) { selectedPlayer = pl; }
            else { return null; }
        }
        try {
            assert selectedPlayer != null;
            if (Objects.equals(selectedPlayer.getPPassword(), password)) {
                return selectedPlayer;
            }
        } catch (AssertionError ignored) {}

        return null;
    }
}
