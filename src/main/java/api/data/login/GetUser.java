package api.data.login;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetUser{

	@JsonProperty("success")
	private boolean success;

	@JsonProperty("user")
	private User user;
}
