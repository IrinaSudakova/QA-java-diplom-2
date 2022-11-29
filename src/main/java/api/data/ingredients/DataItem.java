package api.data.ingredients;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Builder
@Getter
@Setter
public class DataItem{

	@JsonProperty("carbohydrates")
	private int carbohydrates;

	@JsonProperty("image")
	private String image;

	@JsonProperty("proteins")
	private int proteins;

	@JsonProperty("price")
	private int price;

	@JsonProperty("__v")
	private int dataItem_v;

	@JsonProperty("name")
	private String name;

	@JsonProperty("fat")
	private int fat;

	@JsonProperty("_id")
	private String _id;

	@JsonProperty("calories")
	private int calories;

	@JsonProperty("type")
	private String type;

	@JsonProperty("image_mobile")
	private String image_mobile;

	@JsonProperty("image_large")
	private String image_large;

}
