# Challenge 4: Error Handling - SOLUTION

## Bad Prompt
```
Add error handling
```

## Expert CRAFT Prompt

```
Context:
- Spring Boot 3 service method fetching user portfolios
- Uses Java HttpClient for API calls
- Part of financial application requiring high reliability
- Current implementation has NO error handling:
  ```java
  public static List<Portfolio> fetchUserPortfolios(String userId) throws Exception {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("http://api.example.com/users/" + userId + "/portfolios"))
          .GET()
          .build();
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      return List.of(); // Placeholder
  }
  ```

Role: Senior Java developer specializing in resilient API integrations with Spring Boot.

Action: Add comprehensive error handling:
1. Input validation:
   - Validate userId is non-blank
   - Sanitize userId (URL encode to prevent injection)
2. HTTP error handling:
   - 400: Return validation error with details
   - 401: Return authentication error, trigger re-auth flow
   - 403: Return authorization error with permission details
   - 404: Return empty list (valid case - new user with no portfolios)
   - 429: Return rate limit error with retryAfter timestamp
   - 500+: Return server error with correlation ID for support
3. Network error handling:
   - Connection timeout: 5 seconds
   - Read timeout: 10 seconds
   - Retry 3x with exponential backoff for 5xx/network errors
   - NO retry on 4xx errors (except 429)
4. Transform all errors to typed domain errors:
   ```java
   sealed interface PortfolioError permits
       ValidationError, AuthError, RateLimitError, ServerError, NetworkError {}
   ```
5. Logging:
   - Log errors with context (userId, endpoint, duration, status code)
   - Use SLF4J with MDC for correlation
   - NEVER log tokens or sensitive data

Format:
- Return Result<List<Portfolio>, PortfolioError> (functional error handling)
- Use Spring's RestClient OR Java HttpClient with proper configuration
- Include Javadoc documentation
- Use Resilience4j for retry logic OR manual implementation

Constraints:
- Must use constructor injection for dependencies
- Must integrate with existing ErrorReportingService
- Request must be cancellable (CompletableFuture support)
- Timeout must be configurable via @Value or @ConfigurationProperties
```

## Why This Works

| CRAFT Element | What It Provides | Impact |
|---------------|------------------|--------|
| **Context** | Current broken code, app domain | AI sees the starting point |
| **Role** | Resilience expertise | Gets retry, timeout, circuit breaker patterns |
| **Action** | Every error type with handling | Complete coverage |
| **Format** | Sealed interfaces, Result type | Type-safe error handling |
| **Constraints** | DI, cancellation, logging rules | Production integration |

## Expected Output Quality: 9.5/10

```java
// Error types using sealed interfaces (Java 17+)
public sealed interface PortfolioError permits
    ValidationError, AuthError, RateLimitError, ServerError, NetworkError {
    String message();
}

public record ValidationError(String message, String field) implements PortfolioError {}
public record AuthError(String message, AuthAction action) implements PortfolioError {
    public enum AuthAction { LOGIN, REFRESH }
}
public record RateLimitError(String message, Instant retryAfter) implements PortfolioError {}
public record ServerError(String message, String correlationId) implements PortfolioError {}
public record NetworkError(String message, boolean canRetry) implements PortfolioError {}

@Service
@Slf4j
public class PortfolioService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final Duration connectTimeout;
    private final Duration readTimeout;
    private final int maxRetries;

    public PortfolioService(
            RestClient.Builder restClientBuilder,
            ObjectMapper objectMapper,
            @Value("${portfolio.api.connect-timeout:5s}") Duration connectTimeout,
            @Value("${portfolio.api.read-timeout:10s}") Duration readTimeout,
            @Value("${portfolio.api.max-retries:3}") int maxRetries) {

        this.restClient = restClientBuilder
            .baseUrl("https://api.example.com")
            .build();
        this.objectMapper = objectMapper;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.maxRetries = maxRetries;
    }

    /**
     * Fetches portfolios for a user with comprehensive error handling.
     *
     * @param userId the user ID to fetch portfolios for
     * @return Result containing either the portfolio list or a typed error
     */
    public Result<List<Portfolio>, PortfolioError> fetchUserPortfolios(String userId) {
        // Input validation
        if (userId == null || userId.isBlank()) {
            return Result.failure(new ValidationError("User ID is required", "userId"));
        }

        String sanitizedUserId = URLEncoder.encode(userId.trim(), StandardCharsets.UTF_8);
        String endpoint = "/users/" + sanitizedUserId + "/portfolios";

        MDC.put("userId", sanitizedUserId);
        MDC.put("endpoint", endpoint);

        try {
            return executeWithRetry(endpoint, 0);
        } finally {
            MDC.clear();
        }
    }

    private Result<List<Portfolio>, PortfolioError> executeWithRetry(String endpoint, int attempt) {
        long startTime = System.currentTimeMillis();

        try {
            ResponseEntity<String> response = restClient.get()
                .uri(endpoint)
                .retrieve()
                .toEntity(String.class);

            long duration = System.currentTimeMillis() - startTime;
            log.info("API call successful: status={}, duration={}ms",
                response.getStatusCode().value(), duration);

            List<Portfolio> portfolios = objectMapper.readValue(
                response.getBody(),
                new TypeReference<List<Portfolio>>() {}
            );
            return Result.success(portfolios);

        } catch (HttpClientErrorException e) {
            return handleClientError(e);
        } catch (HttpServerErrorException e) {
            return handleServerError(e, endpoint, attempt);
        } catch (ResourceAccessException e) {
            return handleNetworkError(e, endpoint, attempt);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse response", e);
            return Result.failure(new ServerError("Invalid response format", "parse-error"));
        }
    }

    private Result<List<Portfolio>, PortfolioError> handleClientError(HttpClientErrorException e) {
        int status = e.getStatusCode().value();

        return switch (status) {
            case 400 -> Result.failure(new ValidationError("Invalid request", null));
            case 401 -> Result.failure(new AuthError("Session expired", AuthError.AuthAction.LOGIN));
            case 403 -> Result.failure(new AuthError("Access denied", AuthError.AuthAction.REFRESH));
            case 404 -> {
                log.info("No portfolios found for user (new user)");
                yield Result.success(List.of()); // Valid case - new user
            }
            case 429 -> {
                String retryAfterHeader = e.getResponseHeaders() != null
                    ? e.getResponseHeaders().getFirst("Retry-After")
                    : "60";
                Instant retryAfter = Instant.now().plusSeconds(
                    Long.parseLong(Objects.requireNonNullElse(retryAfterHeader, "60"))
                );
                yield Result.failure(new RateLimitError("Too many requests", retryAfter));
            }
            default -> Result.failure(new ValidationError("Request failed: " + status, null));
        };
    }

    private Result<List<Portfolio>, PortfolioError> handleServerError(
            HttpServerErrorException e, String endpoint, int attempt) {

        String correlationId = e.getResponseHeaders() != null
            ? e.getResponseHeaders().getFirst("X-Correlation-ID")
            : UUID.randomUUID().toString();

        log.error("Server error: status={}, correlationId={}",
            e.getStatusCode().value(), correlationId);

        if (attempt < maxRetries) {
            sleep(calculateBackoff(attempt));
            return executeWithRetry(endpoint, attempt + 1);
        }

        return Result.failure(new ServerError("Service unavailable", correlationId));
    }

    private Result<List<Portfolio>, PortfolioError> handleNetworkError(
            ResourceAccessException e, String endpoint, int attempt) {

        log.warn("Network error on attempt {}: {}", attempt + 1, e.getMessage());

        if (attempt < maxRetries) {
            sleep(calculateBackoff(attempt));
            return executeWithRetry(endpoint, attempt + 1);
        }

        return Result.failure(new NetworkError("Connection failed", true));
    }

    private long calculateBackoff(int attempt) {
        return (long) Math.pow(2, attempt) * 1000; // 1s, 2s, 4s
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

## Error Handling Decision Tree

```
Request Made
    │
    ├─► Timeout? ──► Retry (up to 3x with exponential backoff)
    │
    ├─► 401? ──► Return AuthError(LOGIN)
    │
    ├─► 403? ──► Return AuthError(REFRESH)
    │
    ├─► 404? ──► Return SUCCESS with empty list
    │
    ├─► 429? ──► Return RateLimitError with retry time
    │
    ├─► 4xx? ──► Return ValidationError (NO retry)
    │
    ├─► 5xx? ──► Retry 3x, then return ServerError
    │
    └─► Network? ──► Retry 3x, then return NetworkError
```

## Result Type Implementation

```java
public sealed interface Result<S, F> permits Result.Success, Result.Failure {

    record Success<S, F>(S value) implements Result<S, F> {}
    record Failure<S, F>(F error) implements Result<S, F> {}

    static <S, F> Result<S, F> success(S value) {
        return new Success<>(value);
    }

    static <S, F> Result<S, F> failure(F error) {
        return new Failure<>(error);
    }

    default <T> T fold(Function<S, T> onSuccess, Function<F, T> onFailure) {
        return switch (this) {
            case Success<S, F> s -> onSuccess.apply(s.value());
            case Failure<S, F> f -> onFailure.apply(f.error());
        };
    }
}
```

## Usage Example

```java
Result<List<Portfolio>, PortfolioError> result = portfolioService.fetchUserPortfolios(userId);

return result.fold(
    portfolios -> ResponseEntity.ok(portfolios),
    error -> switch (error) {
        case AuthError e -> ResponseEntity.status(401).body(e.message());
        case RateLimitError e -> ResponseEntity.status(429)
            .header("Retry-After", String.valueOf(e.retryAfter().getEpochSecond()))
            .body(e.message());
        case ServerError e -> ResponseEntity.status(503).body("Error ID: " + e.correlationId());
        default -> ResponseEntity.badRequest().body(error.message());
    }
);
```
