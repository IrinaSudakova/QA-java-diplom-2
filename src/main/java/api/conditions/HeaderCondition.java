package api.conditions;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matcher;

@RequiredArgsConstructor
public class HeaderCondition implements Condition {
    private final String header;
    private final Matcher matcher;

    @Override
    public void check(Response response) {
        response.then().assertThat().header(header, matcher);
    }

    @Override
    public String toString() {
        return "header [" + header + "] " + matcher;
    }
}
