package api.test.logoutUser;

import api.data.register.RegisterCredentials;
import api.data.register.RegisteredUser;
import api.data.users.AccessToken;
import api.data.users.LogoutToken;
import api.data.users.UsersFactory;
import api.services.BaseUserMethod;
import api.services.UserApiService;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static api.conditions.Conditions.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class TestSuiteCanNotLogoutUser {
    private RegisterCredentials registerCredentials;
    private UserApiService userApiService;
    private RegisteredUser registeredUser;
    private LogoutToken logoutToken;
    private AccessToken accessToken;
    private BaseUserMethod baseUserMethod;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        baseUserMethod = new BaseUserMethod();
        logoutToken = new LogoutToken();
        accessToken = new AccessToken();
        registerCredentials = UsersFactory.getRandomUser();
        // register new user
        registeredUser = baseUserMethod.registerUserWithCurrent(registerCredentials);
    }

    @After
    public void tearDown() {
        // delete User
        accessToken.setAccessToken(registeredUser.getAccessToken());
        baseUserMethod.deleteUserWithCurrent(accessToken);
    }

    @Feature("logout user")
    @Test
    @DisplayName("Can't logout with incorrect refreshToken")
    public void testCanNotLogoutWithIncorrectRefreshToken() {
        // given
        logoutToken.setToken(registeredUser.getRefreshToken() + "api/test");
        // expected
        userApiService
                .logoutUser(logoutToken)
                .shouldHave(statusCode(STATUS_CODE_404))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString(MESSAGE_TOKEN_REQUIRED)));
    }

    @Feature("logout user")
    @Test
    @DisplayName("Can't logout with empty refreshToken")
    public void testCanNotLogoutWithEmptyRefreshToken() {
        //
        logoutToken.setToken("");
        // expected
        userApiService
                .logoutUser(logoutToken)
                .shouldHave(statusCode(STATUS_CODE_404))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString(MESSAGE_TOKEN_REQUIRED)));
    }
}
