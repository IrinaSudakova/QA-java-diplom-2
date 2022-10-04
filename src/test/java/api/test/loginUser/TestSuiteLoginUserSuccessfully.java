package api.test.loginUser;

import api.data.login.LoginCredentions;
import api.data.login.LoginSuccess;
import api.data.register.RegisterCredentials;
import api.data.users.AccessToken;
import api.data.users.UsersFactory;
import api.services.BaseUserMethod;
import api.services.UserApiService;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static api.conditions.Conditions.*;
import static api.data.users.AccessToken.regexAccessToken;
import static org.hamcrest.Matchers.*;


public class TestSuiteLoginUserSuccessfully {

    private RegisterCredentials registerCredentials;
    private UserApiService userApiService;
    private LoginCredentions loginCredentions;
    private LoginSuccess loginSuccess;
    private AccessToken accessToken;
    private BaseUserMethod baseUserMethod;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        baseUserMethod = new BaseUserMethod();
        accessToken = new AccessToken();
        loginCredentions = new LoginCredentions();
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

    @Feature("login user")
    @Test
    @DisplayName("Can login for valid user")
    public void testCanLoginForValidUser() {
        // given
        loginCredentions.setEmail(registerCredentials.getEmail());
        loginCredentions.setPassword(registerCredentials.getPassword());
        // expected
        loginSuccess = userApiService
                .loginUser(loginCredentions)
                .shouldHave(statusCode(STATUS_CODE_200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("accessToken", matchesPattern(regexAccessToken)))
                .shouldHave(bodyField("refreshToken", notNullValue()))
                .shouldHave(bodyField("user.email", containsString(registerCredentials.getEmail())))
                .shouldHave(bodyField("user.name", containsString(registerCredentials.getName())))
                .asPojo(LoginSuccess.class);
    }
}
