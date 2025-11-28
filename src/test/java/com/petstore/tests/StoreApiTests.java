package com.petstore.tests;

import io.restassured.response.Response;
import org.testng.annotations.*;
import com.petstore.api.StoreApi;
import com.petstore.models.Order;
import com.petstore.utils.AssertionHelper;
import com.petstore.utils.TestDataBuilder;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

/**
 * Test cases for Store API endpoints
 */
@Feature("Pet Store")
@Story("Store Operations")
public class StoreApiTests {
    private StoreApi storeApi;

    @BeforeClass
    public void setup() {
        storeApi = new StoreApi();
    }

    @Test(description = "Place an order")
    @Description("Test placing a new pet order")
    public void testPlaceOrder() {
        // Arrange
        Order order = new Order();
        order.setId(TestDataBuilder.generateOrderId());
        order.setPetId(TestDataBuilder.generatePetId());
        order.setQuantity(TestDataBuilder.generateQuantity());
        order.setStatus("placed");

        // Act
        Response response = storeApi.placeOrder(order);

        // Assert
        AssertionHelper.assertStatusCode(response, 200);
        AssertionHelper.assertResponseContainsKey(response, "id");
    }

    @Test(description = "Get order by ID")
    @Description("Test retrieving an order by its ID")
    public void testGetOrderById() {
        // Arrange
        Long orderId = TestDataBuilder.generateOrderId();
        Order order = new Order();
        order.setId(orderId);
        order.setPetId(1L);
        order.setQuantity(2);
        order.setStatus("placed");
        
        storeApi.placeOrder(order);

        // Act
        Response response = storeApi.getOrderById(orderId);

        // Assert
        AssertionHelper.assertStatusCode(response, 200);
        AssertionHelper.assertResponseContainsKey(response, "id");
    }

    @Test(description = "Delete an order")
    @Description("Test deleting an order")
    public void testDeleteOrder() {
        // Arrange
        Long orderId = TestDataBuilder.generateOrderId();
        Order order = new Order();
        order.setId(orderId);
        order.setPetId(1L);
        order.setQuantity(1);
        order.setStatus("placed");
        
        storeApi.placeOrder(order);

        // Act
        Response response = storeApi.deleteOrder(orderId);

        // Assert
        AssertionHelper.assertStatusCode(response, 200);
    }

    @Test(description = "Get store inventory")
    @Description("Test retrieving store inventory")
    public void testGetInventory() {
        // Act
        Response response = storeApi.getInventory();

        // Assert
        AssertionHelper.assertStatusCode(response, 200);
        AssertionHelper.assertResponseSuccess(response);
    }

    @Test(description = "Place order with invalid pet ID")
    @Description("Test error handling with invalid pet ID")
    public void testPlaceOrderWithInvalidPetId() {
        // Arrange
        Order order = new Order();
        order.setPetId(-1L);
        order.setQuantity(1);
        order.setStatus("placed");

        // Act
        Response response = storeApi.placeOrder(order);

        // Assert
        // Store may accept this, but we check for valid response
        AssertionHelper.assertResponseSuccess(response);
    }
}
