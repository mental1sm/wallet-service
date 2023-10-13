package dao;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.entities.Player;
import com.wallet.infrastructure.PlayerInMemoryRepository;
import com.wallet.utility.IdGenerator;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class PlayerDaoTest {

    public void savePlayerTest() throws PlayerIsNotExistsException {
        PlayerInMemoryRepository repository = PlayerInMemoryRepository.getInstance();
        Player pl_1 = new Player(IdGenerator.genId(), "Test", "test", "login", "pass");
        Player pl_2 = new Player(IdGenerator.genId(), "Test", "test", "login!", "pass");
        PlayerDao playerDao = new PlayerDaoImpl();

        playerDao.savePlayer(pl_1);
        playerDao.savePlayer(pl_2);

        Player test1 = playerDao.findPlayer("login", "pass");
        Player test2 = playerDao.findPlayer("login!", "pass");

        Assertions.assertEquals(pl_1, test1);
        Assertions.assertEquals(pl_2, test2);
    }

    @Test
    public void findPlayerTest() throws PlayerIsNotExistsException {
        PlayerInMemoryRepository repository = PlayerInMemoryRepository.getInstance();
        Player pl_1 = new Player(IdGenerator.genId(), "Test", "test", "login", "pass");
        PlayerDao playerDao = new PlayerDaoImpl();

        playerDao.savePlayer(pl_1);

        Player test = playerDao.findPlayer("login", "pass");

        Assertions.assertEquals(pl_1, test);
    }
}
