package api.services;

import api.assertions.AssertableResponse;
import api.data.orders.OrderRequest;
import io.qameta.allure.Step;

public class OrdersApiService extends ApiService {
    @Step
    public AssertableResponse getAllOrders() {
        return new AssertableResponse(setUp()
                .get(ALL_ORDERS));
    }

    @Step
    public AssertableResponse getUserOrders() {
        return new AssertableResponse(setUp()
                .get(ORDERS));
    }

    @Step
    public AssertableResponse getIngredients() {
        return new AssertableResponse(setUp()
                .get(INGREDIENTS));
    }

    @Step
    public AssertableResponse createOrder(OrderRequest orderRequest) {
        return new AssertableResponse(setUp()
                .body(orderRequest)
                .when()
                .post(ORDERS));
    }
}
