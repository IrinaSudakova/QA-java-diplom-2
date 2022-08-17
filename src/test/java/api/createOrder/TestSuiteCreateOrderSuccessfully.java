package api.createOrder;

import api.data.ingredients.BurgerFactory;
import api.data.ingredients.Ingredients;
import api.services.OrdersApiService;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static api.conditions.Conditions.bodyField;
import static api.conditions.Conditions.statusCode;
import static org.hamcrest.Matchers.*;

public class TestSuiteCreateOrderSuccessfully {
    private OrdersApiService ordersApiService;

    @Before
    public void setUp() {
        ordersApiService = new OrdersApiService();
    }

    @Feature("create order")
    @Test
    @DisplayName("Can create order")
    public void testCanCreateOrder() {
        // given
        Ingredients ingredients = ordersApiService.getIngredients().asPojo(Ingredients.class);

        //expected
        ordersApiService
                .createOrder(BurgerFactory.getRandomBurgerWithAll(ingredients))
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("name", containsString("бургер")))
                .shouldHave(bodyField("order.number", notNullValue()));
    }
}
