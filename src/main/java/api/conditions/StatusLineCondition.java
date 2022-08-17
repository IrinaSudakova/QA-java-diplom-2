package api.conditions;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusLineCondition implements Condition {
    private final String statusLine;

    @Override
    public void check(Response response) {
        response.then().assertThat().statusLine(statusLine);
    }

    @Override
    public String toString() {
        return "status code is " + statusLine;
    }
}

