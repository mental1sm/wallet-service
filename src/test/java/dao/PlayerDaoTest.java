package dao;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.entities.Player;
import com.wallet.infrastructure.PlayerInMemoryRepository;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class PlayerDaoTest {

    @Test
    public void pDaoTest() throws PlayerIsNotExistsException {
        PlayerInMemoryRepository repository = PlayerInMemoryRepository.getInstance();
        Player pl_1 = new Player("Test", "Test", "8832801244", "login", "pass");
        Player pl_2 = new Player("Test", "Test", "8832801248", "login!", "pass");
        PlayerDao playerDao = new PlayerDaoImpl();
        playerDao.savePlayer(pl_1);
        playerDao.savePlayer(pl_2);
        Player test = playerDao.findPlayer("login", "pass");
        System.out.println(test.getName());
        Assertions.assertEquals(pl_1, playerDao.findPlayer("login", "pass"));
        Assertions.assertEquals(pl_2, playerDao.findPlayer("login!", "pass"));
    }
}
