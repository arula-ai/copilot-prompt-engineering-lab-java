package com.fidelity.promptlab.challenges.lab1;

import com.fidelity.promptlab.models.Portfolio;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.List;

/**
 * LAB 1 - CHALLENGE 4: Error Handling
 *
 * BAD PROMPT: "Add error handling"
 *
 * This method has no error handling whatsoever.
 */
public class Challenge4Errors {

    private static final HttpClient client = HttpClient.newHttpClient();

    // No validation of userId
    // No try/catch
    // No timeout handling
    // No retry logic
    // No meaningful error messages
    public static List<Portfolio> fetchUserPortfolios(String userId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://api.example.com/users/" + userId + "/portfolios"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // Assumes JSON parsing would happen here
        return List.of(); // Placeholder
    }

    /**
     * Your task: Create a prompt that adds comprehensive error handling
     *
     * Consider:
     * - What exceptions can occur? (IOException, InterruptedException, etc.)
     * - How should each be handled?
     * - What should be logged? (SLF4J)
     * - What should the caller receive? (Result type, Optional, exceptions?)
     * - Should there be retries? (Resilience4j?)
     * - Connection timeouts?
     */

    // YOUR IMPROVED PROMPT:
    // ____________________________________________
    // ____________________________________________
    // ____________________________________________

    // QUALITY RATING (1-10): ___
}
