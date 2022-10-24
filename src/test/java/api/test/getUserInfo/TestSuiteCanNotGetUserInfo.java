package api.test.getUserInfo;

import api.data.register.RegisterCredentials;
import api.data.register.RegisteredUser;
import api.data.users.AccessToken;
import api.data.users.UsersFactory;
import api.services.BaseUserApiMethod;
import api.services.UserApiService;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static api.conditions.Conditions.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;


public class TestSuiteCanNotGetUserInfo {
    private RegisterCredentials registerCredentials;
    private UserApiService userApiService;
    private RegisteredUser registeredUser;
    private AccessToken accessToken;
    private BaseUserApiMethod baseUserApiMethod;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        baseUserApiMethod = new BaseUserApiMethod();
        accessToken = new AccessToken();
        registerCredentials = UsersFactory.getRandomUser();
        registeredUser = baseUserApiMethod.registerUserWithCurrent(registerCredentials);
    }

    @After
    public void tearDown() {
        // delete User
        accessToken.setAccessToken(registeredUser.getAccessToken());
        baseUserApiMethod.deleteUserWithCurrent(accessToken);
    }

    @Feature("get user")
    @Test
    @DisplayName("Can't get info with incorrect access token")
    public void testCanNotGetInfoForUserWithIncorrectAccessToken() {
        // given
        accessToken.setAccessToken(registeredUser.getAccessToken() + "api/test");
        // expected
        userApiService
                .getUser(accessToken)
                .shouldHave(statusCode(STATUS_CODE_403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString(MESSAGE_INVALID_SIGNATURE)));
    }

    @Feature("get user")
    @Test
    @DisplayName("Can't get info with empty access token")
    public void testCanNotGetInfoForUserWithEmptyAccessToken() {
        // given
        accessToken.setAccessToken("");
        // expected
        userApiService
                .getUser(accessToken)
                .shouldHave(statusCode(STATUS_CODE_401))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString(MESSAGE_SHOULD_BE_AUTHORISED)));
    }
}
