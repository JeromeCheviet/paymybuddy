package com.example.paymybuddy.model.application;

import org.springframework.stereotype.Component;

/**
 * Class to receive contact page form data.
 */
@Component
public class AddConnectionForm {
    private int userConnectionId;

    public int getUserConnectionId() {
        return userConnectionId;
    }

    public void setUserConnectionId(int userConnectionId) {
        this.userConnectionId = userConnectionId;
    }
}
