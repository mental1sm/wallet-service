//package dao;
//
//import com.wallet.dao.player.PlayerDao;
//import com.wallet.dao.player.PlayerDaoImpl;
//import com.wallet.dao.transaction.TransactionDao;
//import com.wallet.dao.transaction.TransactionDaoImpl;
//import com.wallet.dao.wallet.WalletDao;
//import com.wallet.dao.wallet.WalletDaoImpl;
//import com.wallet.entities.Player;
//import com.wallet.entities.Transaction;
//import com.wallet.entities.Wallet;
//import com.wallet.utility.IdGenerator;
//import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.UUID;
//
//public class TransactionDaoTest {
//
//    static UUID playerId;
//    static UUID walletId;
//
//    @BeforeAll
//    public static void setUp() throws PlayerAllreadyExistsException {
//        initPlayersAndWallets();
//    }
//
//    private static void initPlayersAndWallets() throws PlayerAllreadyExistsException {
//        PlayerDao playerDao = new PlayerDaoImpl();
//        WalletDao walletDao = new WalletDaoImpl();
//
//        playerId = IdGenerator.genId();
//        walletId = IdGenerator.genId();
//
//        Player player1 = new Player(playerId, Player.Permission.USER,"pl name", "pl surname", "Alex", "12345");
//        Wallet wallet1 = new Wallet(walletId, playerId, new BigDecimal(0));
//
//        playerDao.savePlayer(player1);
//        walletDao.saveWallet(wallet1);
//    }
//
//    private static Transaction createTestTransaction() {
//        return Transaction.builder()
//                .transactionId(IdGenerator.genId())
//                .transactionDate(new Date())
//                .transactionStatus(Transaction.Status.Pending)
//                .transactionType(Transaction.Type.Deposit)
//                .walletId(walletId)
//                .build();
//
//    }
//
//    @Test
//    public void transactionSaveLoadTest() {
//        TransactionDao transactionDao = new TransactionDaoImpl();
//        Transaction testTransaction = createTestTransaction();
//        transactionDao.saveTransaction(testTransaction);
//
//        ArrayList<Transaction> testTransactionArray = transactionDao.findTransaction(walletId);
//        Assertions.assertThat(testTransactionArray).contains(testTransaction);
//    }
//
//
//}
