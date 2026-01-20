---
agent: 'agent'
description: 'Creates a Java Spring Boot API service method with caching, retry, and error handling'
tools: ['search/changes', 'search/codebase', 'edit/editFiles', 'read/problems']
argument-hint: 'Provide: service name, method name, endpoint, return type'
---

# API Service Method Pattern (Java)

**Category:** Code Generation | **Language:** Java 17 / Spring Boot | **Success Rate:** 93%

Create a Java service method with the following specifications:

## Required Information

Please provide:
- **Service name**: e.g., Portfolio, User, Transaction
- **Method name**: e.g., getAccountBalances, findByUserId
- **Input parameters**: e.g., String userId, List<String> accountIds
- **Return type**: e.g., List<AccountBalance>, Optional<User>, Mono<List<AccountBalance>>
- **Framework variant**: Spring Data JPA, WebFlux with WebClient, or RestClient

## Features to Include

1. **@Cacheable** with configurable TTL (cache key = all params)
2. **@Retryable** for transient failures (3 attempts, exponential backoff)
3. **@CircuitBreaker** with fallback method (for external calls)
4. **Transaction management** (@Transactional where appropriate)
5. **Metrics emission** for latency and error rates

## Error Handling Requirements

- **EntityNotFoundException**: Return Optional.empty() or throw with 404 status
- **FeignException/WebClientException**: Return cached data or empty, log for monitoring
- **ValidationException**: Throw with 400 status and field-level errors
- **ServiceUnavailableException**: Trigger circuit breaker, use fallback

## Format Requirements

- Java 17 with records for DTOs
- Javadoc with @param, @return, @throws
- Constructor injection (no field injection)
- Annotations: @Service, @Cacheable, @Retryable, @Transactional

## Constraints

- Must use constructor injection, no @Autowired on fields
- Thread-safe implementation (stateless service)
- Retry should NOT apply to 4xx client errors
- Log at DEBUG level only (no sensitive data)
- Use BigDecimal for financial calculations

---

## Edge Cases to Handle

| Edge Case | Problem | Solution |
|-----------|---------|----------|
| Empty list vs null | `accountIds: []` vs `null` behave differently | Normalize: `accountIds == null ? List.of() : accountIds` |
| Cache key ordering | `[1,2]` vs `[2,1]` create different keys | Sort before creating cache key |
| Retry on auth errors | Retrying 401 wastes time | Check status before retry decision |

## Expected Output Quality: 9/10
