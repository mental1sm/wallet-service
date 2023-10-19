package dao.fakemocks;

import com.wallet.entities.Transaction;
import com.wallet.utility.IdGenerator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.util.Date;

public class FakeTransaction {
    public static Transaction getFake() {
        Faker faker = Faker.instance();
        Transaction transaction = mock(Transaction.class);
        when(transaction.getTransactionId()).thenReturn(IdGenerator.genId());
        when(transaction.getTransactionStatus()).thenReturn(Transaction.Status.Pending);
        when(transaction.getTransactionType()).thenReturn(Transaction.Type.Deposit);
        when(transaction.getTransactionDate()).thenReturn(new Date());
        when(transaction.getTransactionSum()).thenReturn(BigDecimal.valueOf(1000));
        return transaction;
    }
}
