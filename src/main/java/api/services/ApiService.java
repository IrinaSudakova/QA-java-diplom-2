package api.services;

import api.ProjectConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ApiService {
    protected final String INGREDIENTS = "/ingredients";
    protected final String ORDERS = "/orders";
    protected final String ALL_ORDERS = ORDERS + "/all";
    protected final String PASSWORD_RESET = "/password-reset";
    protected final String RESET = PASSWORD_RESET + "/reset";
    protected final String AUTH = "/auth";
    protected final String REGISTER = AUTH + "/register";
    protected final String LOGIN = AUTH + "/login";
    protected final String LOGOUT = AUTH + "/logout";
    protected final String TOKEN = AUTH + "/token";
    protected final String USER = AUTH + "/user";

    protected RequestSpecification setUp() {
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        RestAssured.baseURI = config.baseUrl();
        return RestAssured
                .given().contentType(ContentType.JSON)
                .filters(getFilters());
    }

    private List<Filter> getFilters() {
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        if (config.logging()) {
            return Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured());
        }
        return Collections.singletonList(new AllureRestAssured());
    }

}
