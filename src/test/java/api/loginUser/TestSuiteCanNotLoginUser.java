package api.loginUser;

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

public class TestSuiteCanNotLoginUser {
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
        loginCredentions = new LoginCredentions();
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

    @Feature("login user")
    @Test
    @DisplayName("Can't login without email")
    public void testCanLoginForValidUserWithoutEmail() {
        // set loginUser
        loginCredentions.setPassword(registerCredentials.getPassword());
        // expected
        loginSuccess = userApiService
                .loginUser(loginCredentions)
                .shouldHave(statusCode(401))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("email or password are incorrect")))
                .asPojo(LoginSuccess.class);
    }

    @Feature("login user")
    @Test
    @DisplayName("Can't login with incorrect email")
    public void testCanLoginForValidUserWithIncorrectEmail() {
        // set loginUser
        loginCredentions.setEmail(registerCredentials.getEmail() + "test");
        loginCredentions.setPassword(registerCredentials.getPassword());
        // expected
        loginSuccess = userApiService
                .loginUser(loginCredentions)
                .shouldHave(statusCode(401))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("email or password are incorrect")))
                .asPojo(LoginSuccess.class);
    }

    @Feature("login user")
    @Test
    @DisplayName("Can't login without password")
    public void testCanLoginForValidUserWithoutPassword() {
        // set loginUser
        loginCredentions.setEmail(registerCredentials.getEmail());
        // expected
        loginSuccess = userApiService
                .loginUser(loginCredentions)
                .shouldHave(statusCode(401))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("email or password are incorrect")))
                .asPojo(LoginSuccess.class);
    }

    @Feature("login user")
    @Test
    @DisplayName("Can't login with incorrect password")
    public void testCanLoginForValidUserWithIncorrectPassword() {
        // set loginUser
        loginCredentions.setEmail(registerCredentials.getEmail());
        loginCredentions.setPassword(registerCredentials.getPassword() + "test");
        // expected
        loginSuccess = userApiService
                .loginUser(loginCredentions)
                .shouldHave(statusCode(401))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("email or password are incorrect")))
                .asPojo(LoginSuccess.class);
    }

    @Feature("login user")
    @Test
    @DisplayName("Can't login as empty user")
    public void testCanLoginAsEmptyUser() {
        // expected
        loginSuccess = userApiService
                .loginUser(loginCredentions)
                .shouldHave(statusCode(401))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("email or password are incorrect")))
                .asPojo(LoginSuccess.class);
    }
}
