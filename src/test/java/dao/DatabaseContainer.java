//package dao;
//
//import com.wallet.infrastructure.db.liquibase.PostgresMigration;
//import lombok.Getter;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//@Getter
//@Testcontainers
//public class DatabaseContainer {
//    private static String jdbcUrl;
//    private static String username;
//    private static String password;
//    @Container
//    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16")
//            .withDatabaseName("test")
//            .withUsername("test")
//            .withPassword("test")
//            .withReuse(true);
//
//    @BeforeAll
//    public static void setUp() {
//        postgresContainer.start();
//
//        jdbcUrl = postgresContainer.getJdbcUrl();
//        username = postgresContainer.getUsername();
//        password = postgresContainer.getPassword();
//
//        DatabaseConfig.getInstance(jdbcUrl, username, password);
//        try {
//            PostgresMigration.migrate();
//        } catch (Exception e) {
//            System.out.println("Произошла ошибка в миграции!!!");
//            e.printStackTrace();
//        }
//
//    }
//
//
//    @AfterAll
//    public static void tearDown() {
//        postgresContainer.stop();
//        postgresContainer.close();
//    }
//}
