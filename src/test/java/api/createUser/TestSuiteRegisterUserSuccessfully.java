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

import static api.conditions.Conditions.statusCode;
import static api.conditions.Conditions.bodyField;
import static api.data.users.AccessToken.regexAccessToken;
import static org.hamcrest.Matchers.*;

public class TestSuiteRegisterUserSuccessfully {
    private RegisterUser registerUser;
    private UserApiService userApiService;
    private RegisterSuccess registerSuccess;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
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
    @DisplayName("Can register as valid user")
    public void testCanRegisterAsValidUser() {
        // given
        registerUser = UsersFactory.getRandomUser();
        // expect
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

    @Feature("create user")
    @Test
    @DisplayName("Can register as valid user with correct status code")
    public void testCanRegisterAsValidUserWithCorrectStatusCode() {
        // given
        registerUser = UsersFactory.getRandomUser();
        // expect
        registerSuccess = userApiService
                .registerUser(registerUser)
                .shouldHave(statusCode(200))
                .asPojo(RegisterSuccess.class);
    }

    @Feature("create user")
    @Test
    @DisplayName("Can register as valid user and body field has 'success'")
    public void testCanRegisterAsValidUserWithCorrectBodyFieldSuccess() {
        // given
        registerUser = UsersFactory.getRandomUser();
        // expect
        registerSuccess = userApiService
                .registerUser(registerUser)
                .shouldHave(bodyField("success", is(true)))
                .asPojo(RegisterSuccess.class);
    }

    @Feature("create user")
    @Test
    @DisplayName("Can register as valid user and body field has user email")
    public void testCanRegisterAsValidUserWithCorrectBodyFieldUserEmail() {
        // given
        registerUser = UsersFactory.getRandomUser();
        // expect
        registerSuccess = userApiService
                .registerUser(registerUser)
                .shouldHave(bodyField("user.email", containsString(registerUser.getEmail())))
                .asPojo(RegisterSuccess.class);
    }

    @Feature("create user")
    @Test
    @DisplayName("Can register as valid user and body field has user name")
    public void testCanRegisterAsValidUserWithCorrectBodyFieldUserName() {
        // given
        registerUser = UsersFactory.getRandomUser();
        // expect
        registerSuccess = userApiService
                .registerUser(registerUser)
                .shouldHave(bodyField("user.name", containsString(registerUser.getName())))
                .asPojo(RegisterSuccess.class);
    }


    @Feature("create user")
    @Test
    @DisplayName("Can register as valid user and body field has accessToken")
    public void testCanRegisterAsValidUserWithCorrectBodyFieldAccessToken() {
        // given
        registerUser = UsersFactory.getRandomUser();
        // expect
        registerSuccess = userApiService
                .registerUser(registerUser)
                .shouldHave(bodyField("accessToken", matchesPattern(regexAccessToken)))
                .asPojo(RegisterSuccess.class);
    }

    @Feature("create user")
    @Test
    @DisplayName("Can register as valid user and body field has refreshToken")
    public void testCanRegisterAsValidUserWithCorrectBodyFieldRefreshToken() {
        // given
        registerUser = UsersFactory.getRandomUser();
        // expect
        registerSuccess = userApiService
                .registerUser(registerUser)
                .shouldHave(bodyField("refreshToken", notNullValue()))
                .asPojo(RegisterSuccess.class);
    }
}
