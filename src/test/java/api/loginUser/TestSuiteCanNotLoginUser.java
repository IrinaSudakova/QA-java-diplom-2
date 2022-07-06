package api.loginUser;

import api.data.login.LoginSuccess;
import api.data.login.LoginUser;
import api.data.register.RegisterSuccess;
import api.data.register.RegisterUser;
import api.data.users.UsersFactory;
import api.services.UserApiService;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static api.conditions.Conditions.bodyField;
import static api.conditions.Conditions.statusCode;
import static api.data.users.AccessToken.regexAccessToken;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsString;

public class TestSuiteCanNotLoginUser {
    private RegisterUser registerUser;
    private UserApiService userApiService;
    private RegisterSuccess registerSuccess;
    private LoginUser loginUser;
    private LoginSuccess loginSuccess;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        loginUser = new LoginUser();
        registerUser = UsersFactory.getRandomUser();
        registerSuccess = userApiService
                .registerUser(registerUser)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("user.email", containsString(registerUser.getEmail())))
                .shouldHave(bodyField("user.name", containsString(registerUser.getName())))
                .shouldHave(bodyField("accessToken", matchesPattern(regexAccessToken)))
                .shouldHave(bodyField("refreshToken", notNullValue()))
                .asPojo(RegisterSuccess.class);

    }

    @After
    public void tearDown() {
        // delete User
        userApiService
                .deleteUser(registerSuccess.getAccessToken())
                .shouldHave(statusCode(202))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("message", containsString("User successfully removed")));
    }

    @Feature("login user")
    @Test
    @DisplayName("Can't login without email")
    public void testCanLoginForValidUserWithoutEmail() {
        // set loginUser
        loginUser.setPassword(registerUser.getPassword());
        // expected
        loginSuccess = userApiService
                .loginUser(loginUser)
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
        loginUser.setEmail(registerUser.getEmail() + "test");
        loginUser.setPassword(registerUser.getPassword());
        // expected
        loginSuccess = userApiService
                .loginUser(loginUser)
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
        loginUser.setEmail(registerUser.getEmail());
        // expected
        loginSuccess = userApiService
                .loginUser(loginUser)
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
        loginUser.setEmail(registerUser.getEmail());
        loginUser.setPassword(registerUser.getPassword() + "test");
        // expected
        loginSuccess = userApiService
                .loginUser(loginUser)
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
                .loginUser(loginUser)
                .shouldHave(statusCode(401))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("email or password are incorrect")))
                .asPojo(LoginSuccess.class);
    }
}
