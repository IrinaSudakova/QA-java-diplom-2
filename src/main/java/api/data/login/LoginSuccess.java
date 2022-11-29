package api.data.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginSuccess {

	@JsonProperty("success")
	private boolean success;

	@JsonProperty("accessToken")
	private String accessToken;

	@JsonProperty("user")
	private User user;

	@JsonProperty("refreshToken")
	private String refreshToken;
}
