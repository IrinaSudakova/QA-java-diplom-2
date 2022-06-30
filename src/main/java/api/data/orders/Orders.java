package api.data.orders;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Orders {

	@JsonProperty("total")
	private int total;

	@JsonProperty("success")
	private boolean success;

	@JsonProperty("api/data/orders")
	private List<OrdersItem> orders;

	@JsonProperty("totalToday")
	private int totalToday;
}