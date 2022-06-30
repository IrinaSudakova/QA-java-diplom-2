package api.data.ingredients;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

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
	private int V;

	@JsonProperty("name")
	private String name;

	@JsonProperty("fat")
	private int fat;

	@JsonProperty("_id")
	private String id;

	@JsonProperty("calories")
	private int calories;

	@JsonProperty("type")
	private String type;

	@JsonProperty("image_mobile")
	private String imageMobile;

	@JsonProperty("image_large")
	private String imageLarge;

}
