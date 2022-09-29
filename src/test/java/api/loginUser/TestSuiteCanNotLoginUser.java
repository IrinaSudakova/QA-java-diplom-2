package api.loginUser;

import api.data.login.LoginCredentions;
import api.data.register.RegisteredUser;
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

public class TestSuiteCanNotLoginUser {
    private RegisterCredentials registerCredentials;
    private UserApiService userApiService;
    private RegisteredUser registeredUser;
    private LoginCredentions loginCredentions;
    private AccessToken accessToken;
    private BaseUserMethod baseUserMethod;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        baseUserMethod = new BaseUserMethod();
        loginCredentions = new LoginCredentions();
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

    @Feature("login user")
    @Test
    @DisplayName("Can't login without email")
    public void testCanLoginForValidUserWithoutEmail() {
        // set loginUser
        loginCredentions.setPassword(registerCredentials.getPassword());
        // expected
        userApiService
                .loginUser(loginCredentions)
                .shouldHave(statusCode(STATUS_CODE_401))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString(MESSAGE_EMAIL_OR_PASSWORD_INCORRECT)));
    }

    @Feature("login user")
    @Test
    @DisplayName("Can't login with incorrect email")
    public void testCanLoginForValidUserWithIncorrectEmail() {
        // set loginUser
        loginCredentions.setEmail(registerCredentials.getEmail() + "test");
        loginCredentions.setPassword(registerCredentials.getPassword());
        // expected
        userApiService
                .loginUser(loginCredentions)
                .shouldHave(statusCode(STATUS_CODE_401))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString(MESSAGE_EMAIL_OR_PASSWORD_INCORRECT)));
    }

    @Feature("login user")
    @Test
    @DisplayName("Can't login without password")
    public void testCanLoginForValidUserWithoutPassword() {
        // set loginUser
        loginCredentions.setEmail(registerCredentials.getEmail());
        // expected
        userApiService
                .loginUser(loginCredentions)
                .shouldHave(statusCode(STATUS_CODE_401))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString(MESSAGE_EMAIL_OR_PASSWORD_INCORRECT)));
    }

    @Feature("login user")
    @Test
    @DisplayName("Can't login with incorrect password")
    public void testCanLoginForValidUserWithIncorrectPassword() {
        // set loginUser
        loginCredentions.setEmail(registerCredentials.getEmail());
        loginCredentions.setPassword(registerCredentials.getPassword() + "test");
        // expected
        userApiService
                .loginUser(loginCredentions)
                .shouldHave(statusCode(STATUS_CODE_401))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString(MESSAGE_EMAIL_OR_PASSWORD_INCORRECT)));
    }

    @Feature("login user")
    @Test
    @DisplayName("Can't login as empty user")
    public void testCanLoginAsEmptyUser() {
        // expected
        userApiService
                .loginUser(loginCredentions)
                .shouldHave(statusCode(STATUS_CODE_401))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString(MESSAGE_EMAIL_OR_PASSWORD_INCORRECT)));
    }
}
