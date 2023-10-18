package dao;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.entities.Player;
import com.wallet.infrastructure.db.liquibase.Migration;
import com.wallet.infrastructure.db.liquibase.PostgresMigration;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PlayerDaoTest {

    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testwalletdb")
            .withUsername("mentalism")
            .withPassword("123321");

    @BeforeAll
    public static void setUp() {
        postgresContainer.start();
        PostgresMigration.migrate();
    }


    @Test
    public void playerSaveLoadTest() throws PlayerIsNotExistsException, PlayerAllreadyExistsException {
        PlayerDao playerDao = new PlayerDaoImpl();

        Player testPlayer = mock(Player.class);
        when(testPlayer.getPLogin()).thenReturn("login");
        when(testPlayer.getPPassword()).thenReturn("pass");

        playerDao.savePlayer(testPlayer);

        Player foundPlayer = playerDao.findPlayer(testPlayer.getPLogin());
        Assertions.assertThat(testPlayer.getPLogin()).isEqualTo(foundPlayer.getPLogin());
        Assertions.assertThat(testPlayer.getPPassword()).isEqualTo(foundPlayer.getPPassword());

        // Попытка сохранить игрока с тем же логином и паролем, что player1
        Assertions.assertThatThrownBy(() -> {
            playerDao.savePlayer(testPlayer);
        }).isInstanceOf(PlayerAllreadyExistsException.class);
    }

    @AfterAll
    public static void tearDown() {
        postgresContainer.stop();
    }

}
