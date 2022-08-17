package api.getUserInfo;

import api.data.login.LoginSuccess;
import api.data.login.LoginCredentions;
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

public class TestSuiteGetUserInfoSuccessfully {
    private RegisterCredentials registerCredentials;
    private UserApiService userApiService;
    private RegisteredUser registeredUser;
    private LoginCredentions loginCredentions;
    private LoginSuccess loginSuccess;
    private AccessToken accessToken;
    private UserService userService;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        userService = new UserService();
        accessToken = new AccessToken();
        loginCredentions = new LoginCredentions();
        registerCredentials = UsersFactory.getRandomUser();
        registeredUser = userService.registerUser(userApiService, registerCredentials);
    }

    @After
    public void tearDown() {
        // delete User
        accessToken.setAccessToken(registeredUser.getAccessToken());
        userService.deleteUser(userApiService, accessToken);
    }

    @Feature("get user")
    @Test
    @DisplayName("Can get info for valid user")
    public void testCanGetInfoForValidUser() {
        // given
        accessToken.setAccessToken(registeredUser.getAccessToken());
        // expected
        userApiService
                .getUser(accessToken)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("user.email", containsString(registerCredentials.getEmail())))
                .shouldHave(bodyField("user.name", containsString(registerCredentials.getName())));
    }
}
