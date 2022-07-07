package api.patchUser;

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

public class TestSuitePatchUserSuccessfully {
    private RegisterCredentials registerCredentials;
    private UserApiService userApiService;
    private RegisteredUser registeredUser;
    private LoginCredentions loginCredentions;
    private AccessToken accessToken;
    private User user;
    private UserService userService;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        userService = new UserService();
        loginCredentions = new LoginCredentions();
        accessToken = new AccessToken();
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
    @DisplayName("Can patch email for valid user")
    public void testCanPatchEmailForValidUser() {
        // given
        accessToken.setAccessToken(registeredUser.getAccessToken());
        user.setEmail("test" + registerCredentials.getEmail());
        user.setName(registerCredentials.getName());
        // expected
        userApiService
                .patchUser(accessToken, user)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("user.email", containsString("test" + registerCredentials.getEmail())))
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
                .shouldHave(statusCode(200))
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
        user.setEmail("test" + registerCredentials.getEmail());
        user.setName("Test" + registerCredentials.getName());
        // expected
        userApiService
                .patchUser(accessToken, user)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("user.email", containsString("test" + registerCredentials.getEmail())))
                .shouldHave(bodyField("user.name", containsString("Test" + registerCredentials.getName())));
    }
}
