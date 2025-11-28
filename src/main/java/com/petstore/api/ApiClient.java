package com.petstore.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.petstore.config.ConfigManager;

/**
 * Base API client providing common REST operations and specifications
 */
public class ApiClient {
    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);
    protected RequestSpecification requestSpec;

    public ApiClient() {
        setupRequestSpecification();
    }

    private void setupRequestSpecification() {
        ConfigManager config = ConfigManager.getInstance();
        RestAssured.baseURI = config.getBaseUrl();
        
        requestSpec = RestAssured
                .given()
                .contentType("application/json")
                .accept("application/json")
                .baseUri(config.getBaseUrl())
                .log().all();
    }

    /**
     * Performs a GET request
     */
    public Response get(String endpoint) {
        logger.info("Sending GET request to: {}", endpoint);
        return requestSpec.when().get(endpoint);
    }

    /**
     * Performs a GET request with query parameters
     */
    public Response get(String endpoint, java.util.Map<String, ?> queryParams) {
        logger.info("Sending GET request to: {} with params: {}", endpoint, queryParams);
        return requestSpec
                .queryParams(queryParams)
                .when()
                .get(endpoint);
    }

    /**
     * Performs a POST request
     */
    public Response post(String endpoint, Object body) {
        logger.info("Sending POST request to: {} with body: {}", endpoint, body);
        return requestSpec
                .body(body)
                .when()
                .post(endpoint);
    }

    /**
     * Performs a PUT request
     */
    public Response put(String endpoint, Object body) {
        logger.info("Sending PUT request to: {} with body: {}", endpoint, body);
        return requestSpec
                .body(body)
                .when()
                .put(endpoint);
    }

    /**
     * Performs a DELETE request
     */
    public Response delete(String endpoint) {
        logger.info("Sending DELETE request to: {}", endpoint);
        return requestSpec.when().delete(endpoint);
    }

    /**
     * Resets the request specification for the next request
     */
    protected void resetRequestSpec() {
        setupRequestSpecification();
    }
}
