package dao;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.entities.Player;
import com.wallet.infrastructure.configs.DatabaseConfig;
import com.wallet.infrastructure.db.liquibase.PostgresMigration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.SQLException;


public class DatabaseContainer {

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


    }

    @AfterAll
    public static void tearDown() {
        postgresContainer.stop();
        postgresContainer.close();
    }
}
