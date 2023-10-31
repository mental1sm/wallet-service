package dao.fakentities;

import com.wallet.domain.entities.Transaction;
import com.wallet.utility.IdGenerator;

import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.Date;

public class FakeTransaction {
    public static Transaction getFake(long playerId, long walletId) {
        return Transaction.builder()
                .id(IdGenerator.genId().toString())
                .transactionStatus(Transaction.Status.Pending)
                .transactionType(Transaction.Type.Deposit)
                .transactionSum(BigDecimal.valueOf(1000))
                .transactionDate(new Date())
                .playerId(playerId)
                .walletId(walletId)
                .build();
    }
}
