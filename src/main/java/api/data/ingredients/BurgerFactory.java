package api.data.ingredients;

import api.data.orders.OrderRequest;
import lombok.Builder;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

@Builder
public class BurgerFactory {
    public static OrderRequest getRandomBurgerWithAll(Ingredients ingredients) {
        List<DataItem> listBuns = ingredients.getData().stream()
                .filter(dataItem -> "bun".equals(dataItem.getType()))
                .collect(Collectors.toList());
        List<DataItem> listMain = ingredients.getData().stream()
                .filter(dataItem -> "main".equals(dataItem.getType()))
                .collect(Collectors.toList());
        List<DataItem> listSauce = ingredients.getData().stream()
                .filter(dataItem -> "sauce".equals(dataItem.getType()))
                .collect(Collectors.toList());

        return OrderRequest.builder()
                .ingredients(List.of(
                        listBuns.get(new Random().nextInt(listBuns.size()))
                                .get_id()
                                .toLowerCase(Locale.ROOT),
                        listMain.get(new Random().nextInt(listMain.size()))
                                .get_id()
                                .toLowerCase(Locale.ROOT),
                        listSauce.get(new Random().nextInt(listSauce.size()))
                                .get_id()
                                .toLowerCase(Locale.ROOT)))
                .build();
    }

    public static OrderRequest getRandomBurgerWithOnlyBuns(Ingredients ingredients) {
        List<DataItem> listBuns = ingredients.getData().stream()
                .filter(dataItem -> "bun".equals(dataItem.getType()))
                .collect(Collectors.toList());

        return OrderRequest.builder()
                .ingredients(List.of(
                        listBuns.get(new Random().nextInt(listBuns.size())).get_id().toLowerCase(Locale.ROOT)))
                .build();
    }

    public static OrderRequest getInvalidBurgerWith(Ingredients ingredients) {
        List<DataItem> listBuns = ingredients.getData().stream()
                .filter(dataItem -> "bun".equals(dataItem.getType()))
                .collect(Collectors.toList());
        List<DataItem> listMain = ingredients.getData().stream()
                .filter(dataItem -> "main".equals(dataItem.getType()))
                .collect(Collectors.toList());

        return OrderRequest.builder()
                .ingredients(List.of(
                        listBuns.get(new Random().nextInt(listBuns.size())).get_id().toLowerCase(Locale.ROOT),
                        listMain.get(new Random().nextInt(listMain.size())).get_id().toLowerCase(Locale.ROOT) + "test"))
                .build();
    }

    public static OrderRequest getInvalidBurgerWithoutIngredients() {
        return OrderRequest.builder()
                .build();
    }
}
