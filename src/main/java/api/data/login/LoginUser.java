package api.data.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginUser {

	@JsonProperty("password")
	private String password;

	@JsonProperty("email")
	private String email;
}
