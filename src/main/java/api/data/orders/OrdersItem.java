package api.data.orders;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrdersItem{

	@JsonProperty("number")
	private int number;

	@JsonProperty("createdAt")
	private String createdAt;

	@JsonProperty("api/data/ingredients")
	private List<String> ingredients;

	@JsonProperty("_id")
	private String id;

	@JsonProperty("status")
	private String status;

	@JsonProperty("updatedAt")
	private String updatedAt;
}