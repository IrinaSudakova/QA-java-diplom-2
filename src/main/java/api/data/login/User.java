package api.data.login;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User{

	@JsonProperty("name")
	private String name;

	@JsonProperty("email")
	private String email;
}
