package dao;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.domain.entities.Player;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;
import dao.fakentities.FakePlayer;
import org.junit.jupiter.api.*;
import org.postgresql.util.PSQLException;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.mock;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
public class PlayerDaoTest {

    private static PlayerDao playerDao;
    private static Player fakePlayer;

    @BeforeAll
    public static void setUp() {
        DatabaseContainer.setUp();
        playerDao = new PlayerDaoImpl();
        fakePlayer = FakePlayer.getFake("some_login");
        fakePlayer.setPassword("password");

    }

    @Test
    @Order(1)
    public void testSaveFindPlayer() throws PlayerAllreadyExistsException, PlayerIsNotExistsException {
        playerDao.savePlayer(fakePlayer);
        Player retrievedPlayer = playerDao.findPlayer(fakePlayer.getLogin()).orElseThrow();

        Assertions.assertEquals(fakePlayer.getLogin(), retrievedPlayer.getLogin());
        Assertions.assertEquals(fakePlayer.getPassword(), retrievedPlayer.getPassword());
    }

    @Test
    @Order(2)
    public void testSaveExistingPlayer() {

    }

    @Test
    @Order(3)
    public void testFindNotExistingPlayer() throws PlayerIsNotExistsException {
        Assertions.assertEquals(playerDao.findPlayer("notexistinglogin"), Optional.empty());
    }

    @Test
    @Order(4)
    public void testUpdatePlayerPassword() throws PlayerIsNotExistsException {
        fakePlayer = playerDao.findPlayer(fakePlayer.getLogin()).orElseThrow();
        fakePlayer.setPassword("newpass");
        playerDao.updatePlayer(fakePlayer);
        Player retrievedPlayer = playerDao.findPlayer(fakePlayer.getId()).orElseThrow();
        Assertions.assertEquals(fakePlayer.getPassword(), retrievedPlayer.getPassword());
    }

    @Test
    @Order(5)
    public void testUpdatePlayerPermission() throws PlayerIsNotExistsException {
        fakePlayer.setPermissionId(2);
        playerDao.updatePlayer(fakePlayer);
        Player retrievedPlayer = playerDao.findPlayer(fakePlayer.getId()).orElseThrow();
        Assertions.assertEquals(retrievedPlayer.getPermissionLevel(), Player.Permission.ADMIN);
    }

    @Test
    @Order(6)
    public void testDeletePlayer() throws PlayerIsNotExistsException {
        playerDao.deletePlayer(fakePlayer);
        Assertions.assertThrowsExactly(NoSuchElementException.class, () -> playerDao.findPlayer(fakePlayer.getLogin()).orElseThrow());
    }

}