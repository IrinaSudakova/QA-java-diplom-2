package api.services;

import api.data.login.LoginCredentions;
import api.data.login.LoginSuccess;
import api.data.register.RegisterCredentials;
import api.data.register.RegisteredUser;
import api.data.users.AccessToken;

import static api.conditions.Conditions.bodyField;
import static api.conditions.Conditions.statusCode;
import static api.data.users.AccessToken.regexAccessToken;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

public class UserService {

    public RegisteredUser registerUser(UserApiService userApiService, RegisterCredentials registerCredentials) {
        return userApiService
                .registerUser(registerCredentials)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("user.email", containsString(registerCredentials.getEmail())))
                .shouldHave(bodyField("user.name", containsString(registerCredentials.getName())))
                .shouldHave(bodyField("accessToken", matchesPattern(regexAccessToken)))
                .shouldHave(bodyField("refreshToken", notNullValue()))
                .asPojo(RegisteredUser.class);
    }

    public LoginSuccess loginUser(UserApiService userApiService, LoginCredentions loginCredentions, RegisterCredentials registerCredentials) {
        return userApiService
                .loginUser(loginCredentions)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("accessToken", matchesPattern(regexAccessToken)))
                .shouldHave(bodyField("refreshToken", notNullValue()))
                .shouldHave(bodyField("user.email", containsString(registerCredentials.getEmail())))
                .shouldHave(bodyField("user.name", containsString(registerCredentials.getName())))
                .asPojo(LoginSuccess.class);
    }

    public void deleteUser(UserApiService userApiService, AccessToken accessToken) {
        userApiService
                .deleteUser(accessToken)
                .shouldHave(statusCode(202))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("message", containsString("User successfully removed")));
    }

    public void setLoginCredentials(RegisterCredentials registerCredentials, LoginCredentions loginCredentions) {
        loginCredentions.setEmail(registerCredentials.getEmail());
        loginCredentions.setPassword(registerCredentials.getPassword());
    }
}
