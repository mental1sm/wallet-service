package dao;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.entities.Player;
import com.wallet.infrastructure.configs.DatabaseConfig;
import com.wallet.infrastructure.db.liquibase.PostgresMigration;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import org.junit.jupiter.api.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.MethodName.class)
@Testcontainers
public class PlayerDaoTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test")
            .withReuse(true);

    private static PlayerDao playerDao;
    private static Player mockedPlayer;

    @BeforeAll
    public static void setUp() throws SQLException {
        postgresContainer.start();

        String jdbcUrl = postgresContainer.getJdbcUrl();
        String username = postgresContainer.getUsername();
        String password = postgresContainer.getPassword();

        DatabaseConfig.getInstance(jdbcUrl, username, password);
        PostgresMigration.migrate();


        playerDao = new PlayerDaoImpl();

        mockedPlayer = mock(Player.class);
        when(mockedPlayer.getPLogin()).thenReturn("some_login");
        when(mockedPlayer.getPPassword()).thenReturn("password");
        when(mockedPlayer.getName()).thenReturn("name");
        when(mockedPlayer.getSurname()).thenReturn("surname");
        when(mockedPlayer.getPermission()).thenReturn(Player.Permission.USER);
        when(mockedPlayer.getPermissionId()).thenReturn(3);

    }

    @AfterAll
    public static void tearDown() {
        postgresContainer.stop();
        postgresContainer.close();
    }

    @Test
    @Order(1)
    public void testSaveFindPlayer() throws PlayerAllreadyExistsException {

        playerDao.savePlayer(mockedPlayer);
        Player retrievedPlayer = playerDao.findPlayer(mockedPlayer.getPLogin());
        when(mockedPlayer.getPlayerID()).thenReturn(retrievedPlayer.getPlayerID());

        Assertions.assertEquals(mockedPlayer.getPLogin(), retrievedPlayer.getPLogin());
        Assertions.assertEquals(mockedPlayer.getPPassword(), retrievedPlayer.getPPassword());
        Assertions.assertThrowsExactly(PlayerAllreadyExistsException.class, () -> {playerDao.savePlayer(mockedPlayer);});
        Assertions.assertNull(playerDao.findPlayer("notexistinglogin"));
    }

    @Test
    @Order(2)
    public void testUpdatePlayer() {

        when(mockedPlayer.getPPassword()).thenReturn("password_edited");
        playerDao.updatePlayer(mockedPlayer);

        Player retrievedPlayer = playerDao.findPlayer(mockedPlayer.getPlayerID());

        Assertions.assertEquals(mockedPlayer.getPPassword(), retrievedPlayer.getPPassword());

    }

    @Test
    @Order(3)
    public void testDeletePlayer() {
        playerDao.deletePlayer(mockedPlayer);
        Player retrievedPlayer = playerDao.findPlayer(mockedPlayer.getPLogin());
        Assertions.assertNull(retrievedPlayer);
    }



}