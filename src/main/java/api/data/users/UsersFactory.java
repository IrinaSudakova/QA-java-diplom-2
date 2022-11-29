package api.data.users;

import api.data.register.RegisterCredentials;
import com.github.javafaker.Faker;
import lombok.Builder;

import java.util.Locale;

@Builder
public class UsersFactory {
    private static final Faker faker = new Faker(new Locale("en"));
    public static RegisterCredentials getRandomUser() {
        String firstName = faker.name().firstName();
        String password = faker.internet().password();
        String email = (firstName + "." + faker.name().lastName() + "@example.com").toLowerCase(Locale.ROOT);
        return RegisterCredentials.builder()
                .name(firstName)
                .password(password)
                .email(email)
                .build();
    }

    public static RegisterCredentials getUserWithoutName() {
        String password = faker.internet().password();
        String email = faker.internet().emailAddress();
        return RegisterCredentials.builder()
                .password(password)
                .email(email)
                .build();
    }

    public static RegisterCredentials getUserWithoutPassword() {
        String firstName = faker.name().firstName();
        String email = faker.internet().emailAddress();
        return RegisterCredentials.builder()
                .name(firstName)
                .email(email)
                .build();
    }

    public static RegisterCredentials getUserWithoutEmail() {
        String firstName = faker.name().firstName();
        String password = faker.internet().password();
        return RegisterCredentials.builder()
                .name(firstName)
                .password(password)
                .build();
    }

    public static RegisterCredentials getEmptyUser() {
        return RegisterCredentials.builder()
                .build();
    }
}
