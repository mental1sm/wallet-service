package dao.fakentities;

import com.wallet.entities.Transaction;
import com.wallet.utility.IdGenerator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.util.Date;

public class FakeTransaction {
    public static Transaction getFake(long playerId, long walletId) {
        return Transaction.builder()
                .transactionId(IdGenerator.genId())
                .transactionStatus(Transaction.Status.Pending)
                .transactionType(Transaction.Type.Deposit)
                .transactionSum(BigDecimal.valueOf(1000))
                .transactionDate(new Date())
                .playerId(playerId)
                .walletId(walletId)
                .build();
    }
}
