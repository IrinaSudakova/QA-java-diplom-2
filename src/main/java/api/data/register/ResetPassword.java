package api.data.register;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPassword {

	@JsonProperty("password")
	private String password;

	@JsonProperty("token")
	private String token;
}
