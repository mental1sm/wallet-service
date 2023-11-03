//package dao;
//
//import com.wallet.dao.user.UserDao;
//import com.wallet.dao.user.UserDaoImpl;
//import com.wallet.dao.wallet.WalletDao;
//import com.wallet.dao.wallet.WalletDaoImpl;
//import com.wallet.domain.entities.User;
//import com.wallet.domain.entities.Wallet;
//import com.wallet.utility.exceptions.UserAllreadyExistsException;
//import com.wallet.utility.exceptions.UserIsNotExistsException;
//import org.junit.jupiter.api.*;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@Testcontainers
//public class WalletDaoTest {
//    private static WalletDao walletDao;
//    private static UserDao userDao;
//    private static User fakePlayer;
//    private static Wallet fakeWallet;
//
//    @BeforeAll
//    public static void setUp() throws UserAllreadyExistsException, UserIsNotExistsException {
//        walletDao = new WalletDaoImpl();
//        userDao = new UserDaoImpl();
//
//        fakePlayer = FakePlayer.getFake("for_wallet_testing");
//        userDao.savePlayer(fakePlayer);
//        fakePlayer = userDao.findPlayer(fakePlayer.getLogin()).orElseThrow();
//    }
//
//    @Test
//    @Order(1)
//    public void testWalletSaveFind() {
//        fakeWallet = FakeWallet.getFake(fakePlayer.getId());
//        walletDao.saveWallet(fakeWallet);
//        Wallet retrievedWallet = walletDao.getWalletsOfUser(fakePlayer).get(0);
//        Assertions.assertEquals(fakeWallet.getPlayerId(), retrievedWallet.getPlayerId());
//        fakeWallet = walletDao.getWalletsOfUser(fakePlayer).get(0);
//    }
//
//    @Test
//    @Order(2)
//    public void testWalletUpdate() {
//        BigDecimal money = BigDecimal.valueOf(1490);
//        Wallet retrievedWallet = walletDao.getWalletsOfUser(fakePlayer).get(0);
//        retrievedWallet.setMoneyAmount(money);
//        walletDao.updateWallet(retrievedWallet);
//        Wallet updatedWallet = walletDao.getWalletsOfUser(fakePlayer).get(0);
//        Assertions.assertEquals(updatedWallet.getMoneyAmount(), retrievedWallet.getMoneyAmount());
//    }
//
//    @Test
//    @Order(3)
//    public void testMultipleWallets() {
//        Wallet secondFakeWallet = FakeWallet.getFake(fakePlayer.getId());
//        walletDao.saveWallet(secondFakeWallet);
//        ArrayList<Wallet> wallets = walletDao.getWalletsOfUser(fakePlayer);
//        Assertions.assertEquals(2, wallets.size());
//    }
//
//    @Test
//    @Order(4)
//    public void testDeleteWallet() {
//        walletDao.deleteWallet(fakeWallet);
//        ArrayList<Wallet> wallets = walletDao.getWalletsOfUser(fakePlayer);
//        Assertions.assertEquals(1, wallets.size());
//    }
//
//}
