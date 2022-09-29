package api.createUser;

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
import static api.data.users.AccessToken.regexAccessToken;
import static org.hamcrest.Matchers.*;

public class TestSuiteUserSuccessfullyRegisterUser {
    private RegisterCredentials registerCredentials;
    private UserApiService userApiService;
    private RegisteredUser registeredUser;
    private AccessToken accessToken;
    private BaseUserMethod baseUserMethod;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        accessToken = new AccessToken();
        baseUserMethod = new BaseUserMethod();
    }

    @After
    public void tearDown() {
        // delete User
        accessToken.setAccessToken(registeredUser.getAccessToken());
        baseUserMethod.deleteUserWithCurrent(accessToken);
    }

    @Feature("create user")
    @Test
    @DisplayName("Can register as valid user")
    public void testCanRegisterAsValidUser() {
        // given
        registerCredentials = UsersFactory.getRandomUser();
        // expected
        registeredUser = userApiService
                .registerUser(registerCredentials)
                .shouldHave(statusCode(STATUS_CODE_200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("user.email", containsString(registerCredentials.getEmail())))
                .shouldHave(bodyField("user.name", containsString(registerCredentials.getName())))
                .shouldHave(bodyField("accessToken", matchesPattern(regexAccessToken)))
                .shouldHave(bodyField("refreshToken", notNullValue()))
                .asPojo(RegisteredUser.class);
    }
}
