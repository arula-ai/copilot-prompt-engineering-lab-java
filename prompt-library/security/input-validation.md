# Pattern: Input Validation

**Category:** Security
**Language:** TypeScript / Java
**Success Rate:** 88%
**Last Verified:** December 2025

## Variables

| Variable | Description | Example Values |
|----------|-------------|----------------|
| `[input-type]` | Name of the input structure | `LoginRequest`, `TransactionDTO`, `UserProfile` |
| `[field-N]` | Field name to validate | `email`, `password`, `amount` |
| `[validation-rules]` | Rules for each field | `Required, max 254 chars, valid email` |
| `[security-concern-N]` | OWASP-aligned security considerations | `SQL injection`, `XSS`, `enumeration` |

## Prompt Template

```
Create an input validation function for [input-type]:

Input structure:
[paste interface or describe input]

Validation rules:
1. [field-1]: [validation-rules]
2. [field-2]: [validation-rules]
3. [field-3]: [validation-rules]

Security considerations:
- [security-concern-1]
- [security-concern-2]

Return format:
- Success: { valid: true, data: [sanitized-input] }
- Failure: { valid: false, errors: [array-of-error-messages] }

Constraints:
- Must sanitize all string inputs
- Must not throw exceptions
- Must provide specific error messages per field
- Must be synchronous
```

## Example 1: TypeScript/Angular Validation

```
Create an input validation function for LoginRequest:

Input structure:
interface LoginRequest {
  email: string;
  password: string;
  rememberMe?: boolean;
}

Validation rules:
1. email: Required, valid email format, max 254 chars, lowercase
2. password: Required, min 8 chars, max 128 chars, no leading/trailing whitespace
3. rememberMe: Optional, must be boolean if provided

Security considerations:
- Prevent email enumeration (generic error messages)
- No password content in error messages
- Sanitize email to prevent injection
- Rate limiting hint in response

Return format:
- Success: { valid: true, data: { sanitized LoginRequest } }
- Failure: { valid: false, errors: ["Email is required", "Invalid email format"] }

Constraints:
- Must sanitize all string inputs
- Must not throw exceptions
- Must provide specific error messages per field
- Must be synchronous
```

## Example 2: Java/Spring Validation with Bean Validation

```
Create an input validation service for TradeOrderRequest:

Input structure:
public record TradeOrderRequest(
    String symbol,
    String orderType,
    BigDecimal quantity,
    BigDecimal limitPrice,
    String accountId
) {}

Validation rules:
1. symbol: Required, 1-5 uppercase letters, must exist in valid symbols list
2. orderType: Required, enum value: MARKET, LIMIT, STOP, STOP_LIMIT
3. quantity: Required, positive, max 1,000,000, max 4 decimal places
4. limitPrice: Required for LIMIT/STOP_LIMIT orders, positive, max 2 decimal places
5. accountId: Required, UUID format, must belong to authenticated user

Security considerations:
- Validate accountId belongs to current user (prevent IDOR)
- Sanitize symbol to prevent injection in downstream queries
- Validate quantity against user's buying power (business rule)
- Log validation failures for security monitoring (without sensitive data)

Return format: Use Spring's BindingResult pattern OR custom ValidationResult<T>

Format: Java 17+ with:
- Bean Validation annotations (@NotNull, @Pattern, @DecimalMin)
- Custom validators for complex rules
- ValidationResult sealed interface for type-safe errors

Constraints:
- Thread-safe implementation
- No exceptions for validation failures (return typed errors)
- Support for i18n error messages
- Composable validators (reuse symbol validation, account validation)
```

### Expected Java Output Pattern

```java
// Custom validators
public sealed interface ValidationResult<T> permits Valid, Invalid {
    record Valid<T>(T value) implements ValidationResult<T> {}
    record Invalid<T>(List<ValidationError> errors) implements ValidationResult<T> {}
}

public record ValidationError(String field, String code, String message) {}

@Service
@RequiredArgsConstructor
public class TradeOrderValidator {
    private final SymbolService symbolService;
    private final AccountService accountService;

    public ValidationResult<TradeOrderRequest> validate(
            TradeOrderRequest request, UUID currentUserId) {

        List<ValidationError> errors = new ArrayList<>();

        // Symbol validation
        if (request.symbol() == null || request.symbol().isBlank()) {
            errors.add(new ValidationError("symbol", "REQUIRED", "Symbol is required"));
        } else if (!request.symbol().matches("^[A-Z]{1,5}$")) {
            errors.add(new ValidationError("symbol", "FORMAT", "Invalid symbol format"));
        } else if (!symbolService.exists(request.symbol())) {
            errors.add(new ValidationError("symbol", "NOT_FOUND", "Unknown symbol"));
        }

        // Order type validation
        if (!isValidOrderType(request.orderType())) {
            errors.add(new ValidationError("orderType", "INVALID", "Invalid order type"));
        }

        // Limit price required for LIMIT orders
        if (isLimitOrder(request.orderType()) && request.limitPrice() == null) {
            errors.add(new ValidationError("limitPrice", "REQUIRED",
                "Limit price required for limit orders"));
        }

        // Account ownership validation (prevents IDOR)
        if (!accountService.belongsToUser(request.accountId(), currentUserId)) {
            errors.add(new ValidationError("accountId", "UNAUTHORIZED",
                "Invalid account"));  // Generic message prevents enumeration
        }

        return errors.isEmpty()
            ? new Valid<>(sanitize(request))
            : new Invalid<>(errors);
    }

    private TradeOrderRequest sanitize(TradeOrderRequest request) {
        return new TradeOrderRequest(
            request.symbol().toUpperCase().trim(),
            request.orderType().toUpperCase().trim(),
            request.quantity().stripTrailingZeros(),
            request.limitPrice(),
            request.accountId().trim()
        );
    }
}
```

## Common Pitfalls

| Pitfall | Problem | Solution |
|---------|---------|----------|
| **Throwing exceptions** | Caller forgets try/catch | Return typed ValidationResult |
| **Password in error message** | Security leak | Only say "invalid" never echo input |
| **Specific "not found" errors** | Enables enumeration | Generic "invalid credentials" |
| **Missing sanitization** | XSS/injection risks | Always sanitize before storage |
| **Client-only validation** | Easily bypassed | Always validate server-side too |
| **Regex ReDoS** | Denial of service | Use bounded patterns, test with long inputs |

## Implementation Hints for AI

When generating validation code:
1. **Never echo user input** in error messages (security risk)
2. **Sanitize before validate** - trim whitespace, normalize case
3. **Use allowlists** over blocklists for format validation
4. **Consider timing attacks** - use constant-time comparisons for sensitive fields
5. **Log validation failures** for security monitoring (without sensitive data)

## OWASP Top 10 Alignment

| OWASP Risk | Validation Defense |
|------------|-------------------|
| A01 Broken Access Control | Validate ownership/authorization |
| A03 Injection | Sanitize + parameterized queries |
| A04 Insecure Design | Validate all inputs server-side |
| A07 Auth Failures | Generic errors prevent enumeration |

## Expected Output Quality: 9/10
