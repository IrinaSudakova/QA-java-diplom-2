package api.deleteUser;

import api.data.register.RegisteredUser;
import api.data.register.RegisterCredentials;
import api.data.users.AccessToken;
import api.data.users.UsersFactory;
import api.services.UserApiService;
import api.services.BaseUserMethod;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static api.conditions.Conditions.*;
import static org.hamcrest.Matchers.*;

public class TestSuiteDeleteUserSuccessfully {

    private RegisterCredentials registerCredentials;
    private UserApiService userApiService;
    private RegisteredUser registeredUser;
    private AccessToken accessToken;
    private BaseUserMethod baseUserMethod;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        baseUserMethod = new BaseUserMethod();
        accessToken = new AccessToken();
    }

    @Feature("delete user")
    @Test
    @DisplayName("Can delete for valid user")
    public void testCanDeleteForValidUser() {
        // given
        registerCredentials = UsersFactory.getRandomUser();
        // register new user
        registeredUser = baseUserMethod.registerUserWithCurrent(registerCredentials);
        accessToken.setAccessToken(registeredUser.getAccessToken());
        // expected
        userApiService
                .deleteUser(accessToken)
                .shouldHave(statusCode(STATUS_CODE_202))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("message", containsString(MESSAGE_SUCCESSFULLY_REMOVED)));
    }
}
