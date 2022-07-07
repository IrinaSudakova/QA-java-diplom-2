package api.logoutUser;

import api.data.login.LoginCredentions;
import api.data.users.LogoutToken;
import api.data.register.RegisteredUser;
import api.data.register.RegisterCredentials;
import api.data.users.AccessToken;
import api.data.users.UsersFactory;
import api.services.*;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static api.conditions.Conditions.bodyField;
import static api.conditions.Conditions.statusCode;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsString;

public class TestSuiteCanNotLogoutUser {
    private RegisterCredentials registerCredentials;
    private UserApiService userApiService;
    private RegisteredUser registeredUser;
    private LoginCredentions loginCredentions;
    private LogoutToken logoutToken;
    private AccessToken accessToken;
    private UserService userService;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        userService = new UserService();
        logoutToken = new LogoutToken();
        accessToken = new AccessToken();
        loginCredentions = new LoginCredentions();
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

    @Feature("logout user")
    @Test
    @DisplayName("Can't logout with incorrect refreshToken")
    public void testCanNotLogoutWithIncorrectRefreshToken() {
        // given
        logoutToken.setToken(registeredUser.getRefreshToken() + "test");
        // expected
        userApiService
                .logoutUser(logoutToken)
                .shouldHave(statusCode(404))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("Token required")));
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
                .shouldHave(statusCode(404))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("Token required")));
    }
}
