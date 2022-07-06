package api.createUser;

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

public class TestSuiteCanNotRegisterTwice {

    private RegisterUser registerUser;
    private UserApiService userApiService;
    private RegisterSuccess registerSuccess;

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
    }

    @After
    public void tearDown() {
        // delete User
        userApiService
                .deleteUser(registerSuccess.getAccessToken())
                .shouldHave(statusCode(202))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("message", containsString("User successfully removed")));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register twice")
    public void testCanNotRegisterTwice() {
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(statusCode(403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("User already exists")));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register twice and response has correct status code")
    public void testCanNotRegisterTwiceWithCorrectStatusCode() {
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(statusCode(403));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register twice and body field 'success' is false")
    public void testCanNotRegisterTwiceWithCorrectBodyFieldSuccess() {
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(bodyField("success", is(false)));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register twice and body field has correct message")
    public void testCanNotRegisterTwiceWithCorrectBodyFieldMessage() {
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(bodyField("message", containsString("User already exists")));
    }
}
