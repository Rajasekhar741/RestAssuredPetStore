package com.petstore.utils;

import io.restassured.response.Response;
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
        int actual = response.statusCode();
        if (actual != expectedCode) {
            throw new AssertionError("Expected status code " + expectedCode + " but got " + actual);
        }
    }

    /**
     * Assert response status is 2xx (success)
     */
    public static void assertResponseSuccess(Response response) {
        int statusCode = response.statusCode();
        logger.info("Asserting successful response, status code: {}", statusCode);
        if (statusCode < 200 || statusCode > 299) {
            throw new AssertionError("Expected successful status code (2xx) but got " + statusCode);
        }
    }

    /**
     * Assert response contains a specific key
     */
    public static void assertResponseContainsKey(Response response, String key) {
        logger.info("Asserting response contains key: {}", key);
        Object value = response.jsonPath().get(key);
        if (value == null) {
            throw new AssertionError("Response should contain key: " + key);
        }
    }

    /**
     * Assert response JSON path value
     */
    public static void assertJsonPath(Response response, String jsonPath, Object expectedValue) {
        logger.info("Asserting JSON path '{}' equals '{}'", jsonPath, expectedValue);
        Object actualValue = response.jsonPath().get(jsonPath);
        if (!actualValue.equals(expectedValue)) {
            throw new AssertionError("JSON path '" + jsonPath + "': expected " + expectedValue + " but got " + actualValue);
        }
    }

    /**
     * Assert response header exists
     */
    public static void assertHeaderExists(Response response, String headerName) {
        logger.info("Asserting header exists: {}", headerName);
        String headerValue = response.getHeader(headerName);
        if (headerValue == null) {
            throw new AssertionError("Header should exist: " + headerName);
        }
    }

    /**
     * Assert response header value
     */
    public static void assertHeaderValue(Response response, String headerName, String expectedValue) {
        logger.info("Asserting header '{}' equals '{}'", headerName, expectedValue);
        String actualValue = response.getHeader(headerName);
        if (!actualValue.equals(expectedValue)) {
            throw new AssertionError("Header '" + headerName + "': expected " + expectedValue + " but got " + actualValue);
        }
    }

    /**
     * Assert response body contains text
     */
    public static void assertResponseBodyContains(Response response, String text) {
        logger.info("Asserting response body contains: {}", text);
        String body = response.getBody().asString();
        if (!body.contains(text)) {
            throw new AssertionError("Response body should contain: " + text);
        }
    }

    /**
     * Assert response time is within acceptable limit
     */
    public static void assertResponseTime(Response response, long maxTimeMs) {
        long responseTime = response.getTime();
        logger.info("Asserting response time {} is less than {}ms", responseTime, maxTimeMs);
        if (responseTime >= maxTimeMs) {
            throw new AssertionError("Response time should be less than " + maxTimeMs + "ms but got " + responseTime + "ms");
        }
    }
}
