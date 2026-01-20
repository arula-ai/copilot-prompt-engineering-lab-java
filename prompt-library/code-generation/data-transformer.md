# Pattern: Data Transformer

**Category:** Code Generation
**Language:** TypeScript, Java
**Success Rate:** 91%
**Last Verified:** January 2026

## Prompt Template

```
Create a data transformer function that converts [source-type] to [target-type]:

Context:
- Language: [TypeScript/Java]
- Domain: [financial/user/product data]
- Performance: [single object/batch array/streaming]

Source structure:
[describe or paste source type/interface]

Target structure:
[describe or paste target type/interface]

Transformation rules:
1. [field-mapping-1]
2. [field-mapping-2]
3. [calculated-field-if-any]

Handle these edge cases:
- [edge-case-1]
- [edge-case-2]

Error strategy: [throw exception/return Result<T,E>/return null with logging]

Format: Pure function with full types
Include: Input validation, null safety, documentation
Constraints: No mutations of input, must be deterministic, [precision requirements]
```

## Variables

| Variable | Description | Example Values |
|----------|-------------|----------------|
| `[source-type]` | Input data type name | APIHolding, UserDTO, RawTransaction |
| `[target-type]` | Output data type name | DisplayHolding, UserEntity, TransactionRecord |
| `[language]` | Target language | TypeScript, Java 17 |
| `[domain]` | Business domain context | financial (needs precision), user profile, product catalog |
| `[performance]` | Processing mode | single object, batch (array), streaming (large datasets) |
| `[field-mapping-N]` | How fields map | `sym → symbol (uppercase)`, `created_at → createdAt (parse ISO)` |
| `[calculated-field]` | Derived values | `marketValue = qty * price`, `fullName = first + " " + last` |
| `[edge-case-N]` | Boundary conditions | null values, empty strings, division by zero, invalid dates |
| `[error-strategy]` | How to handle failures | throw ValidationException, return Result.failure(), log and skip |
| `[precision]` | Numeric precision rules | Round to 2 decimals, use BigDecimal, truncate vs round |

## Example Usage

### TypeScript: Financial Data

```
Create a data transformer function that converts APIHolding to DisplayHolding:

Context:
- Language: TypeScript (strict mode)
- Domain: Financial portfolio data (requires precision)
- Performance: Batch array transformation

Source structure:
interface APIHolding {
  sym: string;
  qty: number;
  avg_cost: number;
  cur_price: number | null;
  as_of: string; // ISO date string
}

Target structure:
interface DisplayHolding {
  symbol: string;
  quantity: number;
  averageCost: number;
  currentPrice: number;
  marketValue: number;
  gainLoss: number;
  gainLossPercent: number;
  lastUpdated: Date;
}

Transformation rules:
1. sym → symbol (uppercase, trim whitespace)
2. qty → quantity (ensure integer via Math.floor)
3. avg_cost → averageCost (round to 2 decimal places)
4. cur_price → currentPrice (default to 0 if null)
5. Calculate marketValue = quantity * currentPrice (round to 2 decimals)
6. Calculate gainLoss = marketValue - (quantity * averageCost)
7. Calculate gainLossPercent = (gainLoss / (quantity * averageCost)) * 100
8. as_of → lastUpdated (parse as Date)

Handle these edge cases:
- quantity is 0 → set gainLossPercent to 0 (avoid division by zero)
- cur_price is null or undefined → use 0, flag as stale data
- as_of is invalid date string → use current date, log warning
- sym is empty → throw validation error

Error strategy: Return Result<DisplayHolding[], TransformError> for batch,
               collect all errors rather than fail-fast

Format: Pure function with full TypeScript types
Include: Input validation, null safety, JSDoc with @example
Constraints: No mutations of input, deterministic, all money values to 2 decimals
```

### Java: Entity Mapping

```
Create a data transformer function that converts UserDTO to UserEntity:

Context:
- Language: Java 17 with records
- Domain: User profile data
- Performance: Single object transformation

Source structure:
public record UserDTO(
    String id,
    String email,
    String first_name,
    String last_name,
    String phone,
    String created_at,  // ISO-8601 string
    Map<String, Object> preferences
) {}

Target structure:
public record UserEntity(
    UUID id,
    String email,
    String firstName,
    String lastName,
    Optional<String> phoneNumber,
    LocalDateTime createdAt,
    UserPreferences preferences
) {}

Transformation rules:
1. id → parse as UUID
2. email → validate format, lowercase
3. first_name/last_name → trim, apply proper case
4. phone → normalize format, wrap in Optional
5. created_at → parse as LocalDateTime (UTC)
6. preferences map → convert to UserPreferences record

Handle these edge cases:
- id is not valid UUID format → throw IllegalArgumentException
- email fails validation → throw ValidationException with details
- phone is null or empty → return Optional.empty()
- created_at is null → use LocalDateTime.now()
- preferences is null → use default UserPreferences

Error strategy: Throw ValidationException with field-level error details

Format: Static factory method with full Java types
Include: Input validation, @NonNull annotations, Javadoc
Constraints: Immutable output, thread-safe, no external state
```

## Batch Transformation Pattern

For array/collection transformations, extend the prompt:

```
Additional requirements for batch transformation:
- Process as stream for memory efficiency
- Collect errors without stopping (fail-open)
- Return BatchResult<T> with:
  - successful: T[]
  - failed: { index: number, input: Source, error: string }[]
  - stats: { total, succeeded, failed, duration_ms }
- Support parallel processing hint: [sequential/parallel]
- Maximum batch size: [limit or unlimited]
```

## Common Pitfalls

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Floating point math | `0.1 + 0.2 !== 0.3` in JS | Use BigDecimal (Java) or decimal.js, round explicitly |
| Mutating input | Side effects break determinism | Deep clone or use spread operator |
| Lossy date parsing | Timezone issues | Always specify timezone, use UTC internally |
| Silent null coercion | `null * 5 = 0` in JS | Explicit null checks before math |
| String case sensitivity | `"USD" !== "usd"` | Normalize case early in transform |
| Missing fields in partial data | Runtime errors on access | Use optional chaining, provide defaults |

## TypeScript Implementation Hints

```typescript
// Precision helper for financial calculations
const roundMoney = (value: number): number =>
  Math.round(value * 100) / 100;

// Safe percentage calculation
const safePercent = (part: number, whole: number): number =>
  whole === 0 ? 0 : roundMoney((part / whole) * 100);

// Batch transform pattern
type TransformResult<T> = {
  data: T[];
  errors: Array<{ index: number; error: string }>;
};
```

## Java Implementation Hints

```java
// Precision helper for financial calculations
private static BigDecimal roundMoney(BigDecimal value) {
    return value.setScale(2, RoundingMode.HALF_UP);
}

// Safe percentage calculation
private static BigDecimal safePercent(BigDecimal part, BigDecimal whole) {
    if (whole.compareTo(BigDecimal.ZERO) == 0) {
        return BigDecimal.ZERO;
    }
    return part.divide(whole, 4, RoundingMode.HALF_UP)
               .multiply(BigDecimal.valueOf(100))
               .setScale(2, RoundingMode.HALF_UP);
}
```

## Related Patterns

- [api-service-method.md](./api-service-method.md) - For fetching data to transform
- [unit-test-suite.md](../testing/unit-test-suite.md) - For testing transformers
- [introduce-parameter-object.md](../refactoring/introduce-parameter-object.md) - When transformer has many options

## Expected Output Quality: 9/10

A well-crafted prompt using this pattern typically produces:
- Type-safe transformation with no implicit `any`
- Proper handling of all specified edge cases
- Pure function with no side effects
- Comprehensive documentation
- Financial precision when specified
