package dao.fakentities;

import com.wallet.domain.entities.Wallet;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;

public class FakeWallet {
    public static Wallet getFake(long playerId) {
        return Wallet.builder()
                .id(1)
                .playerId(playerId)
                .moneyAmount(BigDecimal.valueOf(0))
                .build();
    }
}
