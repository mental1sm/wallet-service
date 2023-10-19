package dao.fakemocks;

import com.github.javafaker.Faker;
import com.wallet.entities.Player;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FakePlayer {
    public static Player getFake() {
        Faker faker = Faker.instance();
        Player player = mock(Player.class);
        when(player.getPlayerID()).thenReturn((long) 1);
        when(player.getPPassword()).thenReturn("123321");
        when(player.getName()).thenReturn((faker.name().name()));
        when(player.getSurname()).thenReturn(faker.name().lastName());
        when(player.getPermissionId()).thenReturn(3);
        return player;
    }

}
