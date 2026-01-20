---
agent: 'agent'
description: 'Creates a data transformer function for converting between types with validation and error handling'
tools: ['search/changes', 'search/codebase', 'edit/editFiles', 'read/problems']
argument-hint: 'Provide: source type, target type, field mappings, and language'
---

# Data Transformer Pattern

**Category:** Code Generation | **Success Rate:** 91%

Create a data transformer function with the following specifications:

## Required Information

Please provide:
- **Source type**: e.g., APIHolding, UserDTO, RawTransaction
- **Target type**: e.g., DisplayHolding, UserEntity, TransactionRecord
- **Language**: TypeScript (strict mode) OR Java 17 with records
- **Domain context**: Financial (requires BigDecimal precision), User profile, Product catalog
- **Field mappings**: How each field transforms (e.g., `sym → symbol (uppercase)`)
- **Calculated fields**: Any derived values (e.g., `marketValue = qty * price`)

## Processing Mode

- **Single object**: Transform one item
- **Batch array**: Transform collections with error collection
- **Streaming**: For large datasets with memory efficiency

## Edge Cases to Handle

- Null/undefined values → use sensible defaults or flag as invalid
- Empty strings → treat as missing data
- Division by zero → return 0 for percentage calculations
- Invalid dates → use current date with warning
- Missing required fields → throw ValidationError

## Error Strategy Options

1. **Throw ValidationException** with field-level details
2. **Return Result<T, Error>** sealed type for functional handling
3. **Log and skip** invalid items in batch mode (collect errors)

## Format Requirements

- Pure function with full type definitions
- Input validation BEFORE transformation
- Null safety throughout
- JSDoc/Javadoc with @example
- No mutations of input (immutable transformation)
- Deterministic (same input = same output)

## Financial Domain Constraints (if applicable)

- All money values rounded to 2 decimal places
- Use BigDecimal for Java, explicit rounding for TypeScript
- Percentage calculations use safe division (check for zero)

---

## Common Pitfalls to Avoid

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Floating point math | `0.1 + 0.2 !== 0.3` in JS | Use BigDecimal (Java) or round explicitly |
| Mutating input | Side effects break determinism | Deep clone or use spread operator |
| Lossy date parsing | Timezone issues | Always specify timezone, use UTC internally |
| Silent null coercion | `null * 5 = 0` in JS | Explicit null checks before math |
| String case sensitivity | `"USD" !== "usd"` | Normalize case early in transform |

## Expected Output Quality: 9/10
