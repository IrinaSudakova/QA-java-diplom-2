package api.data.ingredients;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@Setter
@Getter
public class Ingredients {

	@JsonProperty("api/data")
	private List<DataItem> data;

	@JsonProperty("success")
	private boolean success;

}
