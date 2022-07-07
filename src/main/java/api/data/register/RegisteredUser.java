package api.data.register;

import api.data.login.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisteredUser {

	@JsonProperty("success")
	private boolean success;

	@JsonProperty("accessToken")
	private String accessToken;

	@JsonProperty("user")
	private User user;

	@JsonProperty("refreshToken")
	private String refreshToken;
}
