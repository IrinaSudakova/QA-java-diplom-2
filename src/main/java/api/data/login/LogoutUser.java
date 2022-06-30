package api.data.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LogoutUser {

	@JsonProperty("token")
	private String token;
}
