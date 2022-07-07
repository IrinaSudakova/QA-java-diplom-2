package api.services;

import api.assertions.AssertableResponse;
import api.data.users.AccessToken;
import api.data.login.LoginCredentions;
import api.data.users.LogoutToken;
import api.data.login.User;
import api.data.register.RegisterCredentials;
import api.data.register.RequestToResetPassword;
import api.data.register.ResetPassword;
import io.qameta.allure.Step;

public class UserApiService extends ApiService {
    @Step
    public AssertableResponse registerUser(RegisterCredentials registerCredentials) {
        return new AssertableResponse(setUp()
                .body(registerCredentials)
                .when()
                .post(REGISTER));
    }

    @Step
    public AssertableResponse loginUser(LoginCredentions loginCredentions) {
        return new AssertableResponse(setUp()
                .body(loginCredentions)
                .when()
                .post(LOGIN));
    }

    @Step
    public AssertableResponse logoutUser(LogoutToken logoutToken) {
        return new AssertableResponse(setUp()
                .body(logoutToken)
                .when()
                .post(LOGOUT));
    }

    @Step
    public AssertableResponse getToken(LoginCredentions loginCredentions) {
        return new AssertableResponse(setUp()
                .body(loginCredentions)
                .when()
                .post(TOKEN));
    }

    @Step
    public AssertableResponse getUser(AccessToken accessToken) {
        return new AssertableResponse(setUp()
                .header("Authorization", accessToken.getAccessToken())
                .when()
                .get(USER));
    }

    @Step
    public AssertableResponse patchUser(AccessToken accessToken, User user) {
        return new AssertableResponse(setUp()
                .header("Authorization", accessToken.getAccessToken())
                .body(user)
                .when()
                .patch(USER));
    }

    @Step
    public AssertableResponse deleteUser(AccessToken accessToken) {
        return new AssertableResponse(setUp()
                .header("Authorization", accessToken.getAccessToken())
                .when()
                .delete(USER));
    }

    @Step
    public AssertableResponse requestToResetPassword(RequestToResetPassword requestToResetPassword) {
        return new AssertableResponse(setUp()
                .body(requestToResetPassword)
                .when()
                .post(PASSWORD_RESET));
    }

    @Step
    public AssertableResponse resetPassword(ResetPassword resetPassword) {
        return new AssertableResponse(setUp()
                .body(resetPassword)
                .when()
                .post(RESET));
    }
}
