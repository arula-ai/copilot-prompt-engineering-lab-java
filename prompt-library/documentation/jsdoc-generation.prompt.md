---
agent: 'agent'
description: 'Generates comprehensive JSDoc or Javadoc documentation for functions, classes, and methods'
tools: ['search/changes', 'search/codebase', 'edit/editFiles', 'read/problems']
argument-hint: 'Provide: function/class name and paste the code to document'
---

# JSDoc / Javadoc Generation Pattern

**Category:** Documentation | **Success Rate:** 92%

Generate comprehensive documentation with the following specifications:

## Required Information

Please provide:
- **Target**: Function, class, or method name to document
- **Code to document**: Paste the code
- **Language**: TypeScript/JavaScript (JSDoc) or Java (Javadoc)
- **Description level**: Brief (1-2 sentences) or Detailed (with algorithm/formula)

## Documentation Requirements

1. **Description**: Explain purpose, not just what the code does
2. **Parameters**: Document ALL with types, descriptions, valid ranges
3. **Returns**: Document return value and all possible values
4. **Throws**: List ALL exceptions that can be thrown
5. **Examples**: Include 2-3 usage examples with realistic values

## Additional Tags to Include

- **@since**: Version when introduced
- **@see**: Cross-references to related functions/docs
- **@deprecated**: If applicable, with migration guidance

## Format

Standard JSDoc or Javadoc format with proper tags.

## Guidelines

1. **Start with the "why"** not the "what" - explain purpose before mechanics
2. **Document edge case behavior** - what happens with null, empty, zero?
3. **Use realistic examples** - domain-relevant values, not foo/bar
4. **Mention thread-safety** for concurrent methods
5. **Cross-reference related methods** using @see

---

## JSDoc Tags Reference

| Tag | Purpose | Example |
|-----|---------|---------|
| `@param` | Parameter description | `@param userId - The user's unique ID` |
| `@returns` | Return value | `@returns The user object or null` |
| `@throws` | Exception | `@throws {NotFoundError} If user doesn't exist` |
| `@example` | Usage example | See below |
| `@deprecated` | Deprecation | `@deprecated Use findUserById instead` |
| `@see` | Cross-reference | `@see UserService.findById` |
| `@since` | Version | `@since 1.0.0` |

## Javadoc Tags Reference

| Tag | Purpose | Example |
|-----|---------|---------|
| `@param` | Parameter | `@param userId the user's unique identifier` |
| `@return` | Return value | `@return the user object, never null` |
| `@throws` | Exception | `@throws NotFoundException if not found` |
| `@since` | Version | `@since 2.0.0` |
| `@see` | Cross-reference | `@see UserRepository#findById(UUID)` |
| `@implNote` | Implementation notes | `@implNote This method is thread-safe` |
| `{@code}` | Inline code | `{@code null}` |
| `{@link}` | Hyperlink | `{@link User}` |

## Example JSDoc Output

```typescript
/**
 * Calculates the absolute and percentage return on a portfolio investment.
 *
 * Formula: `absoluteReturn = (finalValue - initialValue) + dividends`
 *
 * @param initialValue - The starting portfolio value (must be > 0)
 * @param finalValue - The ending portfolio value (can be any number)
 * @param dividends - Total dividends received (default: 0)
 * @returns Object with absoluteReturn and percentReturn
 * @throws {Error} If initialValue is zero or negative
 *
 * @example
 * // Positive return
 * calculateReturn(10000, 12000) // { absoluteReturn: 2000, percentReturn: 20 }
 *
 * @since 1.0.0
 * @see calculateAnnualizedReturn
 */
```

## Common Pitfalls to Avoid

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Missing @throws | Caller unaware of exceptions | Document ALL thrown exceptions |
| No examples | Hard to understand usage | Include 2-3 realistic examples |
| Outdated docs | Docs don't match code | Review docs when code changes |
| Parameter type only | "@param name string" | Include description and constraints |

## Expected Output Quality: 9/10
