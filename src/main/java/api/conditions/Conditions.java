package api.conditions;

import api.common.RequiredFields;
import lombok.experimental.UtilityClass;
import org.hamcrest.Matcher;

@UtilityClass
public class Conditions extends RequiredFields {
    public StatusCodeCondition statusCode(int code) {
        return new StatusCodeCondition(code);
    }

    public BodyFieldCondition bodyField(String jsonPath, Matcher matcher) {
        return new BodyFieldCondition(jsonPath, matcher);
    }

    public HeaderCondition header(String header, Matcher matcher) {
        return new HeaderCondition(header, matcher);
    }

    public StatusLineCondition statusLine(String statusLine) {
        return new StatusLineCondition(statusLine);
    }
}
