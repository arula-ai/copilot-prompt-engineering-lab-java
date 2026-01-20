# Pattern: API Service Method (Java)

**Category:** Code Generation
**Language:** Java 17 / Spring Boot
**Success Rate:** 93%
**Last Verified:** December 2025

## Prompt Template

```
Create a [service-name] service method in Java 17 that:

Context:
- Framework: [Spring Boot version] with [Spring Data JPA/WebFlux]
- Existing patterns: [describe existing service patterns]
- Dependencies: [list relevant services/repositories]

Method Requirements:
- Name: [method-name]
- Accepts: [input-parameters-with-types]
- Returns: [return-type / Optional<T> / CompletableFuture<T>]

Features to include:
1. [feature-1, e.g., "Caching with @Cacheable (5-minute TTL)"]
2. [feature-2, e.g., "Retry with @Retryable (3 attempts, exponential backoff)"]
3. [feature-3, e.g., "Transaction management with @Transactional"]

Error handling:
- [error-scenario-1]: [how to handle - custom exception?]
- [error-scenario-2]: [how to handle]

Format: Java 17 with full type definitions and Javadoc
Constraints: [any constraints, e.g., "Use constructor injection"]
```

## Variables

| Variable | Description | Example |
|----------|-------------|---------|
| service-name | Name of the service | "Portfolio" |
| method-name | Method to create | "getAccountBalances" |
| input-parameters | Method inputs | "String userId, List<String> accountIds" |
| return-type | What method returns | "List<AccountBalance>" |
| feature-1..n | Required features | "Caching", "Retry logic" |
| error-scenario | Error cases | "EntityNotFoundException", "ServiceUnavailableException" |

## Example Usage

```
Create a Portfolio service method in Java 17 that:

Context:
- Framework: Spring Boot 3.2 with Spring Data JPA
- Existing patterns: Services use constructor injection, return Optional for single entities
- Dependencies: PortfolioRepository, AccountClient (Feign), CacheManager

Method Requirements:
- Name: getAccountBalances
- Accepts: String userId, List<String> accountIds
- Returns: List<AccountBalance>

Features to include:
1. Caching with @Cacheable using "account-balances" cache (5-minute TTL)
2. Retry with @Retryable for FeignException (3 attempts, exponential backoff)
3. Circuit breaker with @CircuitBreaker (fallback method)

Error handling:
- UserNotFoundException: Throw with 404 status
- FeignException.ServiceUnavailable: Return cached data or empty list
- ValidationException: Throw with 400 status for invalid accountIds

Format: Java 17 with records for DTOs, Javadoc comments
Constraints: Must use constructor injection, no field injection
```

## Expected Output Quality: 9/10

## Edge Cases Discovered
- When accountIds is empty list vs null, handle differently
- Cache key must be composite (userId + sorted accountIds)
- Retry should not apply to 4xx client errors
