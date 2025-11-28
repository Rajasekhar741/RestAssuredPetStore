package com.petstore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Order model representing a store order
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("petId")
    private Long petId;
    
    @JsonProperty("quantity")
    private Integer quantity;
    
    @JsonProperty("shipDate")
    private String shipDate;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("complete")
    private Boolean complete;

    public Order() {
    }

    public Order(Long petId, Integer quantity) {
        this.petId = petId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
}
