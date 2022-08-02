package com.example.paymybuddy.model.application;

import org.springframework.stereotype.Component;

/**
 * Class to receive Transfer page form data.
 */
@Component
public class AddTransferForm {
    private int userId;
    private String connection;
    private String description;
    private float amount;

    public String getConnection() {
        return connection;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
