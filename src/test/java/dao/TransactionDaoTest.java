//package dao;
//
//import com.wallet.dao.user.UserDao;
//import com.wallet.dao.user.UserDaoImpl;
//import com.wallet.dao.transaction.TransactionDao;
//import com.wallet.dao.transaction.TransactionDaoImpl;
//import com.wallet.dao.wallet.WalletDao;
//import com.wallet.dao.wallet.WalletDaoImpl;
//import com.wallet.domain.entities.User;
//import com.wallet.domain.entities.Transaction;
//import com.wallet.domain.entities.Wallet;
//import com.wallet.utility.exceptions.UserAllreadyExistsException;
//import com.wallet.utility.exceptions.UserIsNotExistsException;
//import org.junit.jupiter.api.*;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//@Testcontainers
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class TransactionDaoTest {
//    private static TransactionDao transactionDao;
//    private static User fakePlayer;
//    private static Wallet fakeWallet;
//
//
//    @BeforeAll
//    public static void setUp() throws UserAllreadyExistsException, UserIsNotExistsException {
//        transactionDao = new TransactionDaoImpl();
//        WalletDao walletDao = new WalletDaoImpl();
//        UserDao userDao = new UserDaoImpl();
//
//        fakePlayer = FakePlayer.getFake("for_transaction_testing");
//        userDao.savePlayer(fakePlayer);
//        fakePlayer = userDao.findPlayer(fakePlayer.getLogin()).orElseThrow();
//
//        fakeWallet = FakeWallet.getFake(fakePlayer.getId());
//        walletDao.saveWallet(fakeWallet);
//        fakeWallet = walletDao.getWalletsOfUser(fakePlayer).get(0);
//    }
//
//    @Test
//    @Order(1)
//    public void testTransactionSaveFind() {
//        Transaction fakeTransaction = FakeTransaction.getFake(fakePlayer.getId(), fakeWallet.getId());
//        transactionDao.saveTransaction(fakeTransaction);
//        Transaction retrievedTransaction = transactionDao.getTransactionsOfWallet(fakeWallet).get(0);
//        Assertions.assertEquals(fakeTransaction.getId(), retrievedTransaction.getId());
//    }
//
//    @Test
//    @Order(2)
//    public void testTransactionUpdateStatus() {
//
//    }
//
//    @Test
//    @Order(3)
//    public void testTransactionHistory() {
//
//    }
//
//    @Test
//    @Order(4)
//    public void testTransactionDelete() {
//
//    }
//
//}