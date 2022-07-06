package api.data.users;

import api.data.register.RegisterUser;
import com.github.javafaker.Faker;
import lombok.Builder;

import java.util.Locale;

@Builder
public class UsersFactory {
    private static final Faker faker = new Faker(new Locale("en"));
    public static RegisterUser getRandomUser() {
        String firstName = faker.name().firstName();
        String password = faker.internet().password();
        String email = (firstName + "." + faker.name().lastName() + "@example.com").toLowerCase(Locale.ROOT);
        return RegisterUser.builder()
                .name(firstName)
                .password(password)
                .email(email)
                .build();
    }

    public static RegisterUser getUserWithoutName() {
        String password = faker.internet().password();
        String email = faker.internet().emailAddress();
        return RegisterUser.builder()
                .password(password)
                .email(email)
                .build();
    }

    public static RegisterUser getUserWithoutPassword() {
        String firstName = faker.name().firstName();
        String email = faker.internet().emailAddress();
        return RegisterUser.builder()
                .name(firstName)
                .email(email)
                .build();
    }

    public static RegisterUser getUserWithoutEmail() {
        String firstName = faker.name().firstName();
        String password = faker.internet().password();
        return RegisterUser.builder()
                .name(firstName)
                .password(password)
                .build();
    }

    public static RegisterUser getEmptyUser() {
        return RegisterUser.builder()
                .build();
    }
}
