package api.logoutUser;

import api.data.login.LoginSuccess;
import api.data.login.LoginCredentions;
import api.data.users.LogoutToken;
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
import static org.hamcrest.Matchers.containsString;

public class TestSuiteLogoutUserSuccessfully {
    private RegisterCredentials registerCredentials;
    private UserApiService userApiService;
    private RegisteredUser registeredUser;
    private LoginCredentions loginCredentions;
    private LoginSuccess loginSuccess;
    private LogoutToken logoutToken;
    private AccessToken accessToken;
    private UserService userService;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        userService = new UserService();
        accessToken = new AccessToken();
        loginCredentions = new LoginCredentions();
        logoutToken = new LogoutToken();
        // get new user
        registerCredentials = UsersFactory.getRandomUser();
        // register new user
        registeredUser = userService.registerUser(userApiService, registerCredentials);
    }

    @After
    public void tearDown() {
        // delete User
        accessToken.setAccessToken(loginSuccess.getAccessToken());
        userService.deleteUser(userApiService, accessToken);
    }

    @Feature("logout user")
    @Test
    @DisplayName("Can logout for valid user")
    public void testCanLogoutForValidUser() {
        // given
        userService.setLoginCredentials(registerCredentials, loginCredentions);
        loginSuccess = userService.loginUser(userApiService, loginCredentions, registerCredentials);
        logoutToken.setToken(loginSuccess.getRefreshToken());
        // expected
        userApiService
                .logoutUser(logoutToken)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("message", containsString("Successful logout")));
    }
}
