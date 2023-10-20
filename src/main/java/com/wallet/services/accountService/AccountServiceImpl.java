package com.wallet.services.accountService;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.wallet.WalletDao;
import com.wallet.entities.Player;
import com.wallet.entities.Wallet;
import com.wallet.infrastructure.UserSession;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;

import java.math.BigDecimal;
import java.util.Optional;


public class AccountServiceImpl implements AccountService {
    private final PlayerDao playerDao;
    private final WalletDao walletDao;

    public AccountServiceImpl(PlayerDao playerDao, WalletDao walletDao) {
        this.playerDao = playerDao;
        this.walletDao = walletDao;

    }

    @Override
    public Optional<UserSession> regUser(String name, String surname, String pLogin, String pPassword) throws PlayerAllreadyExistsException {
        Player pl = new Player(0, 3, Player.Permission.USER, name, surname, pLogin, pPassword);
        playerDao.savePlayer(pl);
        Optional<Player> optionalPlayer = playerDao.findPlayer(pLogin);
        if (optionalPlayer.isPresent()) {
            pl = optionalPlayer.get();
            UserSession session = new UserSession(pl.getId());
            regWallet(pl.getId());
            session.setCurrentWalletNum(0);
            return Optional.of(session);
        }

        return Optional.empty();
    }

    private void regWallet(long playerId) {
        Wallet wallet = new Wallet(0, playerId, new BigDecimal(0));
        walletDao.saveWallet(wallet);


    }
    @Override
    public Optional<UserSession> authUser(String pLogin, String pPassword) {
        Optional<Player> optionalPlayer = playerDao.findPlayer(pLogin);
        if (optionalPlayer.isEmpty()) { return Optional.empty(); }
        Player pl = optionalPlayer.get();
        if (pl.getPassword().contentEquals(pPassword)) {
            UserSession session = new UserSession(pl.getId());
            session.setCurrentWalletNum(0);
            return Optional.of(session);
        }
        return Optional.empty();
    }

}
