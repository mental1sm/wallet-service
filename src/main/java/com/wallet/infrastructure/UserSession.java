package com.wallet.infrastructure;

import com.wallet.utility.IdGenerator;
import lombok.Getter;
import lombok.Setter;

public class UserSession {

    private String sessionId;
    @Getter private String playerID;

    public UserSession(String playerID) {
        sessionId = IdGenerator.genId();
        this.playerID = playerID;
    }
}
