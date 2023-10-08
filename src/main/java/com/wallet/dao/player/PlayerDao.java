package com.wallet.dao.player;

import com.wallet.entities.Player;
import com.wallet.infrastructure.UserSession;

public interface PlayerDao {
    void savePlayer(Player pl);
    Player loadPlayer(String pLogin, String pPassword);
    Player loadPlayer(UserSession userSession);

    String getName(Player pl);
    String getSurname(Player pl);
    String getPID(Player pl);

    void setPassword(String newPassword, Player pl);
}
