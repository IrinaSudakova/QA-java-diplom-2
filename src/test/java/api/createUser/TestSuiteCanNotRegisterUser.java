package api.createUser;

import api.data.register.RegisterCredentials;
import api.data.users.UsersFactory;
import api.services.UserApiService;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static api.conditions.Conditions.bodyField;
import static api.conditions.Conditions.statusCode;
import static org.hamcrest.Matchers.*;

public class TestSuiteCanNotRegisterUser {

    private UserApiService userApiService;
    private RegisterCredentials registerCredentials;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register without name")
    public void testCanRegisterAsValidUserWithoutName() {
        // given
        registerCredentials = UsersFactory.getUserWithoutName();
        // expected
        userApiService
                .registerUser(registerCredentials)
                .shouldHave(statusCode(403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("Email, password and name are required fields")));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register without password")
    public void testCanRegisterAsValidUserWithoutPassword() {
        // given
        registerCredentials = UsersFactory.getUserWithoutPassword();
        // expected
        userApiService
                .registerUser(registerCredentials)
                .shouldHave(statusCode(403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("Email, password and name are required fields")));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register without email")
    public void testCanRegisterAsValidUserWithoutEmail() {
        // given
        registerCredentials = UsersFactory.getUserWithoutEmail();
        // expected
        userApiService
                .registerUser(registerCredentials)
                .shouldHave(statusCode(403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("Email, password and name are required fields")));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register empty user")
    public void testCanRegisterAsEmptyUser() {
        // given
        registerCredentials = UsersFactory.getEmptyUser();
        // expected
        userApiService
                .registerUser(registerCredentials)
                .shouldHave(statusCode(403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("Email, password and name are required fields")));
    }
}
