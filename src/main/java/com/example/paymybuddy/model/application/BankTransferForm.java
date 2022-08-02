package com.example.paymybuddy.model.application;

import org.springframework.stereotype.Component;

/**
 * Class to receive Home page form data.
 */
@Component
public class BankTransferForm {

    private float amount;
    private String transferType;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }
}
