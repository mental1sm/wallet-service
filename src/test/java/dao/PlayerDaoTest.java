package dao;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.entities.Player;
import com.wallet.infrastructure.PlayerInMemoryRepository;
import com.wallet.utility.IdGenerator;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PlayerDaoTest {

    @BeforeAll
    public static void setUp() {
    }


    @Test
    public void playerSaveLoadTest() throws PlayerIsNotExistsException, PlayerAllreadyExistsException {
        PlayerInMemoryRepository repository = PlayerInMemoryRepository.getInstance();
        PlayerDao playerDao = new PlayerDaoImpl();

        Player testPlayer = mock(Player.class);
        when(testPlayer.getPLogin()).thenReturn("login");
        when(testPlayer.getPPassword()).thenReturn("pass");

        playerDao.savePlayer(testPlayer);

        Assertions.assertThat(
                playerDao.findPlayer(
                        testPlayer.getPLogin(),
                        testPlayer.getPPassword()
                ))
                .isNotNull()
                .satisfies(
                        player -> {
                            Assertions.assertThat(player.getPLogin()).isEqualTo(testPlayer.getPLogin());
                            Assertions.assertThat(player.getPPassword()).isEqualTo(testPlayer.getPPassword());
                        }
                );



        // Попытка сохранить игрока с тем же логином и паролем, что player1
        Assertions.assertThatThrownBy(() -> {
            playerDao.savePlayer(testPlayer);
        }).isInstanceOf(PlayerAllreadyExistsException.class);
    }

}
