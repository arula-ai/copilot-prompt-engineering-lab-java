package com.fidelity.promptlab.models;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Core domain type representing a user in the system.
 */
public class User {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private UserPreferences preferences;

    public User() {}

    public User(String id, String email, String firstName, String lastName, UserRole role) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.preferences = new UserPreferences();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Optional<LocalDateTime> getLastLogin() { return Optional.ofNullable(lastLogin); }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    public UserPreferences getPreferences() { return preferences; }
    public void setPreferences(UserPreferences preferences) { this.preferences = preferences; }
}
