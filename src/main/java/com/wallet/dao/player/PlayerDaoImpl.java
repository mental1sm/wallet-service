package com.wallet.dao.player;

import com.wallet.entities.Player;
import com.wallet.infrastructure.PlayerInMemoryRepository;
import com.wallet.infrastructure.UserSession;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;

import java.util.UUID;

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
    public Player findPlayer(String pLogin, String pPassword) throws PlayerIsNotExistsException {
        return this.playerInMemoryRepository.getPlayerByLoginPassword(pLogin, pPassword);
    }

    @Override
    public Player findPlayer(UserSession userSession) {
        UUID PID = userSession.getPlayerID();
        return this.playerInMemoryRepository.getPlayerById(PID);
    }

}
