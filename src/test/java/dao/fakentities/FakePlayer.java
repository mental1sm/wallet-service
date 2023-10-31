package dao.fakentities;

import com.github.javafaker.Faker;
import com.wallet.domain.entities.Player;

import static org.mockito.Mockito.mock;

public class FakePlayer {
    public static Player getFake(String login) {
        Faker faker = Faker.instance();

        return Player.builder()
                .id(1)
                .login(login)
                .password("123321")
                .name(faker.name().name())
                .surname(faker.name().lastName())
                .build();
    }

}
