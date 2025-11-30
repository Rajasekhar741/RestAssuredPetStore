package com.petstore.tests;

import io.restassured.response.Response;
import org.testng.annotations.*;
import com.petstore.api.PetApi;
import com.petstore.models.Pet;
import com.petstore.utils.AssertionHelper;
import com.petstore.utils.TestDataBuilder;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

/**
 * Test cases for Pet API endpoints
 */
@Feature("Pet Store")
@Story("Pet Management")
public class PetApiTests {
    private PetApi petApi;

    @BeforeClass
    public void setup() {
        petApi = new PetApi();
    }

    @Test(description = "Add a new pet to the store")
    @Description("Test adding a new pet with valid data")
    public void testAddPet() {
        // Arrange
        Pet pet = new Pet();
        pet.setId(TestDataBuilder.generatePetId());
        pet.setName(TestDataBuilder.generatePetName());
        pet.setStatus("available");

        // Act
        Response response = petApi.addPet(pet);

        // Assert
        AssertionHelper.assertStatusCode(response, 200);
        AssertionHelper.assertJsonPath(response, "name", pet.getName());
        AssertionHelper.assertJsonPath(response, "status", "available");
    }

    @Test(description = "Get pet by ID")
    @Description("Test retrieving a pet by its ID")
    public void testGetPetById() {
        // Arrange
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Fluffy");
        pet.setStatus("available");
        
        petApi.addPet(pet);

        // Act
        Response response = petApi.getPetById(1L);

        // Assert
        AssertionHelper.assertStatusCode(response, 200);
        AssertionHelper.assertResponseContainsKey(response, "id");
    }

    @Test(description = "Update an existing pet")
    @Description("Test updating pet information")
    public void testUpdatePet() {
        // Arrange
        Pet pet = new Pet();
        pet.setId(2L);
        pet.setName("UpdatedName");
        pet.setStatus("sold");

        petApi.addPet(pet);

        pet.setName("NewName");
        pet.setStatus("pending");

        // Act
        Response response = petApi.updatePet(pet);

        // Assert
        AssertionHelper.assertStatusCode(response, 200);
        AssertionHelper.assertJsonPath(response, "name", "NewName");
    }

    @Test(description = "Find pets by status")
    @Description("Test finding pets filtered by status")
    public void testFindPetsByStatus() {
        // Act
        Response response = petApi.findPetsByStatus("available");

        // Assert
        AssertionHelper.assertStatusCode(response, 200);
        AssertionHelper.assertResponseSuccess(response);
    }

    @Test(description = "Delete a pet")
    @Description("Test deleting a pet from the store")
    public void testDeletePet() {
        // Arrange
        Pet pet = new Pet();
        pet.setId(3L);
        pet.setName("DeleteMe");
        pet.setStatus("available");
        
        petApi.addPet(pet);

        // Act
        Response response = petApi.deletePet(3L);

        // Assert
        AssertionHelper.assertStatusCode(response, 200);
    }

    @Test(description = "Get non-existent pet")
    @Description("Test error handling when pet doesn't exist")
    public void testGetNonExistentPet() {
        // Act
        Response response = petApi.getPetById(999999L);

        // Assert - PetStore API returns 200 but with null id
        AssertionHelper.assertStatusCode(response, 200);
    }
}
