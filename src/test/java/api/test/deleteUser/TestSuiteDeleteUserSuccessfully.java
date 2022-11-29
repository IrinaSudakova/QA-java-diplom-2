package api.test.deleteUser;

import api.data.register.RegisterCredentials;
import api.data.register.RegisteredUser;
import api.data.users.AccessToken;
import api.data.users.UsersFactory;
import api.services.BaseUserApiMethod;
import api.services.UserApiService;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static api.conditions.Conditions.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class TestSuiteDeleteUserSuccessfully {

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
    }

    @Feature("delete user")
    @Test
    @DisplayName("Can delete for valid user")
    public void testCanDeleteForValidUser() {
        // given
        registerCredentials = UsersFactory.getRandomUser();
        // register new user
        registeredUser = baseUserApiMethod.registerUserWithCurrent(registerCredentials);
        accessToken.setAccessToken(registeredUser.getAccessToken());
        // expected
        userApiService
                .deleteUser(accessToken)
                .shouldHave(statusCode(STATUS_CODE_202))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("message", containsString(MESSAGE_SUCCESSFULLY_REMOVED)));
    }
}
