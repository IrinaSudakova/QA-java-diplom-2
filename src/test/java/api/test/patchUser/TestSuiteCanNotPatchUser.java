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

public class TestSuiteCanNotPatchUser {
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
    @DisplayName("Can't patch with incorrect accessToken")
    public void testCanNotPatchInfoWithIncorrectAccessToken() {
        // given
        accessToken.setAccessToken(registeredUser.getAccessToken().substring(0, registeredUser.getAccessToken().length() - 3));
        user.setEmail("Test" + registerCredentials.getEmail());
        user.setName("Test" + registerCredentials.getName());
        // expected
        userApiService
                .patchUser(accessToken, user)
                .shouldHave(statusCode(STATUS_CODE_403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString(MESSAGE_INVALID_SIGNATURE)));
    }

    @Feature("patch user")
    @Test
    @DisplayName("Can't patch with same email")
    public void testCanNotPatchInfoWithSameEmail() {
        // given
        // register newUser
        RegisterCredentials newRegisterCredentials = UsersFactory.getRandomUser();
        RegisteredUser registeredNewUser = baseUserMethod.registerUserWithCurrent(newRegisterCredentials);
        accessToken.setAccessToken(registeredNewUser.getAccessToken());
        User newUser = new User();
        newUser.setEmail(registerCredentials.getEmail());
        newUser.setName(newRegisterCredentials.getName());
        // expected
        userApiService
                .patchUser(accessToken, newUser)
                .shouldHave(statusCode(STATUS_CODE_403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString(MESSAGE_USER_ALREADY_EXISTS)));
        // delete newUser
        userApiService
                .deleteUser(accessToken)
                .shouldHave(statusCode(STATUS_CODE_202))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("message", containsString(MESSAGE_SUCCESSFULLY_REMOVED)));
    }

    @Feature("patch user")
    @Test
    @DisplayName("Can't patch with empty user")
    public void testCanNotPatchInfoWithEmptyUser() {
        // given
        accessToken.setAccessToken(registeredUser.getAccessToken());
        // expected
        userApiService
                .patchUser(accessToken, user)
                .shouldHave(statusCode(STATUS_CODE_200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("user.email", containsString(registerCredentials.getEmail())))
                .shouldHave(bodyField("user.name", containsString(registerCredentials.getName())));
    }

    @Feature("patch user")
    @Test
    @DisplayName("Can't patch with empty accessToken")
    public void testCanNotPatchInfoWithEmptyAccessToken() {
        // given
        accessToken.setAccessToken("");
        user.setEmail("api/test" + registerCredentials.getEmail());
        user.setName("Test" + registerCredentials.getName());
        // expected
        userApiService
                .patchUser(accessToken, user)
                .shouldHave(statusCode(STATUS_CODE_401))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString(MESSAGE_SHOULD_BE_AUTHORISED)));
    }
}
