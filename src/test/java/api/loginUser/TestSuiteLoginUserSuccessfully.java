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
import static org.hamcrest.Matchers.notNullValue;

public class TestSuiteLoginUserSuccessfully {

    private RegisterUser registerUser;
    private UserApiService userApiService;
    private RegisterSuccess registerSuccess;
    private LoginUser loginUser;
    private LoginSuccess loginSuccess;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
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
        // set loginUser
        loginUser = new LoginUser();
        loginUser.setEmail(registerUser.getEmail());
        loginUser.setPassword(registerUser.getPassword());
    }

    @After
    public void tearDown() {
        // delete User
        userApiService
                .deleteUser(loginSuccess.getAccessToken())
                .shouldHave(statusCode(202))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("message", containsString("User successfully removed")));
    }

    @Feature("login user")
    @Test
    @DisplayName("Can login for valid user")
    public void testCanLoginForValidUser() {
        loginSuccess = userApiService
                .loginUser(loginUser)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("accessToken", matchesPattern(regexAccessToken)))
                .shouldHave(bodyField("refreshToken", notNullValue()))
                .shouldHave(bodyField("user.email", containsString(registerUser.getEmail())))
                .shouldHave(bodyField("user.name", containsString(registerUser.getName())))
                .asPojo(LoginSuccess.class);
    }

    @Feature("login user")
    @Test
    @DisplayName("Can login for valid user and response has correct status code")
    public void testCanLoginForValidUserWithCorrectStatusCode() {
        loginSuccess = userApiService
                .loginUser(loginUser)
                .shouldHave(statusCode(200))
                .asPojo(LoginSuccess.class);
    }

    @Feature("login user")
    @Test
    @DisplayName("Can login for valid user and body field 'success' is true")
    public void testCanLoginForValidUserWithCorrectBodyFieldSuccess() {
        loginSuccess = userApiService
                .loginUser(loginUser)
                .shouldHave(bodyField("success", is(true)))
                .asPojo(LoginSuccess.class);
    }

    @Feature("login user")
    @Test
    @DisplayName("Can login for valid user and body field has correct accessToken")
    public void testCanLoginForValidUserWithCorrectBodyFieldAccessToken() {
        loginSuccess = userApiService
                .loginUser(loginUser)
                .shouldHave(bodyField("accessToken", matchesPattern(regexAccessToken)))
                .asPojo(LoginSuccess.class);
    }

    @Feature("login user")
    @Test
    @DisplayName("Can login for valid user and body field has correct refreshToken")
    public void testCanLoginForValidUserWithCorrectBodyFieldRefreshToken() {
        loginSuccess = userApiService
                .loginUser(loginUser)
                .shouldHave(bodyField("refreshToken", notNullValue()))
                .asPojo(LoginSuccess.class);
    }

    @Feature("login user")
    @Test
    @DisplayName("Can login for valid user and body field has correct user email")
    public void testCanLoginForValidUserWithCorrectBodyFieldUserEmail() {
        loginSuccess = userApiService
                .loginUser(loginUser)
                .shouldHave(bodyField("user.email", containsString(registerUser.getEmail())))
                .asPojo(LoginSuccess.class);
    }

    @Feature("login user")
    @Test
    @DisplayName("Can login for valid user and body field has correct user name")
    public void testCanLoginForValidUserWithCorrectBodyFieldUserName() {
        loginSuccess = userApiService
                .loginUser(loginUser)
                .shouldHave(bodyField("user.name", containsString(registerUser.getName())))
                .asPojo(LoginSuccess.class);
    }
}
