package api.logoutUser;

import api.data.login.LoginSuccess;
import api.data.login.LoginCredentions;
import api.data.users.LogoutToken;
import api.data.register.RegisterCredentials;
import api.data.users.AccessToken;
import api.data.users.UsersFactory;
import api.services.UserApiService;
import api.services.BaseUserMethod;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static api.conditions.Conditions.*;
import static org.hamcrest.Matchers.*;

public class TestSuiteLogoutUserSuccessfully {
    private RegisterCredentials registerCredentials;
    private UserApiService userApiService;
    private LoginCredentions loginCredentions;
    private LoginSuccess loginSuccess;
    private LogoutToken logoutToken;
    private AccessToken accessToken;
    private BaseUserMethod baseUserMethod;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        baseUserMethod = new BaseUserMethod();
        accessToken = new AccessToken();
        loginCredentions = new LoginCredentions();
        logoutToken = new LogoutToken();
        // get new user
        registerCredentials = UsersFactory.getRandomUser();
        // register new user
        baseUserMethod.registerUserWithCurrent(registerCredentials);
    }

    @After
    public void tearDown() {
        // delete User
        accessToken.setAccessToken(loginSuccess.getAccessToken());
        baseUserMethod.deleteUserWithCurrent(accessToken);
    }

    @Feature("logout user")
    @Test
    @DisplayName("Can logout for valid user")
    public void testCanLogoutForValidUser() {
        // given
        baseUserMethod.setCurrentLoginCredentials(registerCredentials, loginCredentions);
        loginSuccess = baseUserMethod.loginUserWithCurrent(loginCredentions, registerCredentials);
        logoutToken.setToken(loginSuccess.getRefreshToken());
        // expected
        userApiService
                .logoutUser(logoutToken)
                .shouldHave(statusCode(STATUS_CODE_200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("message", containsString(MESSAGE_SUCCESSFUL_LOGOUT)));
    }
}
