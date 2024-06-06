package org.example.helpers;

import com.google.gson.annotations.Expose;

public class Order {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    String invalidOrderMessage = "Invalid Order";

    public String getInvalidOrderMessage() {
        return invalidOrderMessage;
    }

    public int getInvalidOrderCode() {
        return invalidOrderCode;
    }

    public String getInvalidIdSupplied() {
        return invalidIdSupplied;
    }

    public int getInvalidIdSuppliedCode() {
        return invalidIdSuppliedCode;
    }

    public String getOrderNotFoundMessage() {
        return orderNotFoundMessage;
    }

    public int getOrderNotFoundCode() {
        return orderNotFoundCode;
    }

    int invalidOrderCode = 400;
    String invalidIdSupplied = "Invalid ID supplied";
    int invalidIdSuppliedCode = 400;
    String orderNotFoundMessage = "Order Not Found";
    int orderNotFoundCode = 404;

    @Expose
    long id;
    @Expose
    long petId;
    @Expose
    int quantity;
    @Expose
    String shipDate;
    @Expose
    String status;
    @Expose
    boolean complete;
}
