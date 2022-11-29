package api.test.deleteUser;

import api.data.register.RegisterCredentials;
import api.data.register.RegisteredUser;
import api.data.users.AccessToken;
import api.data.users.UsersFactory;
import api.services.BaseUserApiMethod;
import api.services.UserApiService;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static api.conditions.Conditions.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class TestSuiteCanNotDeleteUser {

    private RegisterCredentials registerCredentials;
    private UserApiService userApiService;
    private RegisteredUser registeredUser;
    private AccessToken accessToken;
    private BaseUserApiMethod baseUserApiMethod;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        baseUserApiMethod = new BaseUserApiMethod();
        accessToken = new AccessToken();
        registerCredentials = UsersFactory.getRandomUser();
        // register new user
        registeredUser = baseUserApiMethod.registerUserWithCurrent(registerCredentials);
    }

    @After
    public void tearDown() {
        // delete User
        accessToken.setAccessToken(registeredUser.getAccessToken());
        baseUserApiMethod.deleteUserWithCurrent(accessToken);
    }

    @Feature("delete user")
    @Test
    @DisplayName("Can't delete for invalid token")
    public void testCanNotDeleteForInvalidToken() {
        // given
        accessToken.setAccessToken(registeredUser.getAccessToken() + "api/test");
        // expected
        userApiService
                .deleteUser(accessToken)
                .shouldHave(statusCode(STATUS_CODE_403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString(MESSAGE_INVALID_SIGNATURE)));
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
                .shouldHave(statusCode(STATUS_CODE_401))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString(MESSAGE_SHOULD_BE_AUTHORISED)));
    }
}
