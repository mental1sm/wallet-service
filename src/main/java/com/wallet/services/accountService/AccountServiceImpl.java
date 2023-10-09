package com.wallet.services.accountService;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.dao.wallet.WalletDao;
import com.wallet.dao.wallet.WalletDaoImpl;
import com.wallet.entities.Player;
import com.wallet.entities.Wallet;
import com.wallet.infrastructure.UserSession;
import com.wallet.utility.IdGenerator;

import java.math.BigDecimal;


public class AccountServiceImpl implements AccountService {
    private final PlayerDao playerDao;
    private final WalletDao walletDao;

    public AccountServiceImpl() {
        this.playerDao = new PlayerDaoImpl();
        this.walletDao = new WalletDaoImpl();

    }

    @Override
    public UserSession regUser(String name, String surname, String pLogin, String pPassword) {
        Player pl = new Player(name, surname, IdGenerator.genId(), pLogin, pPassword);
        playerDao.savePlayer(pl);
        Wallet wallet = new Wallet(IdGenerator.genId(), playerDao.getPID(pl), new BigDecimal(0));
        walletDao.saveWallet(wallet);
        return new UserSession(playerDao.getPID(pl));
    }

    @Override
    public UserSession authUser(String pLogin, String pPassword) {
        Player pl = playerDao.loadPlayer(pLogin, pPassword);
        return new UserSession(playerDao.getPID(pl));
    }

}
