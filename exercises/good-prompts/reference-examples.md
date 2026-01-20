# Reference Examples: Good Prompts (Java 17)

These prompts demonstrate effective use of the CRAFT framework for Java 17 development.

## Example 1: API Service Method

```
Context: Spring Boot 3.2 financial application with Spring Data JPA.
Services use constructor injection and return Optional for single entities.
Using Resilience4j for fault tolerance.

Role: Senior Java developer with Spring Boot expertise.

Action: Create a getAccountBalances method in AccountService that:
1. Accepts String userId and Optional<List<String>> accountIds
2. Queries AccountRepository with specifications
3. Uses @Cacheable with "account-balances" cache (5-minute TTL)
4. Implements @Retryable for transient failures (3 attempts, exponential backoff)
5. Uses @CircuitBreaker with fallback to cached data

Format: Java 17 with:
- Full Javadoc documentation
- Spring annotations
- Custom exceptions extending RuntimeException

Constraints:
- Must return List<AccountBalance> (never null, empty list if none)
- Must throw UserNotFoundException (404) if user doesn't exist
- Must use constructor injection (no @Autowired on fields)
- Must be thread-safe
- Log method entry/exit at DEBUG level
```

**Why it works:** Specific Spring patterns, fault tolerance, clear exception strategy.

---

## Example 2: Service Unit Tests

```
Context: JUnit 5 test class for TransactionService that processes
financial transactions. Service has dependencies on TransactionRepository
and NotificationService.

Role: QA engineer focused on Java testing best practices.

Action: Generate comprehensive tests for TransactionService.processTransaction():
1. Happy path - successful transaction processing
2. Validation failures - invalid amount, null fields
3. Repository exceptions - DataAccessException handling
4. Notification failures - should not fail transaction
5. Concurrent processing - thread safety

Format: JUnit 5 with:
- @Nested classes grouping related tests
- @DisplayName for readable descriptions
- @ParameterizedTest with @CsvSource for multiple inputs
- Mockito for dependencies (@Mock, @InjectMocks)
- AssertJ fluent assertions

Constraints:
- Each test follows AAA pattern (Arrange-Act-Assert)
- Use assertThatThrownBy for exception testing
- Verify mock interactions with verify()
- Minimum 12 test cases
- Test both success and failure paths
```

**Why it works:** Specifies JUnit 5 features, mocking strategy, clear test structure.

---

## Example 3: Global Exception Handler

```
Context: Spring Boot 3.2 REST API needing consistent error responses.
Using Problem Details (RFC 7807) format for errors.
Has existing AuditService for logging.

Role: Senior Spring developer with REST API expertise.

Action: Create a GlobalExceptionHandler with @ControllerAdvice that:
1. Handles MethodArgumentNotValidException - returns 400 with field errors
2. Handles EntityNotFoundException - returns 404
3. Handles AccessDeniedException - returns 403
4. Handles all other exceptions - returns 500, logs full stack trace
5. Creates ProblemDetail response for each case

Format: Java 17 with:
- @ControllerAdvice and @ExceptionHandler
- ProblemDetail builder (Spring 6)
- Structured logging with SLF4J MDC

Constraints:
- Never expose internal exception details to clients
- Always include correlation ID in response
- Log exceptions at appropriate levels (WARN for 4xx, ERROR for 5xx)
- Must be unit testable (no static methods)
- Handle both sync and async controller methods
```

**Why it works:** Modern Spring patterns, security considerations, observability.

---

## Example 4: Input Validation with Bean Validation

```
Context: Spring Boot REST endpoint receiving transaction requests.
Must validate against business rules and security requirements.
Using Jakarta Bean Validation 3.0.

Role: Senior Java developer with validation expertise.

Action: Create a TransactionRequest record with validation:
1. symbol: @NotBlank, @Pattern for stock symbol format, @Size(max=10)
2. quantity: @NotNull, @Positive, @Max(1_000_000)
3. price: @NotNull, @DecimalMin("0.01"), stored as BigDecimal
4. type: @NotNull, must be valid TransactionType enum
5. Custom cross-field validation: total (qty * price) under limit

Format: Java 17 with:
- Record class for immutability
- Jakarta validation annotations
- Custom @Constraint for cross-field validation
- Builder pattern for test convenience

Constraints:
- All monetary values as BigDecimal
- Immutable (record)
- Null-safe with Optional where appropriate
- Include validation messages suitable for API responses
- Thread-safe
```

**Why it works:** Modern Java patterns, comprehensive validation, security focus.

---

## Example 5: Repository with Specifications

```
Context: Spring Data JPA repository for Transaction entity.
Need complex dynamic queries with pagination and sorting.
Performance critical - millions of transactions.

Role: Senior Java developer with JPA/Hibernate expertise.

Action: Create TransactionRepository with:
1. Standard CRUD via JpaRepository<Transaction, UUID>
2. JpaSpecificationExecutor for dynamic queries
3. Custom query methods with @Query for complex joins
4. Specification builders for common filters (date range, status, symbol)
5. Projection interface for summary queries

Format: Java 17 with:
- Interface extending JpaRepository and JpaSpecificationExecutor
- Static factory methods in Specifications class
- @EntityGraph for eager loading where needed

Constraints:
- Avoid N+1 queries (use @EntityGraph or JOIN FETCH)
- Use parameterized queries (prevent SQL injection)
- Support pagination (Pageable parameter)
- Indexed columns for common queries
- Use read-only transactions for queries
```

**Why it works:** Performance considerations, security, Spring Data patterns.

---

## Key Java/Spring Patterns to Reference

| Pattern | When to Mention |
|---------|-----------------|
| `Optional<T>` | Nullable single values |
| `record` | DTOs, value objects |
| `@Transactional` | Database operations |
| `@Cacheable` | Expensive computations |
| `@Retryable` | Transient failures |
| `@Valid` | Input validation |
| `@Async` | Background processing |
| Constructor injection | All dependencies |
| Builder pattern | Complex object creation |
| Specification pattern | Dynamic queries |
