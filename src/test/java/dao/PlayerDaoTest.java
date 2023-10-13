package dao;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.entities.Player;
import com.wallet.infrastructure.PlayerInMemoryRepository;
import com.wallet.utility.IdGenerator;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class PlayerDaoTest {

    @Test
    public void savePlayerTest() throws PlayerIsNotExistsException {
        PlayerInMemoryRepository repository = PlayerInMemoryRepository.getInstance();

        Player player1 = new Player(IdGenerator.genId(), "Test", "test", "login", "pass");
        Player player2 = new Player(IdGenerator.genId(), "Test", "test", "login!!!!", "pass");
        Player player3 = new Player(IdGenerator.genId(), "Test", "Test", "login", "pass");

        PlayerDao playerDao = new PlayerDaoImpl();

        playerDao.savePlayer(player1);
        playerDao.savePlayer(player2);

        Player testPlayer1 = playerDao.findPlayer("login", "pass");
        Player testPlayer2 = playerDao.findPlayer("login!", "pass");

        Assertions.assertThat(player1).isEqualTo(testPlayer1);
        Assertions.assertThat(player2).isEqualTo(testPlayer2);
        Assertions.assertThatThrownBy(() -> {
            playerDao.savePlayer(player3);
        }).isInstanceOf(PlayerAllreadyExistsException.class);
    }

    @Test
    public void findPlayerTest() throws PlayerIsNotExistsException {
        PlayerInMemoryRepository repository = PlayerInMemoryRepository.getInstance();

        Player pl_1 = new Player(IdGenerator.genId(), "Test", "test", "login", "pass");

        PlayerDao playerDao = new PlayerDaoImpl();
        playerDao.savePlayer(pl_1);

        Player test = playerDao.findPlayer("login", "pass");
        Assertions.assertThat(pl_1).isEqualTo(test);
    }
}
