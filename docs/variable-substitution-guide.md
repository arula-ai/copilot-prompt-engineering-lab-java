# Variable Substitution Guide

Prompt library patterns use `[variable-name]` placeholders that you replace with your specific values. This guide shows you how to fill in each variable type effectively.

## How Variables Work

Pattern templates look like this:
```
Create a [service-name] service with a [method-name] method
that calls [endpoint] and returns [return-type].
```

You substitute your values:
```
Create a PortfolioService service with a getPortfolioById method
that calls GET /api/portfolios/{id} and returns Observable<Portfolio>.
```

## Common Variables by Category

### Service & Method Names

| Variable | Description | Good Examples | Bad Examples |
|----------|-------------|---------------|--------------|
| `[service-name]` | Name of the service class | `PortfolioService`, `UserAuthService`, `PaymentProcessor` | `Service1`, `MyService`, `Handler` |
| `[method-name]` | Name of the method | `getPortfolioById`, `validateUserInput`, `processPayment` | `doStuff`, `handle`, `run` |
| `[class-name]` | Name of a class | `UserProfile`, `TransactionRecord`, `ValidationResult` | `Data`, `Thing`, `Object` |

**Naming Tips:**
- Use descriptive names that indicate purpose
- Follow your project's naming conventions (camelCase, PascalCase)
- Include the action verb for methods (`get`, `create`, `validate`, `process`)

### Endpoints & URLs

| Variable | Description | Good Examples | Bad Examples |
|----------|-------------|---------------|--------------|
| `[endpoint]` | API endpoint path | `GET /api/portfolios/{id}`, `POST /api/users`, `DELETE /api/orders/{orderId}` | `/api/data`, `endpoint`, `/stuff` |
| `[base-url]` | Base API URL | `https://api.example.com/v2`, `${environment.apiUrl}` | `url`, `http://localhost` |

**Endpoint Tips:**
- Include HTTP method: `GET /api/...`, `POST /api/...`
- Use path parameters with braces: `/users/{userId}/orders/{orderId}`
- Be specific about resource names

### Types & Interfaces

| Variable | Description | Good Examples | Bad Examples |
|----------|-------------|---------------|--------------|
| `[return-type]` | Method return type | `Observable<Portfolio>`, `Promise<User[]>`, `Result<Order, OrderError>` | `any`, `object`, `data` |
| `[input-type]` | Input parameter type | `CreateUserRequest`, `LoginCredentials`, `SearchFilters` | `input`, `params`, `data` |
| `[error-type]` | Error type | `PortfolioNotFoundError`, `ValidationError`, `NetworkError` | `Error`, `Exception`, `Problem` |
| `[entity-type]` | Domain entity | `User`, `Portfolio`, `Transaction`, `Order` | `Item`, `Thing`, `Record` |

**Type Tips:**
- Use your project's existing types when available
- For new types, use descriptive domain names
- Consider using `Result<Success, Failure>` pattern for error handling

### Configuration & Options

| Variable | Description | Good Examples | Bad Examples |
|----------|-------------|---------------|--------------|
| `[caching]` | Caching strategy | `shareReplay(1) with 5-minute TTL`, `@Cacheable("portfolios")`, `no caching` | `cache`, `yes`, `true` |
| `[retry]` | Retry configuration | `3 attempts with exponential backoff (1s, 2s, 4s)`, `Resilience4j @Retry`, `no retry` | `retry`, `3`, `yes` |
| `[timeout]` | Timeout duration | `30 seconds`, `5000ms`, `Duration.ofSeconds(10)` | `timeout`, `long`, `short` |
| `[validation]` | Validation rules | `@NotNull, @Email, @Size(min=8)`, `Zod schema with email and password` | `validate`, `check`, `rules` |

**Configuration Tips:**
- Be specific about values (not just "yes" or "no")
- Include units for time-based values
- Reference framework-specific annotations/decorators

### Error Handling

| Variable | Description | Good Examples | Bad Examples |
|----------|-------------|---------------|--------------|
| `[errors]` | Error types to handle | `PortfolioNotFoundError, NetworkError, ValidationError (sealed interface)` | `errors`, `exceptions`, `problems` |
| `[error-codes]` | HTTP/error codes | `401 (unauthorized), 403 (forbidden), 404 (not found), 429 (rate limited), 500+ (server error)` | `errors`, `codes`, `4xx` |
| `[error-response]` | Error response format | `{ code: string, message: string, details?: Record<string, string> }` | `error object`, `response`, `message` |

**Error Handling Tips:**
- List specific error codes, not ranges
- Describe what should happen for each error
- Include both client errors (4xx) and server errors (5xx)

### Testing

| Variable | Description | Good Examples | Bad Examples |
|----------|-------------|---------------|--------------|
| `[test-framework]` | Testing framework | `Jest with @testing-library/angular`, `JUnit 5 with Mockito and AssertJ` | `tests`, `framework`, `unit tests` |
| `[coverage-target]` | Coverage requirements | `90% line coverage, 85% branch coverage, 100% critical paths` | `high`, `good`, `100%` |
| `[mock-strategy]` | How to mock dependencies | `jest.mock() for HttpClient, manual mock for AuthService` | `mock`, `fake`, `stub` |

### Documentation

| Variable | Description | Good Examples | Bad Examples |
|----------|-------------|---------------|--------------|
| `[doc-format]` | Documentation style | `JSDoc with @param, @returns, @throws, @example`, `Javadoc with full descriptions` | `docs`, `comments`, `documentation` |
| `[audience]` | Who reads the docs | `developers integrating with this API`, `end users of the library` | `people`, `users`, `readers` |

## Complete Substitution Examples

### Example 1: API Service Method Pattern

**Pattern template:**
```
Create a [service-name] service with a [method-name] method
that calls [endpoint] and returns [return-type].

Features:
- Caching: [caching]
- Retry: [retry]
- Error handling: [errors]
```

**Filled in (Angular):**
```
Create a PortfolioService service with a getPortfolioById method
that calls GET /api/portfolios/{id} and returns Observable<Result<Portfolio, PortfolioError>>.

Features:
- Caching: shareReplay(1) with 5-minute TTL, invalidate on mutations
- Retry: 3 attempts with exponential backoff (1s, 2s, 4s), only on 5xx errors
- Error handling: PortfolioNotFoundError (404), UnauthorizedError (401),
  NetworkError (connection failed), ServerError (500+)
```

**Filled in (Java):**
```
Create a PortfolioService service with a getPortfolioById method
that calls GET /api/portfolios/{id} and returns Result<Portfolio, PortfolioError>.

Features:
- Caching: @Cacheable("portfolios") with 5-minute TTL via Spring Cache
- Retry: @Retry with maxAttempts=3, exponentialBackoff (1s, 2s, 4s) via Resilience4j
- Error handling: PortfolioNotFoundError, UnauthorizedError, NetworkError,
  ServerError (sealed interface hierarchy)
```

### Example 2: Unit Test Suite Pattern

**Pattern template:**
```
Generate [test-framework] tests for [method-name] covering:
1. Happy path: [happy-path-scenarios]
2. Edge cases: [edge-cases]
3. Error cases: [error-cases]

Mocking strategy: [mock-strategy]
Coverage target: [coverage-target]
```

**Filled in:**
```
Generate JUnit 5 with Mockito and AssertJ tests for calculatePortfolioReturn covering:
1. Happy path: positive return (gain), negative return (loss), break-even,
   return with dividends included
2. Edge cases: zero initial value (should throw), very small values (0.01),
   very large values (billions), maximum precision values
3. Error cases: null initialValue (NPE), null finalValue (NPE),
   negative initial value (IllegalArgumentException)

Mocking strategy: @Mock for PortfolioRepository, @InjectMocks for service under test,
                  use ArgumentCaptor to verify repository calls
Coverage target: 95% line coverage, 90% branch coverage, 100% of exception paths
```

### Example 3: Input Validation Pattern

**Pattern template:**
```
Create validation for [input-type] with rules:
[validation-rules]

Return type: [return-type]
Security considerations: [security-notes]
```

**Filled in:**
```
Create validation for CreateUserRequest with rules:
- email: required, valid email format, max 255 chars, lowercase normalized
- password: required, min 12 chars, must contain uppercase, lowercase,
  number, and special character
- username: required, 3-30 chars, alphanumeric and underscores only,
  not in reserved words list
- dateOfBirth: required, must be at least 18 years ago, not in future

Return type: Result<ValidatedUser, ValidationErrors> where ValidationErrors
             contains field-level error messages

Security considerations:
- Use constant-time comparison for username reserved word check
- Don't reveal which field failed in auth contexts (prevent enumeration)
- Sanitize all string inputs (trim whitespace, normalize unicode)
- Rate limit validation attempts per IP
```

## Variable Substitution Checklist

Before using a pattern, verify:

- [ ] All `[variables]` replaced with actual values
- [ ] No placeholder text remaining (search for `[` in your prompt)
- [ ] Values are specific to your project, not generic examples
- [ ] Types match your project's type system
- [ ] Naming follows your project's conventions
- [ ] Framework/library references are correct for your stack

## Common Mistakes

| Mistake | Example | Fix |
|---------|---------|-----|
| Too generic | `[return-type]` → `object` | Use specific type: `Portfolio` |
| Leaving placeholders | `[service-name]Service` | Replace fully: `PortfolioService` |
| Wrong framework | Using RxJS example in Java | Use framework-appropriate example |
| Inconsistent naming | `getUserById` but `user_service` | Match your project's style |
| Missing details | `[caching]` → `yes` | Specify: `5-minute TTL, invalidate on write` |

## Tips for Better Substitution

1. **Copy from existing code:** Look at similar services/tests in your codebase
2. **Be specific:** `3 attempts with 1s/2s/4s backoff` beats `retry logic`
3. **Use real names:** Your actual service names, not `MyService`
4. **Include context:** `Spring Boot 3.2` not just `Spring`
5. **List all cases:** All error codes, all edge cases, all scenarios

---

*Good variable substitution is the difference between a generic output and production-ready code.*
