package api.data.login;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessToken {
    @JsonProperty("authorization")
    private String authorization;
}
