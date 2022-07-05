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
        // expect
        userApiService
                .registerUser(registerUser)
                .shouldHave(statusCode(403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("Email, password and name are required fields")));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register without password")
    public void testCanRegisterAsValidUserWithoutPassword() {
        // given
        registerUser = UsersFactory.getUserWithoutPassword();
        // expect
        userApiService
                .registerUser(registerUser)
                .shouldHave(statusCode(403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("Email, password and name are required fields")));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register without email")
    public void testCanRegisterAsValidUserWithoutEmail() {
        // given
        registerUser = UsersFactory.getUserWithoutEmail();
        // expect
        userApiService
                .registerUser(registerUser)
                .shouldHave(statusCode(403))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString("Email, password and name are required fields")));
    }
}