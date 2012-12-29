package com.breaker.webapp;

import com.breaker.user.User;

public class SignupView {

    private String submitButtonName;
    private String submitButtonValue;
    private String errorMessage;
    private String username;
    private String email;
    private String verifyEmail;
    private String password;
    private String verifyPassword;
    private User   user = new User();

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSubmitButtonName() {
        return submitButtonName;
    }

    public void setSubmitButtonName(String submitButtonName) {
        this.submitButtonName = submitButtonName;
    }

    public String getSubmitButtonValue() {
        return submitButtonValue;
    }

    public void setSubmitButtonValue(String submitButtonValue) {
        this.submitButtonValue = submitButtonValue;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifyEmail() {
        return verifyEmail;
    }

    public void setVerifyEmail(String verifyEmail) {
        this.verifyEmail = verifyEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}