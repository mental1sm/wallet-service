package com.wallet.services.accountService;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.wallet.WalletDao;
import com.wallet.domain.entities.Player;
import com.wallet.domain.entities.Wallet;
import com.wallet.utility.exceptions.InvalidPasswordException;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;

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
    public boolean regUser(String name, String surname, String pLogin, String pPassword) throws PlayerAllreadyExistsException, PlayerIsNotExistsException {
        Player pl = new Player(0, 3, Player.Permission.USER, name, surname, pLogin, pPassword);
        playerDao.savePlayer(pl);
        Optional<Player> optionalPlayer = playerDao.findPlayer(pLogin);
        if (optionalPlayer.isEmpty()) { throw new PlayerIsNotExistsException(); }
        pl = optionalPlayer.get();
        regWallet(pl.getId());

        return true;
    }

    private void regWallet(long playerId) {
        Wallet wallet = new Wallet(0, playerId, new BigDecimal(0));
        walletDao.saveWallet(wallet);


    }
    @Override
    public boolean authUser(String pLogin, String pPassword) throws PlayerIsNotExistsException, InvalidPasswordException {
        Optional<Player> optionalPlayer = playerDao.findPlayer(pLogin);
        if (optionalPlayer.isEmpty()) { throw new PlayerIsNotExistsException(); }
        Player pl = optionalPlayer.get();
        if (!(pl.getPassword().contentEquals(pPassword))) { throw new InvalidPasswordException(); }
        return true;
    }

}
