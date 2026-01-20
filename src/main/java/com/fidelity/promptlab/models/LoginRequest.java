package com.fidelity.promptlab.models;

/**
 * Request object for user login.
 *
 * NOTE: This is implemented as a record for Java 17 best practices.
 * Records provide:
 * - Immutability
 * - Auto-generated equals, hashCode, toString
 * - Compact constructor for validation
 */
public record LoginRequest(
    String email,
    String password,
    boolean rememberMe
) {
    /**
     * Compact constructor for validation.
     * In a production app, you'd use Bean Validation annotations instead.
     */
    public LoginRequest {
        // Challenge: This validation is minimal
        // Should use @NotBlank, @Email, @Size annotations
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
    }

    /**
     * Convenience constructor without rememberMe.
     */
    public LoginRequest(String email, String password) {
        this(email, password, false);
    }
}
