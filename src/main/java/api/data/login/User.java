package api.data.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User{

	@JsonProperty("name")
	private String name;

	@JsonProperty("email")
	private String email;
}
