package com.petstore.api;

import io.restassured.response.Response;
import com.petstore.models.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Store API endpoints handler
 */
public class StoreApi extends ApiClient {
    private static final Logger logger = LoggerFactory.getLogger(StoreApi.class);
    private static final String STORE_ENDPOINT = "/store";

    /**
     * Place an order
     */
    public Response placeOrder(Order order) {
        logger.info("Placing order for pet id: {}", order.getPetId());
        return post(STORE_ENDPOINT + "/order", order);
    }

    /**
     * Get order by ID
     */
    public Response getOrderById(Long orderId) {
        logger.info("Getting order by id: {}", orderId);
        resetRequestSpec();
        return get(STORE_ENDPOINT + "/order/" + orderId);
    }

    /**
     * Delete an order
     */
    public Response deleteOrder(Long orderId) {
        logger.info("Deleting order with id: {}", orderId);
        return delete(STORE_ENDPOINT + "/order/" + orderId);
    }

    /**
     * Get store inventory
     */
    public Response getInventory() {
        logger.info("Getting store inventory");
        resetRequestSpec();
        return get(STORE_ENDPOINT + "/inventory");
    }
}
