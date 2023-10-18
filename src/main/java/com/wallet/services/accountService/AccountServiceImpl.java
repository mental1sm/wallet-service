package com.wallet.services.accountService;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.dao.wallet.WalletDao;
import com.wallet.dao.wallet.WalletDaoImpl;
import com.wallet.entities.Log;
import com.wallet.entities.Player;
import com.wallet.entities.Wallet;
import com.wallet.infrastructure.UserSession;
import com.wallet.services.loggerService.LoggerService;
import com.wallet.utility.IdGenerator;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;

import java.math.BigDecimal;


public class AccountServiceImpl implements AccountService {
    private final PlayerDao playerDao;
    private final WalletDao walletDao;

    public AccountServiceImpl(PlayerDao playerDao, WalletDao walletDao) {
        this.playerDao = playerDao;
        this.walletDao = walletDao;

    }

    @Override
    public UserSession regUser(String name, String surname, String pLogin, String pPassword) throws PlayerAllreadyExistsException {
        Player pl = new Player(0, 3, Player.Permission.USER, name, surname, pLogin, pPassword);
        playerDao.savePlayer(pl);
        pl = playerDao.findPlayer(pLogin);
        UserSession session = new UserSession(pl.getPlayerID());
        regWallet(pl.getPlayerID());
        session.setCurrentWalletNum(0);

        return session;
    }

    private void regWallet(long playerId) {
        Wallet wallet = new Wallet(0, playerId, new BigDecimal(0));
        walletDao.saveWallet(wallet);


    }
    @Override
    public UserSession authUser(String pLogin, String pPassword) throws PlayerIsNotExistsException {
        Player pl = playerDao.findPlayer(pLogin);
        if (pl == null) { return null; }
        else if (pl.getPPassword().contentEquals(pPassword)) {
            UserSession session = new UserSession(pl.getPlayerID());
            session.setCurrentWalletNum(0);
            return session;
        }
        return null;
    }

}
