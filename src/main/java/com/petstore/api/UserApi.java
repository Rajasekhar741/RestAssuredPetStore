package com.petstore.api;

import io.restassured.response.Response;
import com.petstore.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User API endpoints handler
 */
public class UserApi extends ApiClient {
    private static final Logger logger = LoggerFactory.getLogger(UserApi.class);
    private static final String USER_ENDPOINT = "/user";

    /**
     * Create a new user
     */
    public Response createUser(User user) {
        logger.info("Creating new user: {}", user.getUsername());
        return post(USER_ENDPOINT, user);
    }

    /**
     * Get user by username
     */
    public Response getUserByUsername(String username) {
        logger.info("Getting user by username: {}", username);
        resetRequestSpec();
        return get(USER_ENDPOINT + "/" + username);
    }

    /**
     * Update user
     */
    public Response updateUser(String username, User user) {
        logger.info("Updating user: {}", username);
        return put(USER_ENDPOINT + "/" + username, user);
    }

    /**
     * Delete user
     */
    public Response deleteUser(String username) {
        logger.info("Deleting user: {}", username);
        return delete(USER_ENDPOINT + "/" + username);
    }

    /**
     * User login
     */
    public Response loginUser(String username, String password) {
        logger.info("Logging in user: {}", username);
        resetRequestSpec();
        java.util.Map<String, String> params = new java.util.HashMap<>();
        params.put("username", username);
        params.put("password", password);
        return get(USER_ENDPOINT + "/login", params);
    }

    /**
     * User logout
     */
    public Response logoutUser() {
        logger.info("Logging out user");
        resetRequestSpec();
        return get(USER_ENDPOINT + "/logout");
    }
}
