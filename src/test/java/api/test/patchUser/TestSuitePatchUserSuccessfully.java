package api.test.patchUser;

import api.data.login.User;
import api.data.register.RegisterCredentials;
import api.data.register.RegisteredUser;
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
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class TestSuitePatchUserSuccessfully {
    private RegisterCredentials registerCredentials;
    private UserApiService userApiService;
    private RegisteredUser registeredUser;
    private AccessToken accessToken;
    private User user;
    private BaseUserMethod baseUserMethod;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        baseUserMethod = new BaseUserMethod();
        accessToken = new AccessToken();
        user = new User();
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

    @Feature("patch user")
    @Test
    @DisplayName("Can patch email for valid user")
    public void testCanPatchEmailForValidUser() {
        // given
        accessToken.setAccessToken(registeredUser.getAccessToken());
        user.setEmail("api/test" + registerCredentials.getEmail());
        user.setName(registerCredentials.getName());
        // expected
        userApiService
                .patchUser(accessToken, user)
                .shouldHave(statusCode(STATUS_CODE_200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("user.email", containsString("api/test" + registerCredentials.getEmail())))
                .shouldHave(bodyField("user.name", containsString(registerCredentials.getName())));
    }

    @Feature("patch user")
    @Test
    @DisplayName("Can patch name for valid user")
    public void testCanPatchNameForValidUser() {
        // given
        accessToken.setAccessToken(registeredUser.getAccessToken());
        user.setEmail(registerCredentials.getEmail());
        user.setName("Test" + registerCredentials.getName());
        // expected
        userApiService
                .patchUser(accessToken, user)
                .shouldHave(statusCode(STATUS_CODE_200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("user.email", containsString(registerCredentials.getEmail())))
                .shouldHave(bodyField("user.name", containsString("Test" + registerCredentials.getName())));
    }

    @Feature("patch user")
    @Test
    @DisplayName("Can patch name and email for valid user")
    public void testCanPatchNameAndEmailForValidUser() {
        // given
        accessToken.setAccessToken(registeredUser.getAccessToken());
        user.setEmail("api/test" + registerCredentials.getEmail());
        user.setName("Test" + registerCredentials.getName());
        // expected
        userApiService
                .patchUser(accessToken, user)
                .shouldHave(statusCode(STATUS_CODE_200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("user.email", containsString("api/test" + registerCredentials.getEmail())))
                .shouldHave(bodyField("user.name", containsString("Test" + registerCredentials.getName())));
    }
}
