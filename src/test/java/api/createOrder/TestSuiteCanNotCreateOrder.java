package api.createOrder;

import api.data.ingredients.BurgerFactory;
import api.data.ingredients.Ingredients;
import api.services.OrdersApiService;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static api.conditions.Conditions.*;
import static org.hamcrest.Matchers.*;

public class TestSuiteCanNotCreateOrder {
    private OrdersApiService ordersApiService;

    @Before
    public void setUp() {
        ordersApiService = new OrdersApiService();
    }

    @Feature("create order")
    @Test
    @DisplayName("Can't create order with invalid ingredient")
    public void testCanNotCreateOrderWithInvalidIngredient() {
        // given
        Ingredients ingredients = ordersApiService.getIngredients().asPojo(Ingredients.class);

        //expected
        ordersApiService
                .createOrder(BurgerFactory.getInvalidBurgerWith(ingredients))
                .shouldHave(statusCode(STATUS_CODE_500))
                .shouldHave(statusLine(MESSAGE_INTERNAL_SERVER_ERROR));
    }

    @Feature("create order")
    @Test
    @DisplayName("Can't create order without ingredients")
    public void testCanNotCreateOrderWithoutIngredients() {
        //expected
        ordersApiService
                .createOrder(BurgerFactory.getInvalidBurgerWithoutIngredients())
                .shouldHave(statusCode(STATUS_CODE_400))
                .shouldHave(bodyField("success", is(false)))
                .shouldHave(bodyField("message", containsString(MESSAGE_INGREDIENT_MUST_BE_PROVIDED)));
    }
}
