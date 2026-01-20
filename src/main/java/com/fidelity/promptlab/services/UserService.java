package com.fidelity.promptlab.services;

import com.fidelity.promptlab.models.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * This service has intentional issues for participants to improve with good prompts.
 *
 * INTENTIONAL ISSUES:
 * 1. Login returns raw Map instead of proper LoginResponse
 * 2. No input validation on createUser
 * 3. Returns null instead of Optional
 * 4. O(n) email lookup instead of indexed query
 * 5. Missing audit logging on updateUser
 * 6. Hard delete with no soft delete option
 * 7. No @Transactional annotations
 * 8. No @Cacheable for frequently accessed data
 */
@Service
public class UserService {

    // Challenge: In-memory storage instead of repository
    // Should use: private final UserRepository userRepository;
    private final Map<String, User> users = new HashMap<>();

    // Challenge: This login method is incomplete and insecure
    // - Returns raw Map instead of Result<LoginResponse, AuthError>
    // - No password hashing verification
    // - No rate limiting
    // - No audit logging
    public Map<String, Object> login(String email, String password) {
        // TODO: Implement proper authentication
        // Current implementation is intentionally weak
        Map<String, Object> result = new HashMap<>();
        if (email != null && !email.isBlank() && password != null && !password.isBlank()) {
            result.put("success", true);
            result.put("token", "fake-token");
            // Bug: No actual credential verification
            // Bug: Token is not a real JWT
        } else {
            result.put("success", false);
        }
        return result;
    }

    // Challenge: No input validation, accepts raw Map
    // Should accept: CreateUserRequest with @Valid
    // Should return: Result<User, ValidationError>
    public User createUser(Map<String, Object> data) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail((String) data.get("email")); // Bug: No email validation
        user.setFirstName((String) data.get("firstName")); // Bug: No null check
        user.setLastName((String) data.get("lastName"));
        user.setRole(UserRole.CUSTOMER);
        user.setPreferences(new UserPreferences());

        users.put(user.getId(), user);
        return user;
    }

    // Challenge: Returns null instead of Optional
    // Should be: Optional<User> or throw EntityNotFoundException
    public User getUser(String id) {
        return users.get(id); // Bug: Returns null if not found
    }

    // Challenge: O(n) lookup - should use indexed query
    // Should be: userRepository.findByEmail(email)
    public User findByEmail(String email) {
        for (User user : users.values()) {
            if (user.getEmail().equals(email)) { // Bug: NPE if email is null
                return user;
            }
        }
        return null;
    }

    // Challenge: Missing audit logging
    // Should: log who changed what, when, previous values
    // Should: use @Transactional
    public User updateUser(String id, Map<String, Object> updates) {
        User user = users.get(id);
        if (user == null) {
            throw new RuntimeException("User not found"); // Bug: Wrong exception type
        }

        // Bug: No validation of update values
        if (updates.containsKey("email")) {
            user.setEmail((String) updates.get("email"));
        }
        if (updates.containsKey("firstName")) {
            user.setFirstName((String) updates.get("firstName"));
        }
        if (updates.containsKey("lastName")) {
            user.setLastName((String) updates.get("lastName"));
        }

        return user;
    }

    // Challenge: Hard delete with no soft delete option
    // Should: set deletedAt timestamp, not remove from DB
    // Should: cascade or prevent if user has portfolios
    public void deleteUser(String id) {
        users.remove(id); // Bug: No confirmation, no soft delete, no cascade check
    }
}
