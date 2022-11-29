package api.assertions;

import api.ProjectConfig;
import api.conditions.Condition;
import io.qameta.allure.Step;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;

@RequiredArgsConstructor
@Slf4j
public class AssertableResponse {
    private final Response response;

    @Step("then api response should have {condition}")
    public AssertableResponse shouldHave(Condition condition) {
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        if (config.logging()) {
            log.info("Check condition {}", condition);
        }
        condition.check(response);
        return this;
    }

    public <T> T asPojo(Class<T> tClass) {
        return response.as(tClass);
    }

    public Headers headers() {
        return response.getHeaders();
    }

    public int extractId() {
        return response.path("id");
    }
}
