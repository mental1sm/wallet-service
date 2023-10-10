package dao;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.entities.Player;
import com.wallet.entities.Transaction;
import com.wallet.infrastructure.PlayerInMemoryRepository;
import com.wallet.infrastructure.UserSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class PlayerDaoTest {

    @Test
    public void pDaoTest(){
        PlayerInMemoryRepository repository = PlayerInMemoryRepository.getInstance();
        Player pl_1 = new Player("Test", "Test", "8832801244", "login", "pass");
        Player pl_2 = new Player("Test", "Test", "8832801248", "login!", "pass");
        PlayerDao playerDao = new PlayerDaoImpl();
        playerDao.savePlayer(pl_1);
        playerDao.savePlayer(pl_2);
        Player test = playerDao.loadPlayer("login", "pass");
        System.out.println(test.getName());
        Assertions.assertEquals(pl_1, playerDao.loadPlayer("login", "pass"));
        Assertions.assertEquals(pl_2, playerDao.loadPlayer("login!", "pass"));
    }
}
