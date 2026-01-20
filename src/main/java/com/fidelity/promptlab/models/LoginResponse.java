package com.fidelity.promptlab.models;

import java.util.Optional;

/**
 * Response object for login attempts.
 */
public class LoginResponse {
    private boolean success;
    private String token;
    private User user;
    private String error;
    private boolean requiresTwoFactor;

    private LoginResponse() {}

    public static LoginResponse success(String token, User user) {
        LoginResponse response = new LoginResponse();
        response.success = true;
        response.token = token;
        response.user = user;
        return response;
    }

    public static LoginResponse failure(String error) {
        LoginResponse response = new LoginResponse();
        response.success = false;
        response.error = error;
        return response;
    }

    public static LoginResponse requiresTwoFactor() {
        LoginResponse response = new LoginResponse();
        response.success = false;
        response.requiresTwoFactor = true;
        return response;
    }

    // Getters
    public boolean isSuccess() { return success; }
    public Optional<String> getToken() { return Optional.ofNullable(token); }
    public Optional<User> getUser() { return Optional.ofNullable(user); }
    public Optional<String> getError() { return Optional.ofNullable(error); }
    public boolean isRequiresTwoFactor() { return requiresTwoFactor; }
}
