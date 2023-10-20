package dao.fakentities;

import com.github.javafaker.Faker;
import com.wallet.entities.Player;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FakePlayer {
    public static Player getFake(String login) {
        Faker faker = Faker.instance();

        return Player.builder()
                .id(1)
                .login(login)
                .password("123321")
                .name(faker.name().name())
                .surname(faker.name().lastName())
                .permissionId(3)
                .permissionLevel(Player.Permission.USER)
                .build();
    }

}
