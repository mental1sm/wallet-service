//package dao;
//
//import com.wallet.dao.user.UserDao;
//import com.wallet.dao.user.UserDaoImpl;
//import com.wallet.domain.entities.User;
//import com.wallet.utility.exceptions.UserAllreadyExistsException;
//import com.wallet.utility.exceptions.UserIsNotExistsException;
//import org.junit.jupiter.api.*;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.NoSuchElementException;
//import java.util.Optional;
//
//import static org.mockito.Mockito.mock;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@Testcontainers
//public class UserDaoTest {
//
//    private static UserDao userDao;
//    private static User fakePlayer;
//
//    @BeforeAll
//    public static void setUp() {
//        DatabaseContainer.setUp();
//        userDao = new UserDaoImpl();
//        fakePlayer = FakePlayer.getFake("some_login");
//        fakePlayer.setPassword("password");
//
//    }
//
//    @Test
//    @Order(1)
//    public void testSaveFindPlayer() throws UserAllreadyExistsException, UserIsNotExistsException {
//        userDao.savePlayer(fakePlayer);
//        User retrievedPlayer = userDao.findPlayer(fakePlayer.getLogin()).orElseThrow();
//
//        Assertions.assertEquals(fakePlayer.getLogin(), retrievedPlayer.getLogin());
//        Assertions.assertEquals(fakePlayer.getPassword(), retrievedPlayer.getPassword());
//    }
//
//    @Test
//    @Order(2)
//    public void testSaveExistingPlayer() {
//
//    }
//
//    @Test
//    @Order(3)
//    public void testFindNotExistingPlayer() throws UserIsNotExistsException {
//        Assertions.assertEquals(userDao.findPlayer("notexistinglogin"), Optional.empty());
//    }
//
//    @Test
//    @Order(4)
//    public void testUpdatePlayerPassword() throws UserIsNotExistsException {
//        fakePlayer = userDao.findPlayer(fakePlayer.getLogin()).orElseThrow();
//        fakePlayer.setPassword("newpass");
//        userDao.updatePlayer(fakePlayer);
//        User retrievedPlayer = userDao.findPlayer(fakePlayer.getId()).orElseThrow();
//        Assertions.assertEquals(fakePlayer.getPassword(), retrievedPlayer.getPassword());
//    }
//
//    @Test
//    @Order(5)
//    public void testDeletePlayer() throws UserIsNotExistsException {
//        userDao.deletePlayer(fakePlayer);
//        Assertions.assertThrowsExactly(NoSuchElementException.class, () -> userDao.findPlayer(fakePlayer.getLogin()).orElseThrow());
//    }
//
//}