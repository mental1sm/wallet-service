//package dao;
//
//import com.wallet.dao.wallet.WalletDao;
//import com.wallet.dao.wallet.WalletDaoImpl;
//import com.wallet.entities.Player;
//import com.wallet.entities.Wallet;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.UUID;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class WalletDaoTest {
//
//    @Test
//    public void walletSaveLoadTest() {
//        WalletDao walletDao = new WalletDaoImpl();
//        UUID playerID = UUID.randomUUID();
//        UUID walletID = UUID.randomUUID();
//
//        Wallet wallet = mock(Wallet.class);
//        when(wallet.getWalletId()).thenReturn(walletID);
//        when(wallet.getPlayerId()).thenReturn(playerID);
//
//        walletDao.saveWallet(wallet);
//
//        Assertions.assertThat(wallet).isEqualTo(walletDao.findWallet(playerID));
//    }
//}
