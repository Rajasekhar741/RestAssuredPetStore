package com.petstore.tests;

import io.restassured.response.Response;
import org.testng.annotations.*;
import com.petstore.api.UserApi;
import com.petstore.models.User;
import com.petstore.utils.AssertionHelper;
import com.petstore.utils.TestDataBuilder;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

/**
 * Test cases for User API endpoints
 */
@Feature("Pet Store")
@Story("User Management")
public class UserApiTests {
    private UserApi userApi;
    private String testUsername;

    @BeforeClass
    public void setup() {
        userApi = new UserApi();
        testUsername = TestDataBuilder.generateUsername();
    }

    @Test(description = "Create a new user")
    @Description("Test creating a new user account")
    public void testCreateUser() {
        // Arrange
        User user = new User();
        user.setUsername(testUsername);
        user.setEmail(TestDataBuilder.generateEmail());
        user.setPassword(TestDataBuilder.generatePassword());
        user.setFirstName("John");
        user.setLastName("Doe");

        // Act
        Response response = userApi.createUser(user);

        // Assert
        AssertionHelper.assertStatusCode(response, 200);
    }

    @Test(description = "Get user by username")
    @Description("Test retrieving user details by username")
    public void testGetUserByUsername() {
        // Arrange
        String username = TestDataBuilder.generateUsername();
        User user = new User();
        user.setUsername(username);
        user.setEmail(TestDataBuilder.generateEmail());
        user.setPassword(TestDataBuilder.generatePassword());
        
        userApi.createUser(user);

        // Act
        Response response = userApi.getUserByUsername(username);

        // Assert
        AssertionHelper.assertStatusCode(response, 200);
        AssertionHelper.assertJsonPath(response, "username", username);
    }

    @Test(description = "Update user")
    @Description("Test updating user information")
    public void testUpdateUser() {
        // Arrange
        String username = TestDataBuilder.generateUsername();
        User user = new User();
        user.setUsername(username);
        user.setEmail(TestDataBuilder.generateEmail());
        user.setPassword(TestDataBuilder.generatePassword());
        user.setFirstName("Jane");
        user.setLastName("Smith");
        
        userApi.createUser(user);

        user.setFirstName("Janet");
        user.setLastName("Updated");

        // Act
        Response response = userApi.updateUser(username, user);

        // Assert
        AssertionHelper.assertStatusCode(response, 200);
    }

    @Test(description = "Delete user")
    @Description("Test deleting a user account")
    public void testDeleteUser() {
        // Arrange
        String username = TestDataBuilder.generateUsername();
        User user = new User();
        user.setUsername(username);
        user.setEmail(TestDataBuilder.generateEmail());
        user.setPassword(TestDataBuilder.generatePassword());
        
        userApi.createUser(user);

        // Act
        Response response = userApi.deleteUser(username);

        // Assert
        AssertionHelper.assertStatusCode(response, 200);
    }

    @Test(description = "User login")
    @Description("Test user login functionality")
    public void testLoginUser() {
        // Arrange
        String username = TestDataBuilder.generateUsername();
        String password = TestDataBuilder.generatePassword();
        
        User user = new User();
        user.setUsername(username);
        user.setEmail(TestDataBuilder.generateEmail());
        user.setPassword(password);
        
        userApi.createUser(user);

        // Act
        Response response = userApi.loginUser(username, password);

        // Assert
        AssertionHelper.assertStatusCode(response, 200);
        AssertionHelper.assertResponseContainsKey(response, "message");
    }

    @Test(description = "User logout")
    @Description("Test user logout functionality")
    public void testLogoutUser() {
        // Act
        Response response = userApi.logoutUser();

        // Assert
        AssertionHelper.assertStatusCode(response, 200);
    }

    @Test(description = "Login with invalid credentials")
    @Description("Test error handling for invalid login credentials")
    public void testLoginWithInvalidCredentials() {
        // Act
        Response response = userApi.loginUser("invalidUser", "invalidPass");

        // Assert
        // Should return 200 but with error message for this API
        AssertionHelper.assertResponseSuccess(response);
    }
}
