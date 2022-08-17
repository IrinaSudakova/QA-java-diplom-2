package api.deleteUser;

import api.data.register.RegisteredUser;
import api.data.register.RegisterCredentials;
import api.data.users.AccessToken;
import api.data.users.UsersFactory;
import api.services.UserApiService;
import api.services.UserService;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static api.conditions.Conditions.bodyField;
import static api.conditions.Conditions.statusCode;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

public class TestSuiteCanNotDeleteUser {

    private RegisterCredentials registerCredentials;
    private UserApiService userApiService;
    private RegisteredUser registeredUser;
    private AccessToken accessToken;
    private UserService userService;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        userService = new UserService();
        accessToken = new AccessToken();
        registerCredentials = UsersFactory.getRandomUser();
        // register new user
        registeredUser = userService.registerUser(userApiService, registerCredentials);
    }

    @After
    public void tearDown() {
        // delete User
        accessToken.setAccessToken(registeredUser.getAccessToken());
        userService.deleteUser(userApiService, accessToken);
    }

    @Feature("delete user")
    @Test
    @DisplayName("Can't delete for invalid token")
    public void testCanNotDeleteForInvalidToken() {
        // given
        accessToken.setAccessToken(registeredUser.getAccessToken() + "test");
        // expected
        userApiService
                .deleteUser(accessToken)
                .shouldHave(statusCode(403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("invalid signature")));
    }

    @Feature("delete user")
    @Test
    @DisplayName("Can't delete for empty token")
    public void testCanNotDeleteForEmptyToken() {
        // given
        accessToken.setAccessToken("");
        // expected
        userApiService
                .deleteUser(accessToken)
                .shouldHave(statusCode(401))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("You should be authorised")));
    }
}
