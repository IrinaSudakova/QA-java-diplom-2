package api.deleteUser;

import api.data.register.RegisterSuccess;
import api.data.register.RegisterUser;
import api.data.users.UsersFactory;
import api.services.UserApiService;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static api.conditions.Conditions.bodyField;
import static api.conditions.Conditions.statusCode;
import static api.data.users.AccessToken.regexAccessToken;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

public class TestSuiteDeleteUserSuccessfully {

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

    @Feature("delete user")
    @Test
    @DisplayName("Can delete for valid user")
    public void testCanDeleteForValidUser() {
        // expected
        userApiService
                .deleteUser(registerSuccess.getAccessToken())
                .shouldHave(statusCode(202))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("message", containsString("User successfully removed")));
    }

    @Feature("delete user")
    @Test
    @DisplayName("Can delete for valid user and response has correct status code")
    public void testCanDeleteForValidUserWithCorrectStatusCode() {
        // expected
        userApiService
                .deleteUser(registerSuccess.getAccessToken())
                .shouldHave(statusCode(202));
    }

    @Feature("delete user")
    @Test
    @DisplayName("Can delete for valid user and body field 'success' is true")
    public void testCanDeleteForValidUserWithCorrectBodyFieldSuccess() {
        // expected
        userApiService
                .deleteUser(registerSuccess.getAccessToken())
                .shouldHave(bodyField("success", is(true)));
    }

    @Feature("delete user")
    @Test
    @DisplayName("Can delete for valid user and body field has correct message")
    public void testCanDeleteForValidUserWithCorrectBodyFieldMessage() {
        // expected
        userApiService
                .deleteUser(registerSuccess.getAccessToken())
                .shouldHave(bodyField("message", containsString("User successfully removed")));
    }
}
