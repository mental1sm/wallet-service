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
import dao.fakemocks.FakePlayer;
import dao.fakemocks.FakeTransaction;
import dao.fakemocks.FakeWallet;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.Mockito.when;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransactionDaoTest {
    private static TransactionDao transactionDao;
    private static Player mockedPlayer;
    private static Wallet mockedWallet;


    @BeforeAll
    public static void setUp() throws PlayerAllreadyExistsException {
        transactionDao = new TransactionDaoImpl();
        WalletDao walletDao = new WalletDaoImpl();
        PlayerDao playerDao = new PlayerDaoImpl();

        mockedPlayer = FakePlayer.getFake();
        when(mockedPlayer.getPLogin()).thenReturn("for_transaction_testing");
        playerDao.savePlayer(mockedPlayer);
        mockedPlayer = playerDao.findPlayer(mockedPlayer.getPLogin());

        mockedWallet = FakeWallet.getFake();
        when(mockedWallet.getPlayerId()).thenReturn(mockedPlayer.getPlayerID());
        walletDao.saveWallet(mockedWallet);
        mockedWallet = walletDao.getWalletsOfPlayer(mockedPlayer).get(0);
    }

    @Test
    @Order(1)
    public void testTransactionSaveFind() {
        Transaction mockedTransaction = FakeTransaction.getFake();
        when(mockedTransaction.getPlayerId()).thenReturn(mockedPlayer.getPlayerID());
        when(mockedTransaction.getWalletId()).thenReturn(mockedWallet.getWalletId());
        transactionDao.saveTransaction(mockedTransaction);
        Transaction retrievedTransaction = transactionDao.getTransactionsOfWallet(mockedWallet).get(0);
        Assertions.assertEquals(mockedTransaction.getTransactionId(), retrievedTransaction.getTransactionId());
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