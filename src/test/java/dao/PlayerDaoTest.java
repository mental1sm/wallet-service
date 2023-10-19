package dao;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.entities.Player;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import org.junit.jupiter.api.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.testcontainers.junit.jupiter.Testcontainers;
import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
public class PlayerDaoTest {

    static PlayerDao playerDao;
    static Player mockedPlayer;

    @BeforeAll
    public static void setUp() {
        playerDao = new PlayerDaoImpl();
        mockedPlayer = mock(Player.class);
        when(mockedPlayer.getPLogin()).thenReturn("some_login");
        when(mockedPlayer.getPPassword()).thenReturn("password");
        when(mockedPlayer.getName()).thenReturn("name");
        when(mockedPlayer.getSurname()).thenReturn("surname");
        when(mockedPlayer.getPermission()).thenReturn(Player.Permission.USER);
        when(mockedPlayer.getPermissionId()).thenReturn(3);

    }

    @Test
    @Order(1)
    public void testSaveFindPlayer() throws PlayerAllreadyExistsException {
        playerDao.savePlayer(mockedPlayer);
        Player retrievedPlayer = playerDao.findPlayer(mockedPlayer.getPLogin());
        when(mockedPlayer.getPlayerID()).thenReturn(retrievedPlayer.getPlayerID());

        Assertions.assertEquals(mockedPlayer.getPLogin(), retrievedPlayer.getPLogin());
        Assertions.assertEquals(mockedPlayer.getPPassword(), retrievedPlayer.getPPassword());
    }

    @Test
    @Order(2)
    public void testSaveExistingPlayer() {
        Assertions.assertThrowsExactly(PlayerAllreadyExistsException.class, () -> {playerDao.savePlayer(mockedPlayer);});
    }

    @Test
    @Order(3)
    public void testFindNotExistingPlayer() {
        Assertions.assertNull(playerDao.findPlayer("notexistinglogin"));
    }

    @Test
    @Order(4)
    public void testUpdatePlayerPassword() {
        when(mockedPlayer.getPPassword()).thenReturn("password_edited");
        playerDao.updatePlayer(mockedPlayer);
        Player retrievedPlayer = playerDao.findPlayer(mockedPlayer.getPlayerID());
        Assertions.assertEquals(mockedPlayer.getPPassword(), retrievedPlayer.getPPassword());
    }

    @Test
    @Order(5)
    public void testUpdatePlayerPermission() {
        when(mockedPlayer.getPermissionId()).thenReturn(2);
        playerDao.updatePlayer(mockedPlayer);
        Player retrievedPlayer = playerDao.findPlayer(mockedPlayer.getPlayerID());
        Assertions.assertEquals(retrievedPlayer.getPermission(), Player.Permission.ADMIN);
    }

    @Test
    @Order(6)
    public void testDeletePlayer() {
        playerDao.deletePlayer(mockedPlayer);
        Player retrievedPlayer = playerDao.findPlayer(mockedPlayer.getPLogin());
        Assertions.assertNull(retrievedPlayer);
    }
}