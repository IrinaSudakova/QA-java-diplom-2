package api.patchUser;

import api.data.login.LoginSuccess;
import api.data.login.LoginCredentions;
import api.data.login.User;
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

public class TestSuiteCanNotPatchUser {
    private RegisterCredentials registerCredentials;
    private UserApiService userApiService;
    private RegisteredUser registeredUser;
    private LoginCredentions loginCredentions;
    private LoginSuccess loginSuccess;
    private AccessToken accessToken;
    private User user;
    private UserService userService;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        userService = new UserService();
        accessToken = new AccessToken();
        loginCredentions = new LoginCredentions();
        user = new User();
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
                .shouldHave(statusCode(403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("invalid signature")));
    }

    @Feature("patch user")
    @Test
    @DisplayName("Can't patch with same email")
    public void testCanNotPatchInfoWithSameEmail() {
        // given
        // register newUser
        RegisterCredentials newRegisterCredentials = UsersFactory.getRandomUser();
        RegisteredUser registeredNewUser = userService.registerUser(userApiService, newRegisterCredentials);
        accessToken.setAccessToken(registeredNewUser.getAccessToken());
        User newUser = new User();
        newUser.setEmail(registerCredentials.getEmail());
        newUser.setName(newRegisterCredentials.getName());
        // expected
        userApiService
                .patchUser(accessToken, newUser)
                .shouldHave(statusCode(403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("User with such email already exists")));
        // delete newUser
        userApiService
                .deleteUser(accessToken)
                .shouldHave(statusCode(202))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("message", containsString("User successfully removed")));
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
                .shouldHave(statusCode(200))
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
        user.setEmail("test" + registerCredentials.getEmail());
        user.setName("Test" + registerCredentials.getName());
        // expected
        userApiService
                .patchUser(accessToken, user)
                .shouldHave(statusCode(401))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("You should be authorised")));
    }
}
