package com.example.paymybuddy.model.application;

import org.springframework.stereotype.Component;

@Component
public class SignupForm {
    private String signupEmail;
    private String signupPassword;
    private String signupUserName;
    private String signupBankName;
    private String signupRib;

    public String getSignupEmail() {
        return signupEmail;
    }

    public void setSignupEmail(String signupEmail) {
        this.signupEmail = signupEmail;
    }

    public String getSignupPassword() {
        return signupPassword;
    }

    public void setSignupPassword(String signupPassword) {
        this.signupPassword = signupPassword;
    }

    public String getSignupUserName() {
        return signupUserName;
    }

    public void setSignupUserName(String signupUserName) {
        this.signupUserName = signupUserName;
    }

    public String getSignupBankName() {
        return signupBankName;
    }

    public void setSignupBankName(String signupBankName) {
        this.signupBankName = signupBankName;
    }

    public String getSignupRib() {
        return signupRib;
    }

    public void setSignupRib(String signupRib) {
        this.signupRib = signupRib;
    }
}
