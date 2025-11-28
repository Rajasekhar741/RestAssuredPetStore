package com.petstore.api;

import io.restassured.response.Response;
import com.petstore.models.Pet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Pet API endpoints handler
 */
public class PetApi extends ApiClient {
    private static final Logger logger = LoggerFactory.getLogger(PetApi.class);
    private static final String PET_ENDPOINT = "/pet";
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Add a new pet to the store
     */
    public Response addPet(Pet pet) {
        logger.info("Adding new pet: {}", pet);
        return post(PET_ENDPOINT, pet);
    }

    /**
     * Update an existing pet
     */
    public Response updatePet(Pet pet) {
        logger.info("Updating pet with id: {}", pet.getId());
        return put(PET_ENDPOINT, pet);
    }

    /**
     * Get pet by ID
     */
    public Response getPetById(Long petId) {
        logger.info("Getting pet by id: {}", petId);
        resetRequestSpec();
        return get(PET_ENDPOINT + "/" + petId);
    }

    /**
     * Find pets by status
     */
    public Response findPetsByStatus(String status) {
        logger.info("Finding pets by status: {}", status);
        resetRequestSpec();
        return get(PET_ENDPOINT + "/findByStatus?status=" + status);
    }

    /**
     * Delete a pet by ID
     */
    public Response deletePet(Long petId) {
        logger.info("Deleting pet with id: {}", petId);
        return delete(PET_ENDPOINT + "/" + petId);
    }

    /**
     * Upload an image for a pet
     */
    public Response uploadPetImage(Long petId, String imagePath) {
        logger.info("Uploading image for pet id: {} from path: {}", petId, imagePath);
        resetRequestSpec();
        return requestSpec
                .multiPart("file", new java.io.File(imagePath))
                .when()
                .post(PET_ENDPOINT + "/" + petId + "/uploadImage");
    }
}
