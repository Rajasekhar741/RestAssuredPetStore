package com.petstore.utils;

import io.restassured.response.Response;
import static org.assertj.core.api.Assertions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for common assertions and response validations
 */
public class AssertionHelper {
    private static final Logger logger = LoggerFactory.getLogger(AssertionHelper.class);

    /**
     * Assert response status code
     */
    public static void assertStatusCode(Response response, int expectedCode) {
        logger.info("Asserting status code: {}", expectedCode);
        assertThat(response.statusCode())
                .as("Expected status code")
                .isEqualTo(expectedCode);
    }

    /**
     * Assert response status is 2xx (success)
     */
    public static void assertResponseSuccess(Response response) {
        int statusCode = response.statusCode();
        logger.info("Asserting successful response, status code: {}", statusCode);
        assertThat(statusCode).isBetween(200, 299);
    }

    /**
     * Assert response contains a specific key
     */
    public static void assertResponseContainsKey(Response response, String key) {
        logger.info("Asserting response contains key: {}", key);
        assertThat(response.jsonPath().get(key))
                .as("Response should contain key: " + key)
                .isNotNull();
    }

    /**
     * Assert response JSON path value
     */
    public static void assertJsonPath(Response response, String jsonPath, Object expectedValue) {
        logger.info("Asserting JSON path '{}' equals '{}'", jsonPath, expectedValue);
        Object actualValue = response.jsonPath().get(jsonPath);
        assertThat(actualValue)
                .as("JSON path: " + jsonPath)
                .isEqualTo(expectedValue);
    }

    /**
     * Assert response header exists
     */
    public static void assertHeaderExists(Response response, String headerName) {
        logger.info("Asserting header exists: {}", headerName);
        assertThat(response.getHeader(headerName))
                .as("Header should exist: " + headerName)
                .isNotNull();
    }

    /**
     * Assert response header value
     */
    public static void assertHeaderValue(Response response, String headerName, String expectedValue) {
        logger.info("Asserting header '{}' equals '{}'", headerName, expectedValue);
        assertThat(response.getHeader(headerName))
                .as("Header: " + headerName)
                .isEqualTo(expectedValue);
    }

    /**
     * Assert response body contains text
     */
    public static void assertResponseBodyContains(Response response, String text) {
        logger.info("Asserting response body contains: {}", text);
        assertThat(response.getBody().asString())
                .as("Response body")
                .contains(text);
    }

    /**
     * Assert response time is within acceptable limit
     */
    public static void assertResponseTime(Response response, long maxTimeMs) {
        long responseTime = response.getTime();
        logger.info("Asserting response time {} is less than {}ms", responseTime, maxTimeMs);
        assertThat(responseTime)
                .as("Response time should be less than " + maxTimeMs + "ms")
                .isLessThan(maxTimeMs);
    }
}
