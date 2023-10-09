package com.wallet.dao.player;

import com.wallet.entities.Player;
import com.wallet.infrastructure.PlayerInMemoryRepository;
import com.wallet.infrastructure.UserSession;

public class PlayerDaoImpl implements PlayerDao{

    PlayerInMemoryRepository playerInMemoryRepository;

    public PlayerDaoImpl() {
        this.playerInMemoryRepository = PlayerInMemoryRepository.getInstance();
    }

    @Override
    public void savePlayer(Player pl) {
        this.playerInMemoryRepository.savePlayer(pl);
    }

    @Override
    public Player loadPlayer(String pLogin, String pPassword) {
        return this.playerInMemoryRepository.getPlayerByLoginPassword(pLogin, pPassword);
    }

    @Override
    public Player loadPlayer(UserSession userSession) {
        String PID = userSession.getPlayerID();
        return this.playerInMemoryRepository.getPlayerById(PID);
    }

    @Override
    public String getName(Player pl) {
        return pl.getName();
    }

    @Override
    public String getSurname(Player pl) {
        return pl.getSurname();
    }

    @Override
    public String getPID(Player pl) {
        return pl.getPlayerID();
    }


    @Override
    public void setPassword(String newPassword, Player pl) {
        pl.setPPassword(newPassword);
    }
}
