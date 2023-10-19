package dao;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.entities.Player;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
public class PlayerDaoTest {

    private static PlayerDao playerDao;
    private static Player mockedPlayer;

    @BeforeAll
    public static void setUp() {
        DatabaseContainer.setUp();
        playerDao = new PlayerDaoImpl();
        mockedPlayer = mock(Player.class);
        when(mockedPlayer.getLogin()).thenReturn("some_login");
        when(mockedPlayer.getPassword()).thenReturn("password");
        when(mockedPlayer.getName()).thenReturn("name");
        when(mockedPlayer.getSurname()).thenReturn("surname");
        when(mockedPlayer.getPermissionLevel()).thenReturn(Player.Permission.USER);
        when(mockedPlayer.getPermissionId()).thenReturn(3);

    }

    @Test
    @Order(1)
    public void testSaveFindPlayer() throws PlayerAllreadyExistsException {
        playerDao.savePlayer(mockedPlayer);
        Player retrievedPlayer = playerDao.findPlayer(mockedPlayer.getLogin());
        when(mockedPlayer.getId()).thenReturn(retrievedPlayer.getId());

        Assertions.assertEquals(mockedPlayer.getLogin(), retrievedPlayer.getLogin());
        Assertions.assertEquals(mockedPlayer.getPassword(), retrievedPlayer.getPassword());
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
        when(mockedPlayer.getPassword()).thenReturn("password_edited");
        playerDao.updatePlayer(mockedPlayer);
        Player retrievedPlayer = playerDao.findPlayer(mockedPlayer.getId());
        Assertions.assertEquals(mockedPlayer.getPassword(), retrievedPlayer.getPassword());
    }

    @Test
    @Order(5)
    public void testUpdatePlayerPermission() {
        when(mockedPlayer.getPermissionId()).thenReturn(2);
        playerDao.updatePlayer(mockedPlayer);
        Player retrievedPlayer = playerDao.findPlayer(mockedPlayer.getId());
        Assertions.assertEquals(retrievedPlayer.getPermissionLevel(), Player.Permission.ADMIN);
    }

    @Test
    @Order(6)
    public void testDeletePlayer() {
        playerDao.deletePlayer(mockedPlayer);
        Player retrievedPlayer = playerDao.findPlayer(mockedPlayer.getLogin());
        Assertions.assertNull(retrievedPlayer);
    }

}