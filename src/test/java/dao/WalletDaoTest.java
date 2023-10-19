package dao;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.dao.wallet.WalletDao;
import com.wallet.dao.wallet.WalletDaoImpl;
import com.wallet.entities.Player;
import com.wallet.entities.Wallet;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import dao.fakemocks.FakePlayer;
import dao.fakemocks.FakeWallet;
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
    private static Player mockedPlayer;
    private static Wallet mockedWallet;

    @BeforeAll
    public static void setUp() throws PlayerAllreadyExistsException {
        walletDao = new WalletDaoImpl();
        playerDao = new PlayerDaoImpl();

        mockedPlayer = FakePlayer.getFake();
        when(mockedPlayer.getPLogin()).thenReturn("for_wallet_testing");
        playerDao.savePlayer(mockedPlayer);
        mockedPlayer = playerDao.findPlayer(mockedPlayer.getPLogin());
    }

    @Test
    @Order(1)
    public void testWalletSaveFind() {
        mockedWallet = FakeWallet.getFake();
        when(mockedWallet.getPlayerId()).thenReturn(mockedPlayer.getPlayerID());
        walletDao.saveWallet(mockedWallet);
        Wallet retrievedWallet = walletDao.getWalletsOfPlayer(mockedPlayer).get(0);
        Assertions.assertEquals(mockedWallet.getPlayerId(), retrievedWallet.getPlayerId());
        mockedWallet = walletDao.getWalletsOfPlayer(mockedPlayer).get(0);
    }

    @Test
    @Order(2)
    public void testWalletUpdate() {
        BigDecimal money = BigDecimal.valueOf(1490);
        Wallet retrievedWallet = walletDao.getWalletsOfPlayer(mockedPlayer).get(0);
        retrievedWallet.setWalletMoneyAmount(money);
        walletDao.updateWallet(retrievedWallet);
        Wallet updatedWallet = walletDao.getWalletsOfPlayer(mockedPlayer).get(0);
        Assertions.assertEquals(updatedWallet.getWalletMoneyAmount(), retrievedWallet.getWalletMoneyAmount());
    }

    @Test
    @Order(3)
    public void testMultipleWallets() {
        Wallet secondMockedWallet = FakeWallet.getFake();
        when(secondMockedWallet.getPlayerId()).thenReturn(mockedPlayer.getPlayerID());
        walletDao.saveWallet(secondMockedWallet);
        ArrayList<Wallet> wallets = walletDao.getWalletsOfPlayer(mockedPlayer);
        Assertions.assertEquals(2, wallets.size());
    }

    @Test
    @Order(4)
    public void testDeleteWallet() {
        walletDao.deleteWallet(mockedWallet);
        ArrayList<Wallet> wallets = walletDao.getWalletsOfPlayer(mockedPlayer);
        Assertions.assertEquals(1, wallets.size());
    }

}
