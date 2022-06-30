package api.services;

import api.assertions.AssertableResponse;
import api.data.login.AccessToken;
import api.data.login.LoginUser;
import api.data.login.LogoutUser;
import api.data.login.User;
import api.data.register.RegisterUser;
import api.data.register.RequestToResetPassword;
import api.data.register.ResetPassword;
import io.qameta.allure.Step;

public class UserApiService extends ApiService {
    @Step
    public AssertableResponse registerUser(RegisterUser registerUser) {
        return new AssertableResponse(setUp()
                .body(registerUser)
                .when()
                .post(REGISTER));
    }

    @Step
    public AssertableResponse loginUser(LoginUser loginUser) {
        return new AssertableResponse(setUp()
                .body(loginUser)
                .when()
                .post(LOGIN));
    }

    @Step
    public AssertableResponse logoutUser(LogoutUser logoutUser) {
        return new AssertableResponse(setUp()
                .body(logoutUser)
                .when()
                .post(LOGOUT));
    }

    @Step
    public AssertableResponse getToken(LoginUser loginUser) {
        return new AssertableResponse(setUp()
                .body(loginUser)
                .when()
                .post(TOKEN));
    }

    @Step
    public AssertableResponse getUser(AccessToken accessToken) {
        return new AssertableResponse(setUp()
                .body(accessToken)
                .when()
                .get(USER));
    }

    @Step
    public AssertableResponse updateUser(AccessToken accessToken, User user) {
        return new AssertableResponse(setUp()
                .body(accessToken)
                .body(user)
                .when()
                .patch(USER));
    }

    @Step
    public AssertableResponse deleteUser(User user) {
        return new AssertableResponse(setUp()
                .body(user)
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
