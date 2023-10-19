package dao.fakemocks;

import com.github.javafaker.Faker;
import com.wallet.entities.Wallet;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FakeWallet {
    public static Wallet getFake() {
        Faker faker = Faker.instance();
        Wallet wallet = mock(Wallet.class);
        when(wallet.getWalletId()).thenReturn((long) 1);
        when(wallet.getWalletMoneyAmount()).thenReturn(BigDecimal.valueOf(0));
        return wallet;
    }
}
