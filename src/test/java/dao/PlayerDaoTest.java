package dao;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.entities.Player;
import com.wallet.infrastructure.configs.DatabaseConfig;
import com.wallet.infrastructure.db.liquibase.PostgresMigration;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Testcontainers
public class PlayerDaoTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    private static PlayerDao playerDao;

    @BeforeAll
    public static void setUp() throws SQLException {
        postgresContainer.start();

        // Получаем URL, имя пользователя и пароль из контейнера
        String jdbcUrl = postgresContainer.getJdbcUrl();
        String username = postgresContainer.getUsername();
        String password = postgresContainer.getPassword();

        DatabaseConfig.getInstance(jdbcUrl, username, password);
        PostgresMigration.migrate();


        // Инициализируем наш DAO с настройками из контейнера
        playerDao = new PlayerDaoImpl();
    }

    @AfterAll
    public static void tearDown() {
        postgresContainer.stop();
    }

    @Test
    public void testSavePlayer() throws PlayerAllreadyExistsException {
        Player mockedPlayer = mock(Player.class);
        when(mockedPlayer.getPLogin()).thenReturn("login");
        when(mockedPlayer.getPPassword()).thenReturn("password");
        when(mockedPlayer.getName()).thenReturn("name");
        when(mockedPlayer.getSurname()).thenReturn("surname");

        playerDao.savePlayer(mockedPlayer);

        // Проверьте, что игрок был успешно сохранен
        Player retrievedPlayer = playerDao.findPlayer(mockedPlayer.getPLogin());
        Assertions.assertEquals(mockedPlayer.getPLogin(),retrievedPlayer.getPLogin());
        Assertions.assertEquals(mockedPlayer.getPPassword(),retrievedPlayer.getPPassword());
    }


}