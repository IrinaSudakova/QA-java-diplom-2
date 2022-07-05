package api.deleteUser;

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

public class TestSuiteCanNotDelete {

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

    @Feature("delete user")
    @Test
    @DisplayName("Can't delete for unvalid token")
    public void testCanNotDeleteForUnvalidToken() {
        // expected
        userApiService
                .deleteUser(registerSuccess.getAccessToken() + "test")
                .shouldHave(statusCode(403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("invalid signature")));
    }

    @Feature("delete user")
    @Test
    @DisplayName("Can't delete for unvalid token and body field has correct status code")
    public void testCanNotDeleteForUnvalidTokenWithCorrectStatusCode() {
        // expected
        userApiService
                .deleteUser(registerSuccess.getAccessToken() + "test")
                .shouldHave(statusCode(403));
    }

    @Feature("delete user")
    @Test
    @DisplayName("Can't delete for unvalid token and body field 'success' is false")
    public void testCanNotDeleteForUnvalidTokenWithCorrectSuccessField() {
        // expected
        userApiService
                .deleteUser(registerSuccess.getAccessToken() + "test")
                .shouldHave(bodyField("success", is(false)));
    }

    @Feature("delete user")
    @Test
    @DisplayName("Can't delete for unvalid token and body field has correct message")
    public void testCanNotDeleteForUnvalidTokenWithCorrectMessage() {
        // expected
        userApiService
                .deleteUser(registerSuccess.getAccessToken() + "test")
                .shouldHave(bodyField("message", containsString("invalid signature")));
    }

    @Feature("delete user")
    @Test
    @DisplayName("Can't delete for empty token")
    public void testCanNotDeleteForEmptyToken() {
        // expected
        userApiService
                .deleteUser("")
                .shouldHave(statusCode(401))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("You should be authorised")));
    }

    @Feature("delete user")
    @Test
    @DisplayName("Can't delete for empty token and body field has correct status code")
    public void testCanNotDeleteForEmptyTokenWithCorrectStatusCode() {
        // expected
        userApiService
                .deleteUser("")
                .shouldHave(statusCode(401));
    }

    @Feature("delete user")
    @Test
    @DisplayName("Can't delete for empty token and body field 'success' is false")
    public void testCanNotDeleteForEmptyTokenWithCorrectSuccessField() {
        // expected
        userApiService
                .deleteUser("")
                .shouldHave(bodyField("success", is(false)));
    }

    @Feature("delete user")
    @Test
    @DisplayName("Can't delete for empty token and body field has correct message")
    public void testCanNotDeleteForEmptyTokenWithCorrectMessage() {
        // expected
        userApiService
                .deleteUser("")
                .shouldHave(bodyField("message", containsString("You should be authorised")));
    }
}
