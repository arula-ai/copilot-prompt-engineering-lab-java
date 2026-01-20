# Pattern: API Service Method

**Category:** Code Generation
**Language:** TypeScript/Angular, Java/Spring
**Success Rate:** 94%
**Last Verified:** January 2026

## Prompt Template

```
Create a [service-name] service method that:

Context:
- Language: [TypeScript/Java]
- Framework: [Angular 17+/React/Spring Boot 3]
- Existing patterns: [describe existing service patterns]
- Dependencies: [list relevant services/modules]

Method Requirements:
- Name: [method-name]
- Accepts: [input-parameters-with-types]
- Returns: [Observable<T>/Promise<T>/Mono<T>/CompletableFuture<T>] of [return-type]
- HTTP: [GET/POST/PUT/DELETE] [endpoint-path]

Features to include:
1. [feature-1, e.g., "Caching with 5-minute TTL"]
2. [feature-2, e.g., "Retry logic with exponential backoff (3 attempts)"]
3. [feature-3, e.g., "Error transformation to domain errors"]

Error handling:
- [error-scenario-1]: [how to handle]
- [error-scenario-2]: [how to handle]

Format: [TypeScript/Java] with full type definitions and documentation
Constraints: [any constraints, e.g., "No external dependencies", "Thread-safe"]
```

## Variables

| Variable | Description | Example Values |
|----------|-------------|----------------|
| `[service-name]` | Name of the service | Portfolio, User, Transaction |
| `[language]` | Target language | TypeScript, Java 17 |
| `[framework]` | Framework and version | Angular 17, Spring Boot 3.2 |
| `[method-name]` | Method to create | getBalances, findByUserId, createTransaction |
| `[input-parameters]` | Method inputs with types | `userId: string, accountIds?: string[]` / `String userId, List<String> accountIds` |
| `[return-type]` | What method returns | `Observable<AccountBalance[]>` / `Mono<List<AccountBalance>>` |
| `[endpoint-path]` | API endpoint | `/api/users/{userId}/accounts` |
| `[feature-N]` | Required features | Caching, Retry logic, Request cancellation, Circuit breaker |
| `[error-scenario]` | Error cases and handling | 401 → redirect to login, 404 → return empty, 500 → throw with message |

## Example Usage

### Angular 17+ with Modern Patterns

```
Create a Portfolio service method that:

Context:
- Language: TypeScript (strict mode)
- Framework: Angular 17 with standalone components
- Existing patterns: Services use inject(), HttpClient, return Observables
- Dependencies: HttpClient, AuthService (via inject())

Method Requirements:
- Name: getAccountBalances
- Accepts: userId: string, options?: { accountIds?: string[], forceRefresh?: boolean }
- Returns: Observable<AccountBalance[]>
- HTTP: GET /api/users/{userId}/accounts/balances

Features to include:
1. Caching with shareReplay(1) and 5-minute TTL using timer
2. Retry logic with exponential backoff (3 attempts, 1s/2s/4s delays)
3. Request cancellation support via takeUntilDestroyed()
4. Cache invalidation when forceRefresh is true
5. Loading state signal for UI binding

Error handling:
- 401 Unauthorized: Clear auth state, redirect to /login
- 404 Not Found: Return empty array (not an error)
- 429 Rate Limited: Retry after delay from header
- 500+ Server Error: Transform to UserFacingError with retry option

Format: TypeScript with:
- inject() for DI (no constructor)
- Full type definitions
- JSDoc with @example
- Exported as standalone providedIn: 'root' service

Constraints:
- Must work with OnPush change detection
- Cache key must include userId and sorted accountIds
- Only retry on 5xx and network errors, not 4xx
```

### Spring Boot 3 with WebClient

```
Create a Portfolio service method that:

Context:
- Language: Java 17
- Framework: Spring Boot 3.2 with WebFlux
- Existing patterns: Services use WebClient, return Mono/Flux
- Dependencies: WebClient, CacheManager, MeterRegistry

Method Requirements:
- Name: getAccountBalances
- Accepts: String userId, List<String> accountIds (optional)
- Returns: Mono<List<AccountBalance>>
- HTTP: GET /api/users/{userId}/accounts/balances

Features to include:
1. @Cacheable with 5-minute TTL, cache key = userId + sorted accountIds
2. Retry with exponential backoff (3 attempts) using Reactor retry
3. Circuit breaker using Resilience4j
4. Metrics emission for latency and error rates
5. Request timeout of 10 seconds

Error handling:
- 401 Unauthorized: Throw AuthenticationException
- 404 Not Found: Return empty list (Mono.just(List.of()))
- 429 Rate Limited: Retry with Retry-After header value
- 500+ Server Error: Throw ServiceUnavailableException with circuit breaker

Format: Java with:
- @Service annotation
- Full Javadoc with @param, @return, @throws
- Lombok @RequiredArgsConstructor for DI
- @Cacheable, @Retryable annotations

Constraints:
- Thread-safe (stateless service)
- Must not block (reactive only)
- Log request/response at DEBUG level (no sensitive data)
```

### Spring Boot 3 with RestClient (Blocking)

```
Create a User service method that:

Context:
- Language: Java 17
- Framework: Spring Boot 3.2 with RestClient (blocking)
- Existing patterns: Services use RestClient, return domain objects
- Dependencies: RestClient, CacheManager

Method Requirements:
- Name: findUserById
- Accepts: String userId
- Returns: Optional<User>
- HTTP: GET /api/users/{userId}

Features to include:
1. @Cacheable("users") with userId as key
2. @Retryable for transient failures (3 attempts)
3. @Transactional(readOnly = true) for consistency
4. Metrics via @Timed annotation

Error handling:
- 404 Not Found: Return Optional.empty()
- 401/403: Throw AccessDeniedException
- 500+: Throw wrapped ServiceException with correlation ID

Format: Java with full annotations and Javadoc
Constraints: Must use RestClient (not WebClient), synchronous
```

## Common Pitfalls

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Retrying 4xx errors | Wastes resources, won't succeed | Only retry 5xx and network errors |
| Cache key missing params | Wrong cached data returned | Include ALL parameters in cache key |
| Blocking in reactive | Thread starvation | Use subscribeOn(Schedulers.boundedElastic()) |
| No request timeout | Hung requests consume resources | Set explicit timeout (default 30s) |
| Logging sensitive data | Security/compliance violation | Mask tokens, passwords, PII in logs |
| No circuit breaker | Cascade failures | Add circuit breaker for external calls |
| Sharing subscriptions | Multiple HTTP calls | Use shareReplay(1) for caching |

## Implementation Hints

### Angular/TypeScript

```typescript
@Injectable({ providedIn: 'root' })
export class PortfolioService {
  private readonly http = inject(HttpClient);
  private readonly destroyRef = inject(DestroyRef);

  // Cache with TTL pattern
  private cache = new Map<string, { data: Observable<any>; expires: number }>();

  private getCached<T>(key: string, factory: () => Observable<T>, ttlMs = 300000): Observable<T> {
    const cached = this.cache.get(key);
    if (cached && cached.expires > Date.now()) {
      return cached.data as Observable<T>;
    }
    const data$ = factory().pipe(shareReplay(1));
    this.cache.set(key, { data: data$, expires: Date.now() + ttlMs });
    return data$;
  }

  // Retry with exponential backoff
  private retryWithBackoff<T>(maxRetries = 3): MonoTypeOperatorFunction<T> {
    return retry({
      count: maxRetries,
      delay: (error, retryCount) => {
        if (error.status && error.status < 500) throw error; // Don't retry 4xx
        return timer(Math.pow(2, retryCount - 1) * 1000);
      }
    });
  }
}
```

### Java/Spring

```java
@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final WebClient webClient;
    private final CacheManager cacheManager;

    // Cache key generation
    private String cacheKey(String userId, List<String> accountIds) {
        var sortedIds = accountIds == null ? "" :
            accountIds.stream().sorted().collect(Collectors.joining(","));
        return userId + ":" + sortedIds;
    }

    // Retry spec for WebClient
    private Retry retrySpec() {
        return Retry.backoff(3, Duration.ofSeconds(1))
            .filter(ex -> ex instanceof WebClientResponseException.ServiceUnavailable
                       || ex instanceof ConnectException)
            .onRetryExhaustedThrow((spec, signal) -> signal.failure());
    }
}
```

### Java Streams Patterns

```java
// Filtering and transforming API responses
public List<AccountBalance> getActiveBalances(String userId) {
    return fetchAllBalances(userId).stream()
        .filter(balance -> balance.getStatus() == Status.ACTIVE)
        .filter(balance -> balance.getAmount().compareTo(BigDecimal.ZERO) > 0)
        .sorted(Comparator.comparing(AccountBalance::getAmount).reversed())
        .toList(); // Java 16+ (or .collect(Collectors.toList()))
}

// Grouping by category
public Map<AccountType, List<AccountBalance>> groupByType(List<AccountBalance> balances) {
    return balances.stream()
        .collect(Collectors.groupingBy(AccountBalance::getAccountType));
}

// Aggregation with BigDecimal (financial precision)
public BigDecimal calculateTotalValue(List<AccountBalance> balances) {
    return balances.stream()
        .map(AccountBalance::getAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
}

// Parallel processing for large datasets (use with caution)
public List<EnrichedBalance> enrichBalances(List<AccountBalance> balances) {
    return balances.parallelStream()
        .map(this::enrichWithMarketData)  // expensive operation
        .collect(Collectors.toList());
}

// Collecting to Map with duplicate key handling
public Map<String, AccountBalance> toMapBySymbol(List<AccountBalance> balances) {
    return balances.stream()
        .collect(Collectors.toMap(
            AccountBalance::getSymbol,
            Function.identity(),
            (existing, replacement) -> existing  // keep first on duplicate
        ));
}

// FlatMap for nested collections
public List<Transaction> getAllTransactions(List<Portfolio> portfolios) {
    return portfolios.stream()
        .flatMap(p -> p.getTransactions().stream())
        .sorted(Comparator.comparing(Transaction::getDate).reversed())
        .toList();
}

// Optional handling in streams
public List<String> getActiveEmails(List<User> users) {
    return users.stream()
        .filter(User::isActive)
        .map(User::getEmail)
        .flatMap(Optional::stream)  // Filters out empty Optionals
        .toList();
}

// Partitioning (split into two groups)
public Map<Boolean, List<Transaction>> partitionByProfit(List<Transaction> txns) {
    return txns.stream()
        .collect(Collectors.partitioningBy(t -> t.getGainLoss().compareTo(BigDecimal.ZERO) > 0));
}

// Statistics for numeric streams
public DoubleSummaryStatistics getBalanceStats(List<AccountBalance> balances) {
    return balances.stream()
        .mapToDouble(b -> b.getAmount().doubleValue())
        .summaryStatistics();  // count, sum, min, max, average
}

// Teeing collector (Java 12+) - two results in one pass
public record BalanceSummary(BigDecimal total, long count) {}

public BalanceSummary summarize(List<AccountBalance> balances) {
    return balances.stream()
        .collect(Collectors.teeing(
            Collectors.reducing(BigDecimal.ZERO, AccountBalance::getAmount, BigDecimal::add),
            Collectors.counting(),
            BalanceSummary::new
        ));
}
```

### Java Streams Best Practices

| Practice | Why | Example |
|----------|-----|---------|
| Prefer `toList()` over `collect(Collectors.toList())` | Cleaner, returns unmodifiable list (Java 16+) | `.toList()` |
| Use `flatMap(Optional::stream)` | Cleanly filters empty Optionals | See example above |
| Avoid `parallelStream()` for small collections | Overhead exceeds benefit | Only use for 10K+ items |
| Use `BigDecimal::add` for financial sums | Prevents floating point errors | `.reduce(BigDecimal.ZERO, BigDecimal::add)` |
| Close streams from I/O sources | Prevent resource leaks | `try (Stream<String> lines = Files.lines(path))` |
| Don't reuse streams | Streams are single-use | Create new stream for each operation |

## Pagination Pattern Extension

For paginated endpoints, add to the prompt:

```
Pagination requirements:
- Support cursor-based pagination (preferred) or offset/limit
- Return PagedResult<T> with: items, nextCursor, hasMore, totalCount (if available)
- Accept options: { limit?: number, cursor?: string, sortBy?: string, sortDir?: 'asc'|'desc' }
- Cache individual pages with cursor in cache key
- Provide fetchAll() variant that auto-paginates (with max limit safeguard)
```

## Related Patterns

- [data-transformer.md](./data-transformer.md) - For transforming API responses
- [unit-test-suite.md](../testing/unit-test-suite.md) - For testing service methods
- [auth-guard.md](../security/auth-guard.md) - For authentication handling
- [input-validation.md](../security/input-validation.md) - For validating inputs

## Edge Cases Discovered

| Edge Case | Problem | Solution |
|-----------|---------|----------|
| Empty array vs undefined | `accountIds: []` vs `accountIds: undefined` behave differently | Normalize in method: `accountIds ?? []` |
| Cache key ordering | `[1,2]` vs `[2,1]` create different keys | Sort arrays before creating cache key |
| Retry on auth errors | Retrying 401 wastes time | Check status before retry decision |
| Stale cache on error | Failed refresh serves old data | Invalidate cache on certain errors |
| Concurrent requests | Multiple callers duplicate work | Use shareReplay before caching |

## Expected Output Quality: 9/10

A well-crafted prompt using this pattern typically produces:
- Properly typed service with inject() or constructor DI
- Caching with configurable TTL and invalidation
- Retry logic that respects error types
- Comprehensive error transformation
- Observable/Mono composition best practices
