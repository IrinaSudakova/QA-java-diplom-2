package api.logoutUser;

import api.data.login.LoginSuccess;
import api.data.login.LoginUser;
import api.data.login.LogoutUser;
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

public class TestSuiteLogoutUserSuccessfully {
    private RegisterUser registerUser;
    private UserApiService userApiService;
    private RegisterSuccess registerSuccess;
    private LoginUser loginUser;
    private LoginSuccess loginSuccess;
    private LogoutUser logoutUser;

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

        logoutUser = new LogoutUser();
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

    @Feature("logout user")
    @Test
    @DisplayName("Can logout for valid user")
    public void testCanLogoutForValidUser() {
        // given
        loginSuccess = userApiService
                .loginUser(loginUser)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("accessToken", matchesPattern(regexAccessToken)))
                .shouldHave(bodyField("refreshToken", notNullValue()))
                .shouldHave(bodyField("user.email", containsString(registerUser.getEmail())))
                .shouldHave(bodyField("user.name", containsString(registerUser.getName())))
                .asPojo(LoginSuccess.class);
        logoutUser.setToken(loginSuccess.getRefreshToken());
        // expected
        userApiService
                .logoutUser(logoutUser)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("message", containsString("Successful logout")));
    }
}
