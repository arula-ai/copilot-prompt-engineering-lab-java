package com.fidelity.promptlab.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Standardized API error response.
 */
public class ApiError {
    private String code;
    private String message;
    private Map<String, Object> details;
    private LocalDateTime timestamp;

    public ApiError(String code, String message) {
        this.code = code;
        this.message = message;
        this.details = new HashMap<>();
        this.timestamp = LocalDateTime.now();
    }

    public ApiError addDetail(String key, Object value) {
        this.details.put(key, value);
        return this;
    }

    // Getters
    public String getCode() { return code; }
    public String getMessage() { return message; }
    public Map<String, Object> getDetails() { return details; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
