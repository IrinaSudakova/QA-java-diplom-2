package api.data.orders;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class OrderRequest{

	@JsonProperty("ingredients")
	private List<String> ingredients;
}