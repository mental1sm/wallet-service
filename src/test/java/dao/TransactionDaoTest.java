package dao;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.dao.transaction.TransactionDao;
import com.wallet.dao.transaction.TransactionDaoImpl;
import com.wallet.dao.wallet.WalletDao;
import com.wallet.dao.wallet.WalletDaoImpl;
import com.wallet.entities.Player;
import com.wallet.entities.Transaction;
import com.wallet.entities.Wallet;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import dao.fakentities.FakePlayer;
import dao.fakentities.FakeTransaction;
import dao.fakentities.FakeWallet;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.Mockito.when;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransactionDaoTest {
    private static TransactionDao transactionDao;
    private static Player fakePlayer;
    private static Wallet fakeWallet;


    @BeforeAll
    public static void setUp() throws PlayerAllreadyExistsException {
        transactionDao = new TransactionDaoImpl();
        WalletDao walletDao = new WalletDaoImpl();
        PlayerDao playerDao = new PlayerDaoImpl();

        fakePlayer = FakePlayer.getFake("for_transaction_testing");
        playerDao.savePlayer(fakePlayer);
        fakePlayer = playerDao.findPlayer(fakePlayer.getLogin()).orElseThrow();

        fakeWallet = FakeWallet.getFake(fakePlayer.getId());
        walletDao.saveWallet(fakeWallet);
        fakeWallet = walletDao.getWalletsOfPlayer(fakePlayer).get(0);
    }

    @Test
    @Order(1)
    public void testTransactionSaveFind() {
        Transaction fakeTransaction = FakeTransaction.getFake(fakePlayer.getId(), fakeWallet.getId());
        transactionDao.saveTransaction(fakeTransaction);
        Transaction retrievedTransaction = transactionDao.getTransactionsOfWallet(fakeWallet).get(0);
        Assertions.assertEquals(fakeTransaction.getTransactionId(), retrievedTransaction.getTransactionId());
    }

    @Test
    @Order(2)
    public void testTransactionUpdateStatus() {

    }

    @Test
    @Order(3)
    public void testTransactionHistory() {

    }

    @Test
    @Order(4)
    public void testTransactionDelete() {

    }

}