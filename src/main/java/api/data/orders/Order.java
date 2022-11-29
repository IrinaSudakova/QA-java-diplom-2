package api.data.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Order{

	@JsonProperty("success")
	private boolean success;

	@JsonProperty("name")
	private String name;

	@JsonProperty("order")
	private Order order;

	@JsonProperty("number")
	private int number;
}
