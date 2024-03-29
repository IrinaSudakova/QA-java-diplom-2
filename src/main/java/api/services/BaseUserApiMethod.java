package api.services;

import api.data.login.LoginCredentials;
import api.data.login.LoginSuccess;
import api.data.register.RegisterCredentials;
import api.data.register.RegisteredUser;
import api.data.users.AccessToken;

import static api.conditions.Conditions.*;
import static api.data.users.AccessToken.regexAccessToken;
import static org.hamcrest.Matchers.*;

public class BaseUserApiMethod {

    public RegisteredUser registerUserWithCurrent(RegisterCredentials registerCredentials) {
        return new UserApiService()
                .registerUser(registerCredentials)
                .shouldHave(statusCode(STATUS_CODE_200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("user.email", containsString(registerCredentials.getEmail())))
                .shouldHave(bodyField("user.name", containsString(registerCredentials.getName())))
                .shouldHave(bodyField("accessToken", matchesPattern(regexAccessToken)))
                .shouldHave(bodyField("refreshToken", notNullValue()))
                .asPojo(RegisteredUser.class);
    }

    public LoginSuccess loginUserWithCurrent(LoginCredentials loginCredentials, RegisterCredentials registerCredentials) {
        return new UserApiService()
                .loginUser(loginCredentials)
                .shouldHave(statusCode(STATUS_CODE_200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("accessToken", matchesPattern(regexAccessToken)))
                .shouldHave(bodyField("refreshToken", notNullValue()))
                .shouldHave(bodyField("user.email", containsString(registerCredentials.getEmail())))
                .shouldHave(bodyField("user.name", containsString(registerCredentials.getName())))
                .asPojo(LoginSuccess.class);
    }

    public void deleteUserWithCurrent(AccessToken accessToken) {
        new UserApiService()
                .deleteUser(accessToken)
                .shouldHave(statusCode(STATUS_CODE_202))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("message", containsString(MESSAGE_SUCCESSFULLY_REMOVED)));
    }

    public void setCurrentLoginCredentials(RegisterCredentials registerCredentials, LoginCredentials loginCredentials) {
        loginCredentials.setEmail(registerCredentials.getEmail());
        loginCredentials.setPassword(registerCredentials.getPassword());
    }
}
