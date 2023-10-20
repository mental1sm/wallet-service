package dao;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.dao.wallet.WalletDao;
import com.wallet.dao.wallet.WalletDaoImpl;
import com.wallet.entities.Player;
import com.wallet.entities.Wallet;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import dao.fakentities.FakePlayer;
import dao.fakentities.FakeWallet;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
public class WalletDaoTest {
    private static WalletDao walletDao;
    private static PlayerDao playerDao;
    private static Player fakePlayer;
    private static Wallet fakeWallet;

    @BeforeAll
    public static void setUp() throws PlayerAllreadyExistsException {
        walletDao = new WalletDaoImpl();
        playerDao = new PlayerDaoImpl();

        fakePlayer = FakePlayer.getFake("for_wallet_testing");
        playerDao.savePlayer(fakePlayer);
        fakePlayer = playerDao.findPlayer(fakePlayer.getLogin()).orElseThrow();
    }

    @Test
    @Order(1)
    public void testWalletSaveFind() {
        fakeWallet = FakeWallet.getFake(fakePlayer.getId());
        walletDao.saveWallet(fakeWallet);
        Wallet retrievedWallet = walletDao.getWalletsOfPlayer(fakePlayer).get(0);
        Assertions.assertEquals(fakeWallet.getPlayerId(), retrievedWallet.getPlayerId());
        fakeWallet = walletDao.getWalletsOfPlayer(fakePlayer).get(0);
    }

    @Test
    @Order(2)
    public void testWalletUpdate() {
        BigDecimal money = BigDecimal.valueOf(1490);
        Wallet retrievedWallet = walletDao.getWalletsOfPlayer(fakePlayer).get(0);
        retrievedWallet.setMoneyAmount(money);
        walletDao.updateWallet(retrievedWallet);
        Wallet updatedWallet = walletDao.getWalletsOfPlayer(fakePlayer).get(0);
        Assertions.assertEquals(updatedWallet.getMoneyAmount(), retrievedWallet.getMoneyAmount());
    }

    @Test
    @Order(3)
    public void testMultipleWallets() {
        Wallet secondFakeWallet = FakeWallet.getFake(fakePlayer.getId());
        walletDao.saveWallet(secondFakeWallet);
        ArrayList<Wallet> wallets = walletDao.getWalletsOfPlayer(fakePlayer);
        Assertions.assertEquals(2, wallets.size());
    }

    @Test
    @Order(4)
    public void testDeleteWallet() {
        walletDao.deleteWallet(fakeWallet);
        ArrayList<Wallet> wallets = walletDao.getWalletsOfPlayer(fakePlayer);
        Assertions.assertEquals(1, wallets.size());
    }

}
