package api.createUser;

import api.data.register.RegisterUser;
import api.data.users.UsersFactory;
import api.services.UserApiService;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static api.conditions.Conditions.bodyField;
import static api.conditions.Conditions.statusCode;
import static org.hamcrest.Matchers.*;

public class TestSuiteCanNotRegister {

    private UserApiService userApiService;
    private RegisterUser registerUser;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register without name")
    public void testCanRegisterAsValidUserWithoutName() {
        // given
        registerUser = UsersFactory.getUserWithoutName();
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(statusCode(403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("Email, password and name are required fields")));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register without name and response has correct status code")
    public void testCanRegisterAsValidUserWithoutNameWithCorrectStatusCode() {
        // given
        registerUser = UsersFactory.getUserWithoutName();
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(statusCode(403));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register without name and body field 'success' is false")
    public void testCanRegisterAsValidUserWithoutNameWithCorrectBodyFieldSuccess() {
        // given
        registerUser = UsersFactory.getUserWithoutName();
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(bodyField("success", is(false)));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register without name and body field has correct message")
    public void testCanRegisterAsValidUserWithoutNameWithCorrectBodyFieldMessage() {
        // given
        registerUser = UsersFactory.getUserWithoutName();
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(bodyField("message", containsString("Email, password and name are required fields")));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register without password")
    public void testCanRegisterAsValidUserWithoutPassword() {
        // given
        registerUser = UsersFactory.getUserWithoutPassword();
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(statusCode(403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("Email, password and name are required fields")));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register without password and response has correct status code")
    public void testCanRegisterAsValidUserWithoutPasswordWithCorrectStatusCode() {
        // given
        registerUser = UsersFactory.getUserWithoutPassword();
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(statusCode(403));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register without password and body field 'success' is false")
    public void testCanRegisterAsValidUserWithoutPasswordWithCorrectBodyFieldSuccess() {
        // given
        registerUser = UsersFactory.getUserWithoutPassword();
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(bodyField("success", is(false)));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register without password and body field has correct message")
    public void testCanRegisterAsValidUserWithoutPasswordWithCorrectBodyFieldMessage() {
        // given
        registerUser = UsersFactory.getUserWithoutPassword();
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(bodyField("message", containsString("Email, password and name are required fields")));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register without email")
    public void testCanRegisterAsValidUserWithoutEmail() {
        // given
        registerUser = UsersFactory.getUserWithoutEmail();
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(statusCode(403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("Email, password and name are required fields")));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register without email and response has correct status code")
    public void testCanRegisterAsValidUserWithoutEmailWithCorrectStatusCode() {
        // given
        registerUser = UsersFactory.getUserWithoutEmail();
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(statusCode(403));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register without email and body field 'success' is false")
    public void testCanRegisterAsValidUserWithoutEmailWithCorrectBodyFieldSuccess() {
        // given
        registerUser = UsersFactory.getUserWithoutEmail();
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(bodyField("success", is(false)));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register without email and body field has correct message")
    public void testCanRegisterAsValidUserWithoutEmailWithCorrectBodyFieldMessage() {
        // given
        registerUser = UsersFactory.getUserWithoutEmail();
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(bodyField("message", containsString("Email, password and name are required fields")));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register empty user")
    public void testCanRegisterAsEmptyUser() {
        // given
        registerUser = UsersFactory.getEmptyUser();
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(statusCode(403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("Email, password and name are required fields")));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register empty user and response has correct status code")
    public void testCanRegisterAsEmptyUserWithCorrectStatusCode() {
        // given
        registerUser = UsersFactory.getEmptyUser();
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(statusCode(403));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register empty user and body field 'success' is false")
    public void testCanRegisterAsEmptyUserWithCorrectBodyFieldSuccess() {
        // given
        registerUser = UsersFactory.getEmptyUser();
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(bodyField("success", is(false)));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register empty user and body field has correct message")
    public void testCanRegisterAsEmptyUserWithCorrectBodyFieldMessage() {
        // given
        registerUser = UsersFactory.getEmptyUser();
        // expected
        userApiService
                .registerUser(registerUser)
                .shouldHave(bodyField("message", containsString("Email, password and name are required fields")));
    }
}
