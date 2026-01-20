# Pattern: JSDoc / Javadoc Generation

**Category:** Documentation
**Language:** TypeScript/JavaScript / Java
**Success Rate:** 92%
**Last Verified:** December 2025

## Variables

| Variable | Description | Example Values |
|----------|-------------|----------------|
| `[function/class/method]` | What to document | `calculatePortfolioReturn`, `UserService`, `validate` |
| `[brief/detailed]` | Description length | `brief` (1-2 sentences), `detailed` (with algorithm) |
| `[number]` | Number of examples | `2`, `3`, `5` |
| `[version]` | API version | `1.0.0`, `2.3.1` |

## Prompt Template

```
Generate comprehensive JSDoc documentation for [function/class/method]:

Code:
[paste the code]

Documentation requirements:
1. Description: [brief/detailed] explanation of purpose
2. Parameters: Document all with types and descriptions
3. Returns: Document return value and possible values
4. Throws: List all exceptions that can be thrown
5. Examples: Include [number] usage examples

Additional tags to include:
- @since [version]
- @see [related functions/docs]
- @deprecated [if applicable]

Format: Standard JSDoc format
Constraints: [any constraints, e.g., "Keep under 20 lines"]
```

## Example 1: TypeScript JSDoc

```
Generate comprehensive JSDoc documentation for calculatePortfolioReturn:

Code:
function calculatePortfolioReturn(
  initialValue: number,
  finalValue: number,
  dividends: number = 0
): { absoluteReturn: number; percentReturn: number }

Documentation requirements:
1. Description: Detailed explanation including formula used
2. Parameters: Document all three with valid ranges
3. Returns: Document both properties of return object
4. Throws: Document division by zero case
5. Examples: Include 3 usage examples (gain, loss, with dividends)

Additional tags to include:
- @since 1.0.0
- @see calculateAnnualizedReturn

Format: Standard JSDoc format
Constraints: Include formula in description
```

### Expected TypeScript Output

```typescript
/**
 * Calculates the absolute and percentage return on a portfolio investment.
 *
 * Formula: `absoluteReturn = (finalValue - initialValue) + dividends`
 * Formula: `percentReturn = (absoluteReturn / initialValue) * 100`
 *
 * @param initialValue - The starting portfolio value (must be > 0)
 * @param finalValue - The ending portfolio value (can be any number)
 * @param dividends - Total dividends received during the period (default: 0)
 * @returns An object containing absoluteReturn (in dollars) and percentReturn (as percentage)
 * @throws {Error} If initialValue is zero or negative
 *
 * @example
 * // Positive return (gain)
 * calculatePortfolioReturn(10000, 12000) // { absoluteReturn: 2000, percentReturn: 20 }
 *
 * @example
 * // Negative return (loss)
 * calculatePortfolioReturn(10000, 8000) // { absoluteReturn: -2000, percentReturn: -20 }
 *
 * @example
 * // With dividends
 * calculatePortfolioReturn(10000, 10000, 500) // { absoluteReturn: 500, percentReturn: 5 }
 *
 * @since 1.0.0
 * @see calculateAnnualizedReturn
 */
```

## Example 2: Java Javadoc

```
Generate comprehensive Javadoc documentation for PortfolioService.rebalancePortfolio:

Code:
@Transactional
public RebalanceResult rebalancePortfolio(
    UUID portfolioId,
    Map<String, BigDecimal> targetAllocations,
    RebalanceOptions options
)

Documentation requirements:
1. Description: Detailed explanation of rebalancing algorithm
2. Parameters: Document all three with valid ranges and constraints
3. Returns: Document RebalanceResult fields (trades, fees, newAllocations)
4. Throws: List PortfolioNotFoundException, InsufficientFundsException, InvalidAllocationException
5. Examples: Include 2 usage examples (simple rebalance, with options)

Additional tags to include:
- @since 2.0.0
- @see Portfolio#getCurrentAllocations()
- @implNote Uses atomic transactions

Format: Standard Javadoc format
Constraints: Document thread-safety and transaction behavior
```

### Expected Java Output

```java
/**
 * Rebalances a portfolio to match target asset allocations.
 *
 * <p>This method calculates the optimal trades needed to move from current allocations
 * to target allocations while minimizing transaction costs. The rebalancing is performed
 * atomically within a database transaction.
 *
 * <h3>Algorithm:</h3>
 * <ol>
 *   <li>Calculate current allocation percentages</li>
 *   <li>Compute delta from target allocations</li>
 *   <li>Generate sell orders for over-allocated positions</li>
 *   <li>Generate buy orders for under-allocated positions</li>
 *   <li>Execute all trades atomically</li>
 * </ol>
 *
 * @param portfolioId the unique identifier of the portfolio to rebalance
 * @param targetAllocations a map of asset symbols to target percentages (must sum to 100)
 * @param options rebalancing options including threshold and tax-loss harvesting settings
 * @return a {@link RebalanceResult} containing executed trades, fees, and new allocations
 *
 * @throws PortfolioNotFoundException if no portfolio exists with the given ID
 * @throws InsufficientFundsException if the portfolio lacks funds to cover fees
 * @throws InvalidAllocationException if target allocations don't sum to 100%
 * @throws IllegalArgumentException if portfolioId is null
 *
 * <pre>{@code
 * // Simple rebalance to 60/40 stocks/bonds
 * Map<String, BigDecimal> targets = Map.of(
 *     "VTI", new BigDecimal("60"),
 *     "BND", new BigDecimal("40")
 * );
 * RebalanceResult result = portfolioService.rebalancePortfolio(
 *     portfolioId, targets, RebalanceOptions.defaults()
 * );
 *
 * // Rebalance with 5% threshold (only rebalance if drift > 5%)
 * RebalanceOptions options = RebalanceOptions.builder()
 *     .threshold(new BigDecimal("5"))
 *     .taxLossHarvesting(true)
 *     .build();
 * RebalanceResult result = portfolioService.rebalancePortfolio(
 *     portfolioId, targets, options
 * );
 * }</pre>
 *
 * @since 2.0.0
 * @see Portfolio#getCurrentAllocations()
 * @see RebalanceOptions
 * @implNote This method is thread-safe. Concurrent rebalance requests for the same
 *           portfolio are serialized via database-level locking.
 */
```

## Common Pitfalls

| Pitfall | Problem | Solution |
|---------|---------|----------|
| **Missing @throws** | Caller unaware of exceptions | Document all thrown exceptions |
| **No examples** | Hard to understand usage | Always include 2-3 examples |
| **Outdated docs** | Docs don't match code | Review docs when code changes |
| **Parameter type only** | "@param name string" | Include description and constraints |
| **Missing edge cases** | Null/empty behavior unknown | Document boundary conditions |
| **No @since** | Breaking change detection hard | Always include version |

## Documentation Tags Quick Reference

### JSDoc Tags
| Tag | Purpose | Example |
|-----|---------|---------|
| `@param` | Parameter description | `@param userId - The user's unique ID` |
| `@returns` | Return value | `@returns The user object or null` |
| `@throws` | Exception documentation | `@throws {NotFoundError} If user doesn't exist` |
| `@example` | Usage example | See examples above |
| `@deprecated` | Mark as deprecated | `@deprecated Use findUserById instead` |
| `@see` | Cross-reference | `@see UserService.findById` |
| `@since` | Version introduced | `@since 1.0.0` |
| `@template` | Generic type param | `@template T - The response type` |

### Javadoc Tags
| Tag | Purpose | Example |
|-----|---------|---------|
| `@param` | Parameter | `@param userId the user's unique identifier` |
| `@return` | Return value | `@return the user object, never null` |
| `@throws` | Exception | `@throws NotFoundException if user not found` |
| `@since` | Version | `@since 2.0.0` |
| `@see` | Cross-reference | `@see UserRepository#findById(UUID)` |
| `@deprecated` | Deprecation | `@deprecated use {@link #findById} instead` |
| `@implNote` | Implementation notes | `@implNote This method is thread-safe` |
| `@implSpec` | Implementation spec | `@implSpec Subclasses must call super` |
| `{@code}` | Inline code | `{@code null}` |
| `{@link}` | Hyperlink | `{@link User}` |
| `<pre>` | Code block | `<pre>{@code example}</pre>` |

## Implementation Hints for AI

When generating documentation:
1. **Start with the "why"** not the "what" - explain purpose before mechanics
2. **Document all parameters** including default values and valid ranges
3. **Include edge case behavior** - what happens with null, empty, zero?
4. **Add realistic examples** - use domain-relevant values, not foo/bar
5. **Mention thread-safety** for methods that may be called concurrently
6. **Cross-reference related methods** using @see

## Expected Output Quality: 9/10
